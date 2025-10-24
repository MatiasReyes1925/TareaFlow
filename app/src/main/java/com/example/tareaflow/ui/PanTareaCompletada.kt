package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.model.EstadoTarea
import com.example.tareaflow.viewmodel.TareaViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun PanTareaCompletada(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    tareaViewModel: TareaViewModel
) {
    val tareasCompletadas by remember {
        derivedStateOf {
            tareaViewModel.tareas.filter { it.estado == EstadoTarea.COMPLETADA }
        }
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Text("Tareas completadas", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(tareasCompletadas) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = tarea.titulo, fontSize = 16.sp)
                            Text(text = "Categoría: ${tarea.categoria}", fontSize = 14.sp)
                            Text(text = tarea.detalle, fontSize = 14.sp)
                            Text(text = "Estado: ${tarea.estado}", fontSize = 14.sp)
                        }
                        IconButton(onClick = {
                            scope.launch {
                                tareaViewModel.eliminarTarea(tarea)
                            }
                        }) {
                            Text("❌", fontSize = 20.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("pantallaInicio") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Volver", fontSize = 18.sp)
        }
    }
}
