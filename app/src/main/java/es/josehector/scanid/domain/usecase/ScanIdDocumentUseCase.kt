package es.josehector.scanid.domain.usecase

import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import com.rocket.core.domain.usecase.UseCaseSuspend
import es.josehector.scanid.domain.error.DomainFailure
import es.josehector.scanid.domain.model.Document
import es.josehector.scanid.domain.repository.MobbScanRepository
import java.io.File
import javax.inject.Inject

/**
 * Use case for scanning document id.
 *
 * Return all information about this document id.
 *
 * TODO: license id and document type should go in kind of session o similar
 * TODO: manage different situations like connection error, etc...
 */
class ScanIdDocumentUseCase @Inject constructor(
    private val mobbScanRepository: MobbScanRepository
) : UseCaseSuspend<Either<Failure, Document>, ScanIdDocumentUseCase.Params>() {

    override suspend fun run(params: Params?): Either<Failure, Document> {
        return when (params) {
            is Params -> {
                mobbScanRepository.scanIdDocument(
                    licenseId = params.licenseId,
                    documentType = params.documentType,
                    front = params.front,
                    back = params.back
                )
            }
            else -> {
                Either.Left(DomainFailure.MissingParameters("No document info params"))
            }
        }
    }

    class Params private constructor(
        val licenseId: String,
        val documentType: String,
        val front: File,
        val back: File
    ) {
        companion object {
            fun forDocumentInfo(
                licenseId: String,
                documentType: String,
                front: File,
                back: File
            ): Params {
                return Params(
                    licenseId = licenseId,
                    documentType = documentType,
                    front = front,
                    back = back
                )
            }
        }
    }
}