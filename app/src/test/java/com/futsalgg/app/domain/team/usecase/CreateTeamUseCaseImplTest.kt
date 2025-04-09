package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class CreateTeamUseCaseImplTest {

    private lateinit var useCase: CreateTeamUseCaseImpl
    private lateinit var fakeTeamRepository: FakeTeamRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTeamRepository = FakeTeamRepository()
        useCase = CreateTeamUseCaseImpl(fakeTeamRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isTeamNicknameUnique success returns true`() = runTest {
        // given
        fakeTeamRepository.shouldSucceed = true
        fakeTeamRepository.isUnique = true

        // when
        val result = useCase.isTeamNicknameUnique("test_team")

        // then
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `updateTeamLogo success returns logo url`() = runTest {
        // given
        fakeTeamRepository.shouldSucceed = true
        val testFile = File("test.jpg")

        // when
        val result = useCase.updateTeamLogo("test_token", testFile)

        // then
        assertTrue(result.isSuccess)
        val response = result.getOrNull()
        assertEquals("test_url", response?.url)
        assertEquals("test_uri", response?.uri)
    }

    @Test
    fun `createTeam success returns unit`() = runTest {
        // given
        fakeTeamRepository.shouldSucceed = true

        // when
        val result = useCase.createTeam(
            accessToken = "test_token",
            name = "test_team",
            introduction = "test_intro",
            rule = "test_rule",
            matchType = MatchType.INTER_TEAM,
            access = Access.TEAM_LEADER,
            dues = 10000
        )

        // then
        assertTrue(result.isSuccess)
    }
}

class FakeTeamRepository : TeamRepository {
    var shouldSucceed = true
    var isUnique = true

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return if (shouldSucceed) {
            Result.success(isUnique)
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }

    override suspend fun getTeamLogoPresignedUrl(accessToken: String): Result<TeamLogoPresignedUrlResponseModel> {
        return if (shouldSucceed) {
            Result.success(TeamLogoPresignedUrlResponseModel("presigned_url", "test_uri"))
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }

    override suspend fun updateTeamLogo(accessToken: String, uri: String): Result<TeamLogoResponseModel> {
        return if (shouldSucceed) {
            Result.success(TeamLogoResponseModel("test_url", uri))
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }

    override suspend fun uploadLogoImage(accessToken: String, file: File): Result<TeamLogoResponseModel> {
        return if (shouldSucceed) {
            Result.success(TeamLogoResponseModel("test_url", "test_uri"))
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }

    override suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: String,
        access: String,
        dues: Int
    ): Result<Unit> {
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }
} 