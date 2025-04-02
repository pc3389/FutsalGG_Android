package com.futsalgg.app.presentation.signup

import com.futsalgg.app.core.token.FakeTokenManager
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.model.UpdateProfileResponseModel
import com.futsalgg.app.domain.user.usecase.SignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel
    private lateinit var fakeSignupUseCase: FakeSignupUseCase
    private lateinit var fakeTokenManager: FakeTokenManager
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeSignupUseCase = FakeSignupUseCase()
        fakeTokenManager = FakeTokenManager()
        viewModel = SignupViewModel(
            signupUseCase = fakeSignupUseCase,
            tokenManager = fakeTokenManager
        )
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
        assertEquals(EditTextState.Available, viewModel.birthdayState.value)
    }

    /**
     * 한글이 아닌 닉네임("test123")을 입력하면 nicknameState가 ErrorCannotUse이어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication with non-Korean nickname returns ErrorCannotUse`() = runTest {
        // given
        viewModel.onNicknameChange("test123")

        // when
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.ErrorCannotUse, viewModel.nicknameState.value)
    }

    /**
     * 중복되지 않은 닉네임("테스트닉네임")을 입력하면 nicknameState가 Available이어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication with unique nickname returns Available`() = runTest {
        // given
        fakeSignupUseCase.shouldSucceed = true
        fakeSignupUseCase.isUnique = true
        viewModel.onNicknameChange("테스트닉네임")

        // when
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.Available, viewModel.nicknameState.value)
    }

    /**
     * 이미 사용 중인 닉네임("중복닉네임")을 입력하면 nicknameState가 ErrorAlreadyExisting이어야 합니다.
     */
    @Test
    fun `checkNicknameDuplication with duplicate nickname returns ErrorAlreadyExisting`() = runTest {
        // given
        fakeSignupUseCase.shouldSucceed = true
        fakeSignupUseCase.isUnique = false
        viewModel.onNicknameChange("중복닉네임")

        // when
        viewModel.checkNicknameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.ErrorAlreadyExisting, viewModel.nicknameState.value)
    }

    /**
     * 모든 필수 정보가 올바르게 입력된 경우 createUser가 성공적으로 완료되어야 합니다.
     */
    @Test
    fun `createUser success`() = runTest {
        // given
        fakeSignupUseCase.shouldSucceed = true
        viewModel.onNicknameChange("테스트닉네임")
        viewModel.onBirthdayChange("19900101")
        viewModel.onGenderChange(Gender.MALE)

        var successCalled = false

        // when
        viewModel.createUser { successCalled = true }
        advanceUntilIdle()

        // then
        assertTrue(successCalled)
    }

    /**
     * 프로필 이미지 업로드가 성공하면 profileImageUrl이 업데이트되어야 합니다.
     */
    @Test
    fun `uploadProfileImage success`() = runTest {
        // given
        fakeSignupUseCase.shouldSucceed = true
        val testFile = File("test.jpg")

        // when
        viewModel.uploadProfileImage(testFile)
        advanceUntilIdle()

        // then
        assertEquals("testUrl", viewModel.profileImageUrl.value)
    }

    // Fake UseCase
    class FakeSignupUseCase : SignupUseCase {
        var shouldSucceed = true
        var isUnique = true

        override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
            return if (shouldSucceed) {
                Result.success(isUnique)
            } else {
                Result.failure(Exception("Nickname check failed"))
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
            return if (shouldSucceed) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Create user failed"))
            }
        }

        override suspend fun uploadProfileImage(
            accessToken: String,
            file: File
        ): Result<UpdateProfileResponseModel> {
            return if (shouldSucceed) {
                Result.success(UpdateProfileResponseModel("testUrl", "testUri"))
            } else {
                Result.failure(Exception("Upload failed"))
            }
        }
    }
}

