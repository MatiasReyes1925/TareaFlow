package com.example.tareaflow.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun PantallaVerTarea(
    navController: NavController,
    tareaId: Int,
    tareaViewModel: TareaViewModel
) {
    val tarea = tareaViewModel.obtenerTareaPorId(tareaId).collectAsState(initial = null).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        if (tarea != null) {
            Text(
                text = "Título: ${tarea.titulo}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción: ${tarea.detalle}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Categoría: ${tarea.categoria}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (!tarea.fotoUri.isNullOrEmpty()) {
                val uri = Uri.parse("file://${tarea.fotoUri}")
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = "Foto de la tarea",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text(
                    text = "Sin foto adjunta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("pantallaInicio") },
                    modifier = Modifier
                        .height(40.dp)
                        .width(140.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("Volver", fontSize = 16.sp)
                }
            }
        } else {
            // --- Caso: tarea no encontrada ---
            Text(
                text = "No se encontró la tarea",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("pantallaInicio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                )
            ) {
                Text("Volver", fontSize = 18.sp)
            }
        }
    }
}
