package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun TakePictureScreen() {
    val ctx = LocalContext.current
    val file = createImageFile(ctx)
    val uri: Uri = FileProvider.getUriForFile(
        ctx,
        "br.com.nglauber.jetpackcomposeplayground.provider",
        file
    )
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            result.value = imageFromResult(ctx, uri)
        }
    }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { launcher.launch(uri) }) {
            Text(text = "Take a picture")
        }
        result.value?.let { image ->
            Image(image.asImageBitmap(), contentDescription = null)
        }
    }
}

private fun imageFromResult(context: Context, uri: Uri): Bitmap? {
    return try {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
        val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return bitmap
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(IOException::class)
private fun createImageFile(context: Context): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}