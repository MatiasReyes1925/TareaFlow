package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun PantallaVerTarea(
    navController: NavController,
    tareaId: Int,
    tareaViewModel: TareaViewModel
) {
    val tarea by tareaViewModel.obtenerTareaPorId(tareaId).collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        if (tarea != null) {
            Text("Título: ${tarea!!.titulo}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Descripción: ${tarea!!.detalle}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Categoría: ${tarea!!.categoria}")
            Spacer(modifier = Modifier.height(16.dp))

            tarea!!.fotoUri?.let { uri ->
                Text("Foto: $uri")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("pantallaInicio") },
                    modifier = Modifier
                        .height(32.dp)
                        .width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("Volver", fontSize = 14.sp)
                }
            }


        } else {
            Text("No se encontró la tarea")
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
