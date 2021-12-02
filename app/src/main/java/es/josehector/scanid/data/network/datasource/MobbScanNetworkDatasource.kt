package es.josehector.scanid.data.network.datasource

import androidx.annotation.VisibleForTesting
import com.rocket.android.core.data.network.datasource.BaseNetworkDatasource
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import es.josehector.scanid.data.network.model.response.ApiResponse
import es.josehector.scanid.data.network.service.MobbScanService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MobbScanNetworkDatasource @Inject constructor(
    private val mobbScanService: MobbScanService,
    private val crashLogger: CrashLogger
) : BaseNetworkDatasource(crashLogger) {

    fun scanIdDocument(
        licenseId: String,
        documentType: String,
        front: File,
        back: File
    ): Either<Failure, ApiResponse.Document?> {
        val requestBodyLicenseId = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(), licenseId
        )
        val requestBodyDocumentType = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(), documentType
        )

        val requestFileFront = front.asRequestBody("image/*".toMediaTypeOrNull())
        val multiPartBodyFront =
            MultipartBody.Part.createFormData("front", front.name, requestFileFront)

        val requestFileBack = back.asRequestBody("image/*".toMediaTypeOrNull())
        val multiPartBodyBack =
            MultipartBody.Part.createFormData("back", back.name, requestFileBack)

        return requestApi(
            call = {
                mobbScanService.scanIdDocument(
                    licenseId = requestBodyLicenseId,
                    documentType = requestBodyDocumentType,
                    front = multiPartBodyFront,
                    back = multiPartBodyBack
                )
            },
            parserSuccess = {
                it?.document
            }
        )
    }

    @VisibleForTesting
    internal fun scanIdDocument(): Either<Failure, ApiResponse.Document?> {
        return requestApi(
            call = {
                mobbScanService.scanIdDocument(
                )
            },
            parserSuccess = {
                it?.document
            }
        )
    }
}