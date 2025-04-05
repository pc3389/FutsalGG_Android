package com.futsalgg.app.data.team.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
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
class TeamRepositoryImplTest {

    private lateinit var repository: TeamRepositoryImpl
    private lateinit var fakeTeamApi: FakeTeamApi
    private lateinit var fakeFileUploader: FakeOkHttpFileUploader
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTeamApi = FakeTeamApi()
        fakeFileUploader = FakeOkHttpFileUploader()
        repository = TeamRepositoryImpl(fakeTeamApi, fakeFileUploader)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isTeamNicknameUnique success returns true`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = true
        fakeTeamApi.isUnique = true

        // when
        val result = repository.isTeamNicknameUnique("test_team")

        // then
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `isTeamNicknameUnique failure returns error`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = false

        // when
        val result = repository.isTeamNicknameUnique("test_team")

        // then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DataError.ServerError)
    }

    @Test
    fun `getTeamLogoPresignedUrl success returns presigned url`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = true

        // when
        val result = repository.getTeamLogoPresignedUrl("test_token")

        // then
        assertTrue(result.isSuccess)
        val response = result.getOrNull()
        assertEquals("presigned_url", response?.url)
        assertEquals("test_uri", response?.uri)
    }

    @Test
    fun `updateTeamLogo success returns logo url`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = true

        // when
        val result = repository.updateTeamLogo("test_token", "test_uri")

        // then
        assertTrue(result.isSuccess)
        val response = result.getOrNull()
        assertEquals("test_url", response?.url)
        assertEquals("test_uri", response?.uri)
    }

    @Test
    fun `uploadLogoImage success returns logo url`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = true
        fakeFileUploader.shouldSucceed = true
        val testFile = File("test.jpg")

        // when
        val result = repository.uploadLogoImage("test_token", testFile)

        // then
        assertTrue(result.isSuccess)
        val response = result.getOrNull()
        assertEquals("test_url", response?.url)
        assertEquals("test_uri", response?.uri)
    }

    @Test
    fun `createTeam success returns unit`() = runTest {
        // given
        fakeTeamApi.shouldSucceed = true

        // when
        val result = repository.createTeam(
            accessToken = "test_token",
            name = "test_team",
            introduction = "test_intro",
            rule = "test_rule",
            matchType = "INTER_TEAM",
            access = "TEAM_LEADER",
            dues = 10000
        )

        // then
        assertTrue(result.isSuccess)
    }
}

class FakeOkHttpFileUploader : OkHttpFileUploader {
    var shouldSucceed = true

    override suspend fun uploadFileToPresignedUrl(presignedUrl: String, file: File): Result<Unit> {
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }
} 