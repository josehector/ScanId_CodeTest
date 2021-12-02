package es.josehector.scanid.data.network.datasource

import com.rocket.android.core.data.network.error.NetworkFailure
import com.rocket.android.core.data.network.test.mockserver.MockWebServerTest
import com.rocket.android.core.data.network.test.utils.l
import com.rocket.android.core.data.network.test.utils.r
import com.rocket.core.data.network.commons.di.createApiClient
import es.josehector.scanid.data.network.service.MobbScanService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class MobbScanNetworkDatasourceTest : MockWebServerTest() {

    companion object {
        private const val PERSONAL_NUMBER = "99999999R"
        private const val DOCUMENT_NUMBER = "BAA000431"
    }

    private lateinit var apiService: MobbScanService

    private lateinit var sut: MobbScanNetworkDatasource

    @BeforeEach
    override fun init() {
        super.init()
        initDI()
    }

    private fun initDI() {
        configureOkHttpClient(null)

        apiService = createApiClient(
            okHttpClient = okHttpClient,
            baseUrl = baseEndpoint
        )

        sut = MobbScanNetworkDatasource(
            mobbScanService = apiService,
            crashLogger = testCoreDataNetworkProvider.crashLogger
        )
    }

    fun setNetworkTimeout() {
        configureNetworkTimeout()

        apiService = createApiClient(
            okHttpClient = okHttpClient,
            baseUrl = baseEndpoint
        )

        sut = MobbScanNetworkDatasource(
            mobbScanService = apiService,
            crashLogger = testCoreDataNetworkProvider.crashLogger
        )
    }

    @DisplayName("Test requests with base api responses")
    @Nested
    inner class ObjectResponses {
        @Test
        fun `should get ok response`() {
            runBlocking {
                enqueueMockResponse(200, "getDocumentInfo.json")
                val either = sut.scanIdDocument()
                assertTrue(either.isRight())
                either.r()?.let { document ->
                    assert(PERSONAL_NUMBER == document.personalNumber)
                    assert(DOCUMENT_NUMBER == document.documentNumber)
                } ?: kotlin.run {
                    fail()
                }
            }
        }

        @Test
        fun `should get a timeout response`() {
            runBlocking {
                setNetworkTimeout()
                val either = sut.scanIdDocument()
                assertTrue(either.isLeft())
                assertTrue(either.l() is NetworkFailure.Timeout)
            }
        }
    }
}