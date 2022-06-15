package me.podlesnykh.graduationproject.domain.usecases

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.podlesnykh.graduationproject.common.ResponseTestUtils
import me.podlesnykh.graduationproject.data.network.models.ErrorResponse
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.presentation.models.TopHeadlinesSearchSettingsModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test

/**
 * Test for [GetTopHeadlinesUseCase]
 *
 * todo add tests for cases with exceptions from DB and Network
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetTopHeadlinesUseCaseTest {
    private val defaultRepository: NewsRepository = mockk {
        coEvery { saveTopHeadlinesToLocal(ResponseTestUtils.createAllArticlesList()) } returns Unit
    }
    private val useCase = GetTopHeadlinesUseCase(defaultRepository)
    private val settingsModel = TopHeadlinesSearchSettingsModel(
        null, null, null, null, null, null
    )
    private val serverError = ErrorResponse(code = "1", message="1", status = "1")

    @Test
    fun testExecute_success_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()

        Truth.assertThat(useCase.execute(settingsModel, true))
            .isEqualTo(
                Response.Success(
                    ResponseTestUtils.createAllArticlesList(),
                    null
                )
            )

        coVerify {
            defaultRepository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
        }
        coVerify { defaultRepository.saveTopHeadlinesToLocal(any()) }
    }

    @Test
    fun testExecute_success_noReloadDataForce() = runTest {
        coEvery {
            defaultRepository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()
        coEvery { defaultRepository.getTopHeadlinesFromLocal() } returns ResponseTestUtils.createDatabaseTopHeadlinesResponse()

        Truth.assertThat(useCase.execute(settingsModel, false))
            .isEqualTo(
                Response.Success(
                    ResponseTestUtils.createAllArticlesList(),
                    null
                )
            )

        coVerify {
            defaultRepository.getTopHeadlinesFromLocal()
        }
    }

    @Test
    fun testExecute_error_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns retrofit2.Response.error(
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                ""
            )
        )

        Truth.assertThat(useCase.execute(settingsModel, true))
            .isEqualTo(
                Response.Error(
                    null,
                    null
                )
            )
    }

    @Test
    fun testExecute_error_noReloadDataForce() = runTest {
        coEvery { defaultRepository.getTopHeadlinesFromLocal() } returns null

        coEvery {
            defaultRepository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
        } returns retrofit2.Response.error(
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                ""
            )
        )

        Truth.assertThat(useCase.execute(settingsModel, false))
            .isEqualTo(
                Response.Error(
                    null,
                    null
                )
            )
    }
}