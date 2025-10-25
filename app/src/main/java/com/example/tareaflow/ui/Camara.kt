package com.example.tareaflow.ui

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.File

@Composable
fun Camara(
    bitmap: Bitmap?,
    onFotoTomada: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && imageUri != null) {
            val inputStream = context.contentResolver.openInputStream(imageUri!!)
            val exif = ExifInterface(inputStream!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val bitmapOriginal = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

            val rotatedBitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    Matrix().apply { postRotate(90f) }.let {
                        Bitmap.createBitmap(bitmapOriginal, 0, 0, bitmapOriginal.width, bitmapOriginal.height, it, true)
                    }
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    Matrix().apply { postRotate(180f) }.let {
                        Bitmap.createBitmap(bitmapOriginal, 0, 0, bitmapOriginal.width, bitmapOriginal.height, it, true)
                    }
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    Matrix().apply { postRotate(270f) }.let {
                        Bitmap.createBitmap(bitmapOriginal, 0, 0, bitmapOriginal.width, bitmapOriginal.height, it, true)
                    }
                }
                else -> bitmapOriginal
            }

            onFotoTomada(rotatedBitmap)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val file = File.createTempFile("tarea_foto", ".jpg", context.cacheDir)
            file.deleteOnExit()
            val uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                file
            )
            imageUri = uri
            takePictureLauncher.launch(uri)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Tomar Foto")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto de tarea",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
        }
    }
}
