package es.josehector.scanid.domain.error

import com.rocket.core.domain.error.Failure

sealed class DomainFailure(val msg: String? = null): Failure.FeatureFailure(data = msg) {
    class MissingParameters(msg: String?) : DomainFailure(msg)
    object NoData: DomainFailure(msg = "NoData")
}