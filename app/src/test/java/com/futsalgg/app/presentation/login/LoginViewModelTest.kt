package com.futsalgg.app.presentation.login

import com.futsalgg.app.core.token.FakeTokenManager
import com.futsalgg.app.data.model.Platform
import com.futsalgg.app.data.model.response.LoginResponse
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
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
    private lateinit var fakeGoogleLoginRepository: FakeGoogleLoginRepository
    private lateinit var fakeLoginRepository: FakeLoginRepository
    private lateinit var fakeTokenManager: FakeTokenManager

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeGoogleLoginRepository = FakeGoogleLoginRepository()
        fakeLoginRepository = FakeLoginRepository()
        fakeTokenManager = FakeTokenManager()

        viewModel = LoginViewModel(
            loginRepository = fakeLoginRepository,
            googleLoginRepository = fakeGoogleLoginRepository,
            tokenManager = fakeTokenManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // ✅ 테스트 종료 시 Main dispatcher 복원
    }

    @Test
    fun `signInWithGoogleIdToken success calls onSuccess`() = runTest {
        // given
        fakeGoogleLoginRepository.shouldSucceed = true
        fakeLoginRepository.shouldSucceed = true
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
        assertEquals("accessToken", fakeTokenManager.getAccessToken())
        assertEquals("refreshToken", fakeTokenManager.getRefreshToken())
    }

    @Test
    fun `signInWithGoogleIdToken fails on firebase login`() = runTest {
        // given
        fakeGoogleLoginRepository.shouldSucceed = false
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

    @Test
    fun `signInWithGoogleIdToken fails on server login`() = runTest {
        // given
        fakeGoogleLoginRepository.shouldSucceed = true
        fakeLoginRepository.shouldSucceed = false
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
        assertFalse(successCalled)
        assertTrue(failureCalled)
    }

    // Fakes

    class FakeGoogleLoginRepository : GoogleLoginRepository {
        var shouldSucceed = true
        override suspend fun signInWithGoogleIdToken(idToken: String): Result<Unit> {
            return if (shouldSucceed) Result.success(Unit)
            else Result.failure(Exception("Firebase login failed"))
        }
    }

    class FakeLoginRepository : LoginRepository {
        var shouldSucceed = true
        override suspend fun loginWithGoogleToken(token: String, platform: Platform): Result<LoginResponse> {
            return if (shouldSucceed)
                Result.success(LoginResponse("accessToken", "refreshToken", false))
            else Result.failure(Exception("Server login failed"))
        }
    }
}
