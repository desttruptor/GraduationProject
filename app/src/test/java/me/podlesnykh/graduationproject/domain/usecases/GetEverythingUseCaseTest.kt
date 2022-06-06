package me.podlesnykh.graduationproject.domain.usecases

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.podlesnykh.graduationproject.common.ResponseTestUtils
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.presentation.models.EverythingSearchSettingsModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

/**
 * Test for [GetEverythingUseCase]
 *
 * todo add tests for cases with exceptions from DB and Network
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetEverythingUseCaseTest {
    private val defaultRepository: NewsRepository = mockk {
        coEvery { saveEverythingToLocal(ResponseTestUtils.createAllArticlesList()) } returns Unit
    }
    private val useCase = GetEverythingUseCase(defaultRepository)
    private val settingsModel = EverythingSearchSettingsModel(
        null, null, null, null, null, null, null, null, null, null, null
    )

    @Test
    fun testExecute_success_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()

        Truth.assertThat(useCase.execute(settingsModel, true))
            .isEqualTo(
                me.podlesnykh.graduationproject.domain.Response.Success(
                    ResponseTestUtils.createAllArticlesList(),
                    null
                )
            )

        coVerify {
            defaultRepository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
        }
        coVerify { defaultRepository.saveEverythingToLocal(any()) }
    }

    @Test
    fun testExecute_success_noReloadDataForce() = runTest {
        coEvery {
            defaultRepository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()
        coEvery { defaultRepository.getEverythingFromLocal() } returns ResponseTestUtils.createDatabaseEverythingResponse()

        Truth.assertThat(useCase.execute(settingsModel, false))
            .isEqualTo(
                me.podlesnykh.graduationproject.domain.Response.Success(
                    ResponseTestUtils.createAllArticlesList(),
                    null
                )
            )

        coVerify {
            defaultRepository.getEverythingFromLocal()
        }
    }

    @Test
    fun testExecute_error_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns Response.error(
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                ""
            )
        )

        Truth.assertThat(useCase.execute(settingsModel, true))
            .isEqualTo(
                me.podlesnykh.graduationproject.domain.Response.Error(
                    null,
                    404
                )
            )
    }

    @Test
    fun testExecute_error_noReloadDataForce() = runTest {
        coEvery { defaultRepository.getEverythingFromLocal() } returns null

        coEvery {
            defaultRepository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns Response.error(
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                ""
            )
        )

        Truth.assertThat(useCase.execute(settingsModel, false))
            .isEqualTo(
                me.podlesnykh.graduationproject.domain.Response.Error(
                    null,
                    404
                )
            )
    }
}