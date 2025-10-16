package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AgregarTarea(navController: NavController) {
    var titulo by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text("Agregar nueva tarea", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Categoría", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        categorias.forEach { categoria ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = categoriaSeleccionada == categoria,
                    onClick = { categoriaSeleccionada = categoria }
                )
                Text(text = categoria, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "Escribe tu tarea",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Detalle de la tarea") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* sin funcionalidad */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Guardar Tarea", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("pantallaInicio") }) {
            Text("Volver", fontSize = 16.sp)
        }
    }
}
