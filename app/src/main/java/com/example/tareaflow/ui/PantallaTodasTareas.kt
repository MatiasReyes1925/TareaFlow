package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.utils.ColorUtils
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun PantallaTodasTareas(
    navController: NavController,
    tareaViewModel: TareaViewModel
) {
    val tareas = tareaViewModel.tareas

    LaunchedEffect(Unit) {
        tareaViewModel.cargarDesdeXano()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text("Ver todas las tareas", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tareas) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorUtils.getCategoryColor(tarea.categoria)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Título: ${tarea.titulo}",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            "Detalle: ${tarea.detalle}",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            "Categoría: ${tarea.categoria}",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        TextButton(
            onClick = { navController.navigate("pantallaInicio") },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF1976D2)
            )
        ) {
            Text("Volver", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
