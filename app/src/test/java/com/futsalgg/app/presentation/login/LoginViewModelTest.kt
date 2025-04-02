package com.futsalgg.app.presentation.login

import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeLoginUseCase: FakeAuthUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeLoginUseCase = FakeAuthUseCase()
        viewModel = LoginViewModel(authUseCase = fakeLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 유효한 Google ID 토큰으로 로그인 시도 시 성공적으로 로그인이 완료되어야 합니다.
     */
    @Test
    fun `signInWithGoogleIdToken success calls onSuccess`() = runTest {
        // given
        fakeLoginUseCase.shouldSucceed = true
        var successCalled = false
        var failureCalled = false

        // when
        viewModel.signInWithGoogleIdToken(
            idToken = "validIdToken",
            onSuccess = { successCalled = true },
            onFailure = { failureCalled = true }
        )

        advanceUntilIdle()

        // then
        assertTrue(successCalled)
        assertFalse(failureCalled)
    }

    /**
     * 유효하지 않은 Google ID 토큰으로 로그인 시도 시 실패 콜백이 호출되어야 합니다.
     */
    @Test
    fun `signInWithGoogleIdToken failure calls onFailure`() = runTest {
        // given
        fakeLoginUseCase.shouldSucceed = false
        var successCalled = false
        var failureCalled = false

        // when
        viewModel.signInWithGoogleIdToken(
            idToken = "invalidToken",
            onSuccess = { successCalled = true },
            onFailure = { failureCalled = true }
        )

        advanceUntilIdle()

        // then
        assertFalse(successCalled)
        assertTrue(failureCalled)
    }

    // Fake UseCase
    class FakeAuthUseCase : AuthUseCase {
        var shouldSucceed = true
        override suspend operator fun invoke(idToken: String): Result<LoginResponseModel> {
            return if (shouldSucceed) {
                Result.success(LoginResponseModel("accessToken", "refreshToken", false))
            } else {
                Result.failure(Exception("Login failed"))
            }
        }
    }
}
