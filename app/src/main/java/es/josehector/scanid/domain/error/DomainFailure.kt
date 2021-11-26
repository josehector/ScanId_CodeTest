package es.josehector.scanid.domain.error

import com.rocket.core.domain.error.Failure

/**
 * Domain model error to represent any exceptional situation
 *
 * TODO: add connection error situation
 */
sealed class DomainFailure(val msg: String? = null): Failure.FeatureFailure(data = msg) {
    class MissingParameters(msg: String?) : DomainFailure(msg)
    object NoData: DomainFailure(msg = "NoData")
}