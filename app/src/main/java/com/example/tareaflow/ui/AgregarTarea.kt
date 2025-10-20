package com.example.tareaflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun AgregarTarea(navController: NavController, tareaViewModel: TareaViewModel = viewModel()) {
    var titulo by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var categoriaPersonalizada by remember { mutableStateOf("") }
    var detalle by remember { mutableStateOf("") }
    val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")

    Column(modifier = Modifier.padding(24.dp)) {
        Text("Agregar nueva tarea", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la tarea", color = Color.Black) },
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

        // Campo adicional si se selecciona "Otros"
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
            value = detalle,
            onValueChange = { detalle = it },
            label = { Text("Detalle", color = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val categoriaFinal = if (categoriaSeleccionada == "Otros" && categoriaPersonalizada.isNotBlank()) {
                categoriaPersonalizada
            } else {
                categoriaSeleccionada
            }

            tareaViewModel.agregar(titulo, categoriaFinal, detalle)
            navController.navigate("pantallaInicio")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Tarea")
        }

        TextButton(onClick = { navController.navigate("pantallaInicio") }) {
            Text("Volver")
        }
    }
}
