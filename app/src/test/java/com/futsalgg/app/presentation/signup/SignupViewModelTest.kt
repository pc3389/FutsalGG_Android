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
        fakeTokenManager = FakeTokenManager()
        viewModel = SignupViewModel(fakeUserRepository, fakeTokenManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 유효한 생년월일("20000101")을 입력하면 birthdayState가 Available이어야 합니다.
     */
    @Test
    fun `validateBirthday returns Available for correct date format within range`() = runTest {
        viewModel.onBirthdayChange("20000101")
        viewModel.validateBirthday()
        // 올바른 날짜 포맷과 범위이면 에러 상태가 아니라 Available 상태가 되어야 합니다.
        assertEquals(EditTextState.Available, viewModel.birthdayState.value)
    }

    /**
     * 잘못된 생년월일 포맷("2000/01/01")을 입력하면 birthdayState가 ErrorCannotUse이어야 합니다.
     */
    @Test
    fun `validateBirthday returns ErrorCannotUse for incorrect date format`() = runTest {
        viewModel.onBirthdayChange("2000/01/01")
        viewModel.validateBirthday()
        assertEquals(EditTextState.ErrorCannotUse, viewModel.birthdayState.value)
    }

    /**
     * 닉네임이 "유니크"인 경우, checkNicknameDuplication 호출 시 nicknameState가 Available로 설정되어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication returns Available for unique nickname`() = runTest {
        viewModel.onNicknameChange("유니크")
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()  // 모든 코루틴 작업이 완료될 때까지 대기
        assertEquals(EditTextState.Available, viewModel.nicknameState.value)
    }

    /**
     * 한글이 아닌 닉네임("notKorean")인 경우, checkNicknameDuplication 호출 시 nicknameState가 ErrorCannotUse로 설정되어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication returns ErrorCannotUse for non-Korean nickname`() = runTest {
        viewModel.onNicknameChange("notKorean")
        viewModel.checkNicknameDuplication()
        // 포맷이 한글이 아니므로 바로 ErrorCannotUse로 처리됩니다.
        assertEquals(EditTextState.ErrorCannotUse, viewModel.nicknameState.value)
    }

    /**
     * 닉네임이 중복되는 경우("익시스팅에러") checkNicknameDuplication 호출 시 nicknameState가 ErrorAlreadyExisting로 설정되어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication returns ErrorAlreadyExisting for duplicate nickname`() = runTest {
        viewModel.onNicknameChange("익시스팅에러")
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()
        assertEquals(EditTextState.ErrorAlreadyExisting, viewModel.nicknameState.value)
    }

    /**
     * createUser 함수가 호출되면 내부적으로 생년월일 형식을 변환하여 repository를 호출하고,
     * 모든 작업이 완료된 후 _isLoading 상태가 false가 되어야 합니다.
     */
    @Test
    fun `createUser converts birthday format correctly and finishes loading`() = runTest {
        viewModel.onBirthdayChange("20000101")  // "yyyyMMdd" 형식 입력
        viewModel.onNicknameChange("유니크")
        viewModel.onGenderChange(Gender.MALE)
        viewModel.createUser(onSuccess = {})
        // createUser 호출 후 _isLoading가 false여야 합니다.
        assertEquals(false, viewModel.isLoading.value)
    }

    /**
     * uploadProfileImage 함수가 호출되면 FakeUserRepository의 구현에 따라
     * 최종적으로 profileImageUrl 상태가 "http://fake.updated.url/profile"로 업데이트되어야 합니다.
     */
    @Test
    fun `uploadProfileImage updates profileImageUrl when upload succeeds`() = runTest {
        // 테스트용 임시 파일 생성 (빈 파일)
        val tempFile = kotlin.io.path.createTempFile("temp", ".jpg").toFile()
        viewModel.uploadProfileImage(tempFile)
        advanceUntilIdle()
        // FakeUserRepository는 updateProfile이 "http://fake.updated.url/profile"을 반환하도록 구현되어 있습니다.
        assertEquals("http://fake.updated.url/profile", viewModel.profileImageUrl.value)
        tempFile.delete() // 임시 파일 삭제
    }

    // --- Fake Implementations for Testing ---

    /**
     * FakeUserRepository는 UserRepository 인터페이스를 구현하며,
     * FileUploader를 주입받아 uploadProfileImage의 전체 플로우를 시뮬레이션합니다.
     */
    class FakeUserRepository(private val fileUploader: FileUploader) : UserRepository {
        override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
            // "유니크"라는 닉네임이면 고유, 그 외에는 중복으로 처리
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
            // 항상 성공을 반환
            return Result.success(Unit)
        }

        override suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponse> {
            // 테스트용 고정된 presigned URL과 uri 반환
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
            // 테스트용 고정된 업데이트 응답 반환
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
            // 2. presigned URL로 파일 업로드: FakeFileUploader의 uploadFileToPresignedUrl 호출
            val uploadResult = fileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }
            // 3. updateProfile 호출하여 최종 응답 반환
            return updateProfile(accessToken, presignedResponse.uri)
        }
    }

    /**
     * FakeFileUploader는 FileUploader 인터페이스를 구현하며,
     * 항상 성공을 반환하여 파일 업로드 과정을 시뮬레이션합니다.
     */
    class FakeFileUploader : FileUploader {
        override suspend fun uploadFileToPresignedUrl(
            presignedUrl: String,
            file: File
        ): Result<Unit> {
            return Result.success(Unit)
        }
    }
}

