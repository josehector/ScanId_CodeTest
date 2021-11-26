package es.josehector.scanid.domain.model

data class Document(
    val personalNumber: String,
    val documentNumber: String,
    val documentType: String,
    val name: String,
    val surname: String,
    val firstSurname: String,
    val gender: GenderType,
    val dateOfBirth: String,
    val address: String,
    val city: String
)

enum class GenderType constructor(var type: String) {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("");

    companion object {
        fun convertStringToGenderType(type: String?): GenderType {
            for (value in GenderType.values()) {
                if (value.type == type) {
                    return value
                }
            }
            return UNKNOWN
        }
    }
}
