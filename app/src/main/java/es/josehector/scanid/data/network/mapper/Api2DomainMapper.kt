package es.josehector.scanid.data.network.mapper

import es.josehector.scanid.data.network.model.response.ApiResponse
import es.josehector.scanid.domain.model.Document
import es.josehector.scanid.domain.model.GenderType

/**
 * Mapper to convert api model into domain model
 */

fun ApiResponse.Document.toDocument(): Document {
    return Document(
        personalNumber = this.personalNumber ?: "-",
        documentNumber = this.documentNumber ?: "-",
        documentType = this.documentType ?: "-",
        name = this.name ?: "-",
        surname = this.surname ?: "-",
        firstSurname = this.firstSurname ?: "-",
        gender = GenderType.convertStringToGenderType(this.gender),
        dateOfBirth = this.dateOfBirth ?: "-",
        address = this.address ?: "-",
        city = this.city ?: "-"
    ) // FIXME: data layer is not responsible to set "-" when there is not data
}