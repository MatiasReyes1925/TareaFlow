package com.example.tareaflow.ui

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.tareaflow.viewmodel.TareaViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AgregarTarea(navController: NavController, tareaViewModel: TareaViewModel) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")
    var categoriaSeleccionada by remember { mutableStateOf("Estudio") }
    var categoriaPersonalizada by remember { mutableStateOf("") }

    var fotoTarea by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && imageUri != null) {
            val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            fotoTarea = bmp
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

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Agregar Nueva Tarea", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título", color = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Categoría", fontSize = 16.sp)
        categorias.forEach { categoria ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = categoriaSeleccionada == categoria,
                    onClick = { categoriaSeleccionada = categoria }
                )
                Text(categoria)
            }
        }

        if (categoriaSeleccionada == "Otros") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = categoriaPersonalizada,
                onValueChange = { categoriaPersonalizada = it },
                label = { Text("Escribe tu categoría", color = Color.Black) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (fotoTarea != null) {
            Image(
                bitmap = fotoTarea!!.asImageBitmap(),
                contentDescription = "Foto de tarea",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
        }

        Button(onClick = {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Tomar foto de tarea")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (titulo.isNotBlank()) {
                    val categoriaFinal = if (categoriaSeleccionada == "Otros" && categoriaPersonalizada.isNotBlank()) {
                        categoriaPersonalizada
                    } else {
                        categoriaSeleccionada
                    }

                    scope.launch {
                        tareaViewModel.agregar(
                            titulo = titulo,
                            detalle = descripcion,
                            categoria = categoriaFinal,
                            idUsuario = 1 // ID temporal
                            // fotoTarea puede guardarse en base64 o archivo si se desea persistir
                        )
                        navController.navigate("pantallaInicio")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Tarea")
        }

        TextButton(onClick = { navController.navigate("pantallaInicio") }) {
            Text("Volver")
        }
    }
}
