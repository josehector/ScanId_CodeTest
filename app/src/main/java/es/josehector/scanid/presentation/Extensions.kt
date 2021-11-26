package es.josehector.scanid.presentation

import android.graphics.Bitmap
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun View.showOrGone(show: Boolean) {
    this.visibility = when(show) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

fun InputStream.getBytes(): ByteArray {
    val byteBuff = ByteArrayOutputStream()
    val buffSize = 1024
    val buff = ByteArray(buffSize)
    var len = 0
    while (this.read(buff).also { len = it } != -1) {
        byteBuff.write(buff, 0, len)
    }
    return byteBuff.toByteArray()
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
