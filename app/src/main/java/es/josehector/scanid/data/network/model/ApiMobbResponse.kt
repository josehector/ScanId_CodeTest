package es.josehector.scanid.data.network.model

import com.rocket.android.core.data.network.model.BaseNetworkApiResponse
import com.squareup.moshi.Json
import es.josehector.scanid.data.network.model.response.ApiResponse

class ApiMobbResponse {
    open class ApiBaseScanIdResponse(
        @Json(name = "code") val code: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "scanId") val scanId: String? = null,
        @Json(name = "document")val document: ApiResponse.Document? = null
    ) : BaseNetworkApiResponse() {
        override fun errorCode(): String? = code ?: "ERROR"
        override fun errorData(): String? = description ?: ""
        override fun isSuccess(): Boolean = code?.equals("OK") ?: false
    }

    /*class ApiScanIdResponse(
        @Json(name = "document")val document: ApiResponse.Document? = null
    ): ApiBaseScanIdResponse()*/
}