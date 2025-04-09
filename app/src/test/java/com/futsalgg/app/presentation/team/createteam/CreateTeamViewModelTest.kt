package com.futsalgg.app.presentation.team.createteam

import com.futsalgg.app.core.token.FakeTokenManager
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.presentation.common.model.MatchType
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
class CreateTeamViewModelTest {

    private lateinit var viewModel: CreateTeamViewModel
    private lateinit var fakeCreateTeamUseCase: FakeCreateTeamUseCase
    private lateinit var fakeTokenManager: FakeTokenManager
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeCreateTeamUseCase = FakeCreateTeamUseCase()
        fakeTokenManager = FakeTokenManager()
        viewModel = CreateTeamViewModel(
            createTeamUseCase = fakeCreateTeamUseCase,
            tokenManager = fakeTokenManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 팀 이름이 비어있을 때 teamNameState가 Initial이어야 합니다.
     */
    @Test
    fun `checkTeamNameDuplication with empty name returns Initial`() = runTest {
        // given
        viewModel.onTeamNameChange("")

        // when
        viewModel.checkTeamNameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.Initial, viewModel.createTeamState.value.teamNameState)
    }

    /**
     * 중복되지 않은 팀 이름을 입력하면 teamNameState가 Available이어야 합니다.
     */
    @Test
    fun `checkTeamNameDuplication with unique name returns Available`() = runTest {
        // given
        fakeCreateTeamUseCase.shouldSucceed = true
        fakeCreateTeamUseCase.isUnique = true
        viewModel.onTeamNameChange("테스트팀")

        // when
        viewModel.checkTeamNameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.Available, viewModel.createTeamState.value.teamNameState)
    }

    /**
     * 이미 사용 중인 팀 이름을 입력하면 teamNameState가 ErrorAlreadyExisting이어야 합니다.
     */
    @Test
    fun `checkTeamNameDuplication with duplicate name returns ErrorAlreadyExisting`() = runTest {
        // given
        fakeCreateTeamUseCase.shouldSucceed = true
        fakeCreateTeamUseCase.isUnique = false
        viewModel.onTeamNameChange("중복팀")

        // when
        viewModel.checkTeamNameDuplication()
        advanceUntilIdle()

        // then
        assertEquals(EditTextState.ErrorAlreadyExisting, viewModel.createTeamState.value.teamNameState)
    }

    /**
     * 모든 필수 정보가 올바르게 입력된 경우 createTeam이 성공적으로 완료되어야 합니다.
     */
    @Test
    fun `createTeam success`() = runTest {
        // given
        fakeCreateTeamUseCase.shouldSucceed = true
        viewModel.onTeamNameChange("테스트팀")
        viewModel.onMatchTypeChange(MatchType.INTER_TEAM)
        viewModel.onAccessChange(Access.TEAM_LEADER)
        viewModel.onDuesChange("100")

        var successCalled = false

        // when
        viewModel.createTeam { successCalled = true }
        advanceUntilIdle()

        // then
        assertTrue(successCalled)
    }

    /**
     * 팀 로고 업로드가 성공하면 teamImageUrl이 업데이트되어야 합니다.
     */
    @Test
    fun `uploadTeamImage success updates teamImageUrl`() = runTest {
        // given
        fakeCreateTeamUseCase.shouldSucceed = true
        val testFile = File("test.jpg")

        // when
        viewModel.uploadTeamImage(testFile)
        advanceUntilIdle()

        // then
        assertEquals("testUrl", viewModel.createTeamState.value.teamImageUrl)
    }

    /**
     * 팀 이름, 매치 타입, 접근 권한, 회비가 모두 입력되어야 isFormValid가 true가 되어야 합니다.
     */
    @Test
    fun `isFormValid returns true when all required fields are filled`() = runTest {
        // given
        viewModel.onTeamNameChange("테스트팀")
        viewModel.onMatchTypeChange(MatchType.INTER_TEAM)
        viewModel.onAccessChange(Access.TEAM_LEADER)
        viewModel.onDuesChange("10000")

        viewModel.checkTeamNameDuplication()
        advanceUntilIdle()

        // when
        val isValid = viewModel.createTeamState.value.isFormValid

        // then
        assertTrue(isValid)
    }
} 