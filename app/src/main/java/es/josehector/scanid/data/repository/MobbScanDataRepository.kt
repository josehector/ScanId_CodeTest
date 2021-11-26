package es.josehector.scanid.data.repository

import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import es.josehector.scanid.data.network.datasource.MobbScanNetworkDatasource
import es.josehector.scanid.data.network.mapper.toDocument
import es.josehector.scanid.domain.error.DomainFailure
import es.josehector.scanid.domain.model.Document
import es.josehector.scanid.domain.repository.MobbScanRepository
import java.io.File

class MobbScanDataRepository(
    private val mobbScanNetworkDatasource: MobbScanNetworkDatasource
) : MobbScanRepository {
    override fun scanIdDocument(
        licenseId: String,
        documentType: String,
        front: File,
        back: File
    ): Either<Failure, Document> {
        mobbScanNetworkDatasource.scanIdDocument(
            licenseId = licenseId,
            documentType = documentType,
            front = front,
            back = back
        ).fold(
            {
                return Either.Left(it)
            }, { apiDocumentResponse ->
                apiDocumentResponse?.let {
                    return Either.Right(it.toDocument())
                } ?: kotlin.run {
                    return Either.Left(DomainFailure.NoData)
                }
            }
        )
    }
}