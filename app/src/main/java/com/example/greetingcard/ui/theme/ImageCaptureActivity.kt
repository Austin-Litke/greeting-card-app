package com.example.greetingcard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageCaptureActivity : ComponentActivity() {
    private lateinit var captureImageLauncher: ActivityResultLauncher<Intent>
    private var capturedBitmap: Bitmap? by mutableStateOf(null)
    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkCameraPermission()

        setContent {
            ImageCaptureScreen(
                onCaptureImageClick = {
                    captureImage()
                },
                capturedBitmap = capturedBitmap
            )
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            initializeImageCapture()
        }
    }

    private fun initializeImageCapture() {
        captureImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = BitmapFactory.decodeFile(photoUri.path)
                capturedBitmap = bitmap
            }
        }
    }

    private fun captureImage() {
        val file = createImageFile()
        if (file != null) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }
            captureImageLauncher.launch(intent)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}", /* prefix */
            ".jpg",             /* suffix */
            storageDir          /* directory */
        ).apply {
            photoUri = FileProvider.getUriForFile(
                this@ImageCaptureActivity,
                "${applicationContext.packageName}.fileprovider",
                this
            )
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
}

@Composable
fun ImageCaptureScreen(
    onCaptureImageClick: () -> Unit,
    capturedBitmap: Bitmap?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onCaptureImageClick) {
            Text(text = "Capture Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        capturedBitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "Captured Image")
        }
    }
}
