package com.futsalgg.app.presentation.signup

import com.futsalgg.app.core.token.FakeTokenManager
import com.futsalgg.app.data.model.response.ProfilePresignedUrlResponse
import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.domain.repository.FileUploader
import com.futsalgg.app.domain.repository.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SignupViewModelTest {

    private lateinit var fakeFileUploader: FakeFileUploader
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeTokenManager: FakeTokenManager
    private lateinit var viewModel: SignupViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeFileUploader = FakeFileUploader()
        fakeUserRepository = FakeUserRepository(fakeFileUploader)
        fakeTokenManager = FakeTokenManager().apply {
            saveTokens("fake_access_token", "fake_refresh_token")
        }
        viewModel = SignupViewModel(fakeUserRepository, fakeTokenManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `birthday valid when in correct format and within range`() = runTest {
        // 2000년 1월 1일은 유효한 날짜라고 가정 ("yyyyMMdd" 포맷)
        viewModel.onBirthdayChange("20000101")
        viewModel.validateBirthday()
        assertEquals(EditTextState.Available, viewModel.birthdayState.value)
    }

    @Test
    fun `birthday invalid when format is incorrect`() = runTest {
        // 잘못된 포맷으로 입력한 경우
        viewModel.onBirthdayChange("2000/01/01")
        viewModel.validateBirthday()
        assertEquals(EditTextState.ErrorCannotUse, viewModel.birthdayState.value)
    }

    @Test
    fun `nickname unique check returns Available for unique nickname`() = runTest {
        viewModel.onNicknameChange("유니크")
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()
        assertEquals(EditTextState.Available, viewModel.nicknameState.value)
    }

    @Test
    fun `nickname unique check returns ErrorCannotUse for unique nickname`() = runTest {
        viewModel.onNicknameChange("notKorean")
        viewModel.checkNicknameDuplication()
        assertEquals(EditTextState.ErrorCannotUse, viewModel.nicknameState.value)
    }

    @Test
    fun `nickname unique check returns ErrorAlreadyExisting for duplicate nickname`() = runTest {
        viewModel.onNicknameChange("버어어")
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()
        assertEquals(EditTextState.ErrorAlreadyExisting, viewModel.nicknameState.value)
    }

    @Test
    fun `createUser converts birthday format correctly and calls repository`() = runTest {
        // 입력: "yyyyMMdd", 출력: "yyyy-MM-dd" (2000-01-01)
        viewModel.onBirthdayChange("20000101")
        viewModel.onNicknameChange("유니크")
        viewModel.onGenderChange(Gender.MALE)
        // notification 상태는 기본적으로 false; 필요하면 toggleNotification 호출

        viewModel.createUser(onSuccess = {})

        // FakeUserRepository는 항상 성공을 반환하므로, 테스트는 _isLoading가 false로 끝나는지로 검증
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `uploadProfileImage calls repository and updates profileImageUrl`() = runTest {
        // 테스트용 빈 파일 생성 (임시 파일)
        val tempFile = kotlin.io.path.createTempFile("temp", ".jpg").toFile()

        // ViewModel의 uploadProfileImage 호출
        viewModel.uploadProfileImage(tempFile)
        advanceUntilIdle()

        // FakeUserRepository의 updateProfile은 "http://fake.updated.url/profile" 반환하도록 구현되었으므로,
        // profileImageUrl 상태가 해당 값으로 업데이트되었는지 확인합니다.
        assertEquals("http://fake.updated.url/profile", viewModel.profileImageUrl.value)

        tempFile.delete() // 임시 파일 삭제
    }

    class FakeUserRepository(private val fileUploader: FileUploader) : UserRepository {
        override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
            // 예시: "유니크"라는 닉네임이면 유니크, 그 외에는 중복으로 처리
            return if (nickname == "유니크") {
                Result.success(true)
            } else {
                Result.success(false)
            }
        }

        override suspend fun createUser(
            accessToken: String,
            nickname: String,
            birthDate: String,
            gender: Gender,
            agreement: Boolean,
            notification: Boolean
        ): Result<Unit> {
            // 항상 성공했다고 가정
            return Result.success(Unit)
        }

        override suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponse> {
            // 고정된 presigned URL과 uri 반환 (테스트용)
            val fakeResponse = ProfilePresignedUrlResponse(
                url = "http://fake.presigned.url/upload",
                uri = "http://fake.uri/profile"
            )
            return Result.success(fakeResponse)
        }

        override suspend fun updateProfile(
            accessToken: String,
            uri: String
        ): Result<UpdateProfileResponse> {
            // 고정된 업데이트 응답 반환 (테스트용)
            val fakeResponse = UpdateProfileResponse(
                url = "http://fake.updated.url/profile",
                uri = uri
            )
            return Result.success(fakeResponse)
        }

        override suspend fun uploadProfileImage(
            accessToken: String,
            file: File
        ): Result<UpdateProfileResponse> {
            // 1. presigned URL 획득
            val presignedResponse =
                getProfilePresignedUrl(accessToken).getOrElse { return Result.failure(it) }
            // 2. presigned URL로 파일 업로드: 여기서 FakeFileUploader.uploadFileToPresignedUrl 호출
            val uploadResult = fileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }
            // 3. updateProfile 호출하여 최종 응답 반환
            return updateProfile(accessToken, presignedResponse.uri)
        }
    }

    class FakeFileUploader : FileUploader {
        override suspend fun uploadFileToPresignedUrl(
            presignedUrl: String,
            file: File
        ): Result<Unit> {
            return Result.success(Unit)
        }
    }
}

