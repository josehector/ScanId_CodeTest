package es.josehector.scanid.presentation.mobbScan

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rocket.android.core.viewmodel.Result
import dagger.hilt.android.AndroidEntryPoint
import es.josehector.scanid.databinding.ActivityMainBinding
import es.josehector.scanid.domain.model.Document
import es.josehector.scanid.domain.model.GenderType
import es.josehector.scanid.presentation.getBytes
import es.josehector.scanid.presentation.showOrGone
import es.josehector.scanid.presentation.toByteArray
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class MobbScanActivity : AppCompatActivity() {

    companion object {
        const val REQ_FRONT_GALLERY = 1
        const val REQ_BACK_GALLERY = 2
        const val REQ_FRONT_CAMERA = 3
        const val REQ_BACK_CAMERA = 4

        const val FRONT_FILE_NAME = "front.jpg"
        const val BACK_FILE_NAME = "back.jpg"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MobbScanViewModel

    private var fileFront: File? = null
    private var fileBack: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MobbScanViewModel::class.java)

        initObservers()
        initView()
    }

    private fun initObservers() {
        viewModel.onDocumentChanged.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.pbLoading.showOrGone(false)
                    binding.documentInfo.root.showOrGone(true)
                    binding.notDocumentDetected.root.showOrGone(false)
                    setDocumentInfo(result.data)
                }
                is Result.Error -> {
                    binding.pbLoading.showOrGone(false)
                    binding.documentInfo.root.showOrGone(false)
                    binding.notDocumentDetected.root.showOrGone(true)
                }
                is Result.Loading -> {
                    binding.documentInfo.root.showOrGone(false)
                    binding.notDocumentDetected.root.showOrGone(false)
                    binding.pbLoading.showOrGone(true)
                }
            }
        }
    }

    private fun initView() {
        binding.documentInfo.root.showOrGone(false)
        binding.notDocumentDetected.root.showOrGone(false)
        binding.ivCamera.isSelected = true

        binding.btFront.setOnClickListener {
            when (binding.ivCamera.isSelected) {
                true -> takePicture(REQ_FRONT_CAMERA)
                false -> galleryChooser(REQ_FRONT_GALLERY)
            }
        }
        binding.btBack.setOnClickListener {
            when (binding.ivCamera.isSelected) {
                true -> takePicture(REQ_BACK_CAMERA)
                false -> galleryChooser(REQ_BACK_GALLERY)
            }
        }
        binding.ivCamera.setOnClickListener {
            binding.ivCamera.isSelected = true
            binding.ivGallery.isSelected = false

        }
        binding.ivGallery.setOnClickListener {
            binding.ivCamera.isSelected = false
            binding.ivGallery.isSelected = true
        }
        binding.btCheck.setOnClickListener{
            if (fileFront != null && fileBack != null) {
                viewModel.changeText(fileFront!!, fileBack!!)
            }
        }
    }

    private fun galleryChooser(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }

    private fun takePicture(requestCode: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, requestCode)
            }
        }
    }

    private fun setDocumentInfo(document: Document) {
        binding.documentInfo.apply {
            tvName.text = "${document.name} ${document.surname}"
            tvPersonalNumber.text = document.personalNumber
            tvDocumentType.text = document.documentNumber
            tvGender.text = when (document.gender) {
                GenderType.FEMALE -> "FEMALE"
                GenderType.MALE -> "MALE"
                GenderType.UNKNOWN -> "-"
            }
            tvBirthDate.text = document.dateOfBirth
            tvAddress.text = document.address
            tvCity.text = document.city
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            when(requestCode) {
                REQ_FRONT_GALLERY -> {
                    it.data?.let { uri ->
                        binding.ivFront.setImageURI(uri)
                        fileFront = getFileFromUri(uri, FRONT_FILE_NAME)
                    }
                }
                REQ_BACK_GALLERY -> {
                    it.data?.let { uri ->
                        binding.ivBack.setImageURI(uri)
                        fileBack = getFileFromUri(uri, BACK_FILE_NAME)
                    }
                }
                REQ_FRONT_CAMERA -> {
                    it.extras?.let { bundle ->
                        val bitmap: Bitmap = bundle.get("data") as Bitmap
                        binding.ivFront.setImageBitmap(bitmap)
                        fileFront = getFileFromByteArray(bitmap.toByteArray(), FRONT_FILE_NAME)
                    }
                }
                REQ_BACK_CAMERA -> {
                    it.extras?.let { bundle ->
                        val bitmap: Bitmap = bundle.get("data") as Bitmap
                        binding.ivBack.setImageBitmap(bitmap)
                        fileBack = getFileFromByteArray(bitmap.toByteArray(), BACK_FILE_NAME)
                    }
                }
                else -> {}
            }
        }
    }

    private fun getFileFromUri(uri: Uri, fileName: String): File {
        val inputStream = contentResolver.openInputStream(uri)
        val byteArray = inputStream?.getBytes() ?: ByteArray(0)
        return getFileFromByteArray(byteArray, fileName)
    }

    private fun getFileFromByteArray(byteArray: ByteArray, fileName: String): File {
        val file = File(externalCacheDir, fileName)
        val stream = FileOutputStream(file)
        stream.write(byteArray)
        stream.close()
        return file
    }
}