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
import com.example.tareaflow.model.EstadoTarea
import com.example.tareaflow.viewmodel.TareaViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaInicio(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    tareaViewModel: TareaViewModel
) {
    var menuExpandido by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val usuarioActual by usuarioViewModel.usuarioActual.collectAsState()

    LaunchedEffect(usuarioActual) {
        usuarioActual?.id?.let {
            tareaViewModel.cargarTareas(it)
        }
    }

    val tareasPendientes by remember {
        derivedStateOf {
            tareaViewModel.tareas.filter { it.estado == EstadoTarea.PENDIENTE }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { menuExpandido = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menú de tareas")
            }

            IconButton(onClick = { navController.navigate("pantallaPerfil") }) {
                Image(
                    painter = painterResource(id = R.drawable.logousuario),
                    contentDescription = "Ir al perfil",
                    modifier = Modifier.size(36.dp)
                )
            }

            DropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = { menuExpandido = false }
            ){
                DropdownMenuItem(
                    text = { Text("Ver tareas completadas") },
                    onClick = {
                        menuExpandido = false
                        navController.navigate("pantallaCompletadas")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Cerrar sesión") },
                    onClick = {
                        menuExpandido = false
                        usuarioViewModel.cerrarSesion()
                        navController.navigate("pantallaPrincipal") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
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

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(tareasPendientes) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = tarea.titulo, fontSize = 16.sp)
                        Text(text = "Categoría: ${tarea.categoria}", fontSize = 14.sp)
                        Text(text = tarea.detalle, fontSize = 14.sp)
                        Text(text = "Estado: ${tarea.estado}", fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        tareaViewModel.marcarComoCompletada(tarea)
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Text("✔️", fontSize = 20.sp)
                            }

                            IconButton(
                                onClick = {
                                    navController.navigate("editarTarea/${tarea.id}")
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Text("✏️", fontSize = 20.sp)
                            }

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        tareaViewModel.eliminarTarea(tarea)
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Text("❌", fontSize = 20.sp)
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
    }
}
