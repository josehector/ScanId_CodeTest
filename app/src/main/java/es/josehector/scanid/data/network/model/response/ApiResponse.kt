package es.josehector.scanid.data.network.model.response

import com.squareup.moshi.Json

/**
 * Api model to receive web service response
 */

class ApiResponse {
    data class Document(
        @Json(name = "personalNumber") val personalNumber: String?,
        @Json(name = "documentNumber") val documentNumber: String?,
        @Json(name = "documentType") val documentType: String?,
        @Json(name = "name") val name: String?,

        @Json(name = "surname") val surname: String?,
        @Json(name = "firstSurname") val firstSurname: String?,

        @Json(name = "secondSurname") val secondSurname: String?,
        @Json(name = "gender") val gender: String?,
        @Json(name = "dateOfExpiry") val dateOfExpiry: String?,
        @Json(name = "dateOfBirth") val dateOfBirth: String?,
        @Json(name = "nationality") val nationality: String?,
        @Json(name = "issuingState") val issuingState: String?,
        @Json(name = "mrz") val mrz: String?,
        @Json(name = "parents") val parents: String?,
        @Json(name = "region") val region: String?,
        @Json(name = "regionOfBirth") val regionOfBirth: String?,
        @Json(name = "address") val address: String?,
        @Json(name = "city") val city: String?,
        @Json(name = "cityOfBirth") val cityOfBirth: String?,
    )
}