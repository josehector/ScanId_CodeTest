package es.josehector.scanid.data.network.datasource

import com.rocket.android.core.data.network.error.NetworkFailure
import com.rocket.android.core.data.network.test.mockserver.MockWebServerTest
import com.rocket.android.core.data.network.test.utils.l
import com.rocket.core.data.network.commons.di.createApiClient
import es.josehector.scanid.data.network.service.MobbScanService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File

@ExperimentalCoroutinesApi
class MobbScanNetworkDatasourceTest : MockWebServerTest() {
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
                val either = sut.scanIdDocument(
                    licenseId = "",
                    documentType = "",
                    front = File("path"),
                    back = File("path")
                )
                assertTrue(either.isRight())
            }
        }

        @Test
        fun `should get a timeout response`() {
            runBlocking {
                setNetworkTimeout()
                val either = sut.scanIdDocument(
                    licenseId = "",
                    documentType = "",
                    front = File("path"),
                    back = File("path")
                )
                assertTrue(either.isLeft())
                assertTrue(either.l() is NetworkFailure.Timeout)
            }
        }
    }
}