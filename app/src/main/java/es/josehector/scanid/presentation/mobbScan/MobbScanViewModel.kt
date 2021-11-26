package es.josehector.scanid.presentation.mobbScan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rocket.android.core.viewmodel.BaseViewModel
import com.rocket.android.core.viewmodel.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import es.josehector.scanid.domain.model.Document
import es.josehector.scanid.domain.usecase.ScanIdDocumentUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MobbScanViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val scanIdDocumentUseCase: ScanIdDocumentUseCase
) : BaseViewModel(dispatcher = ioDispatcher) {

    private val _onDocumentChanged = MutableLiveData<Result<Document>>()
    val onDocumentChanged: LiveData<Result<Document>> = _onDocumentChanged

    /*private val _onDocumentError = MutableLiveData<Boolean>()
    val onDocumentError: LiveData<Boolean> = _onDocumentError*/

    fun changeText(fileFront: File, fileBack: File) {
        _onDocumentChanged.postValue(Result.Loading())
        launch {
            scanIdDocumentUseCase(
                ScanIdDocumentUseCase.Params.forDocumentInfo(
                    licenseId = "mobbscan-challenge",
                    documentType = "ESP",
                    front = fileFront,
                    back = fileBack
                )
            ).fold(
                {
                    _onDocumentChanged.postValue(Result.Error(it))
                },
                {
                    _onDocumentChanged.postValue(Result.Success(it))
                }

            )
        }
    }
}