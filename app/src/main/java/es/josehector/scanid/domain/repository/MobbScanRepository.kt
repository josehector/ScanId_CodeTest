package es.josehector.scanid.domain.repository

import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import es.josehector.scanid.domain.model.Document
import java.io.File

interface MobbScanRepository {

 fun scanIdDocument(licenseId: String, documentType: String, front: File, back: File): Either<Failure, Document>
}