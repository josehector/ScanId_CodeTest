package es.josehector.scanid.data.network.service

import es.josehector.scanid.data.network.model.ApiMobbResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Web service declaration used by retrofit
 */
interface MobbScanService {
    companion object {
        private const val BASE_URL = "mobbscan/"
        private const val SCAN = BASE_URL + "scan.json"
    }

    @Multipart
    @POST(SCAN)
    fun scanIdDocument(
        @Part("licenseId") licenseId: RequestBody,
        @Part("documentType") documentType: RequestBody,
        @Part front: MultipartBody.Part? = null,
        @Part back: MultipartBody.Part? = null
    ): Call<ApiMobbResponse.ApiBaseScanIdResponse>
}