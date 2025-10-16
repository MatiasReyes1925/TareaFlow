package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.R

data class Tarea(val titulo: String, val completada: Boolean)

@Composable
fun PantallaInicio(navController: NavController) {
    var menuExpandido by remember { mutableStateOf(false) }
    var categoriasExpandido by remember { mutableStateOf(false) }

    val tareasPendientes = listOf(
        Tarea("Estudiar para el examen", false),
        Tarea("Enviar informe de trabajo", false),
        Tarea("Comprar materiales", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { menuExpandido = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menú de tareas")
            }

            DropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = { menuExpandido = false }
            ) {
                DropdownMenuItem(text = { Text("Ver todas las tareas") }, onClick = { menuExpandido = false })
                DropdownMenuItem(text = { Text("Tareas no completadas") }, onClick = { menuExpandido = false })
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logotareaflow),
            contentDescription = "Logo de TareaFlow",
            modifier = Modifier
                .size(180.dp)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Tareas pendientes", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tareasPendientes.filter { !it.completada }) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = tarea.titulo, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("editarTarea")
                                },
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text("Editar", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("agregarTarea") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Agregar nueva tarea", fontSize = 18.sp)
        }

        TextButton(onClick = { navController.navigate("pantallaPrincipal")
        }) {
            Text("Cerrar sesión", fontSize = 16.sp)
        }
    }
}

@Composable
fun CategoriaBoton(texto: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(texto, fontSize = 18.sp)
    }
}
