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
import me.podlesnykh.graduationproject.presentation.models.SourcesSearchSettingsModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test

/**
 * Test for [GetSourcesUseCase]
 *
 * todo add tests for cases with exceptions from DB and Network
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetSourcesUseCaseTest {
    private val defaultRepository: NewsRepository = mockk {
        coEvery { saveSourcesToLocal(ResponseTestUtils.createAllSourcesList()) } returns Unit
    }
    private val useCase = GetSourcesUseCase(defaultRepository)
    private val settingsModel = SourcesSearchSettingsModel(
        null, null, null
    )
    private val serverError = ErrorResponse(code = "1", message = "1", status = "1")

    @Test
    fun testExecute_success_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
            )
        } returns ResponseTestUtils.createSourcesRetrofitResponse()

        Truth.assertThat(useCase.execute(settingsModel, true))
            .isEqualTo(
                Response.Success(
                    null,
                    ResponseTestUtils.createAllSourcesList()
                )
            )

        coVerify {
            defaultRepository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
            )
        }
        coVerify { defaultRepository.saveSourcesToLocal(any()) }
    }

    @Test
    fun testExecute_success_noReloadDataForce() = runTest {

        coEvery {
            defaultRepository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
            )
        } returns ResponseTestUtils.createSourcesRetrofitResponse()

        coEvery { defaultRepository.getSourcesFromLocal() } returns ResponseTestUtils.createDatabaseSourcesResponse()

        Truth.assertThat(useCase.execute(settingsModel, false))
            .isEqualTo(
                Response.Success(
                    null,
                    ResponseTestUtils.createAllSourcesList()
                )
            )

        coVerify {
            defaultRepository.getSourcesFromLocal()
        }
    }

    @Test
    fun testExecute_error_reloadDataForce() = runTest {
        coEvery {
            defaultRepository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
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

        coEvery { defaultRepository.getSourcesFromLocal() } returns null

        coEvery {
            defaultRepository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
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