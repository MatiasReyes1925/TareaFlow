package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun EditarTarea(
    tareaId: Int,
    navController: NavController,
    tareaViewModel: TareaViewModel
) {
    val tarea = tareaViewModel.tareas.find { it.id == tareaId }

    if (tarea != null) {
        var nuevoTitulo by remember { mutableStateOf(tarea.titulo) }
        var nuevoDetalle by remember { mutableStateOf(tarea.detalle) }

        val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")
        val esCategoriaPersonalizada = tarea.categoria !in categorias
        var categoriaSeleccionada by remember { mutableStateOf(if (esCategoriaPersonalizada) "Otros" else tarea.categoria) }
        var categoriaPersonalizada by remember { mutableStateOf(if (esCategoriaPersonalizada) tarea.categoria else "") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Editar tarea", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nuevoTitulo,
                onValueChange = { nuevoTitulo = it },
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
                value = nuevoDetalle,
                onValueChange = { nuevoDetalle = it },
                label = { Text("Detalle", color = Color.Black) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val categoriaFinal = if (categoriaSeleccionada == "Otros" && categoriaPersonalizada.isNotBlank()) {
                        categoriaPersonalizada
                    } else {
                        categoriaSeleccionada
                    }

                    tareaViewModel.actualizar(
                        tareaOriginal = tarea,
                        nuevoTitulo = nuevoTitulo,
                        nuevaCategoria = categoriaFinal,
                        nuevoDetalle = nuevoDetalle
                    )
                    navController.navigate("pantallaInicio")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            TextButton(onClick = { navController.navigate("pantallaInicio") }) {
                Text("Volver")
            }
        }
    } else {
        Text("Tarea no encontrada", fontSize = 18.sp)
    }
}
