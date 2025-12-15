package com.example.tareaflow.ui

import android.graphics.Bitmap
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
import androidx.navigation.NavController
import com.example.tareaflow.viewmodel.TareaViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarTarea(
    navController: NavController,
    tareaViewModel: TareaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val categorias = listOf(
        "Trabajo", "Personal", "Estudio", "Salud", "Finanzas",
        "Hogar", "Deporte", "Viajes", "Compras", "Otros"
    )
    var categoriaSeleccionada by remember { mutableStateOf(categorias.first()) }
    var categoriaPersonalizada by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var fotoTarea by remember { mutableStateOf<Bitmap?>(null) }

    val usuarioActual by usuarioViewModel.usuarioActual.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agregar Nueva Tarea", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título", color = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = categoriaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría", color = Color.Black) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria, color = Color.Black) },
                        onClick = {
                            categoriaSeleccionada = categoria
                            expanded = false
                        }
                    )
                }
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

        Camara(
            bitmap = fotoTarea,
            onFotoTomada = { nuevaFoto -> fotoTarea = nuevaFoto }
        )

        Button(
            onClick = {
                if (titulo.isNotBlank()) {
                    val categoriaFinal = if (categoriaSeleccionada == "Otros" && categoriaPersonalizada.isNotBlank()) {
                        categoriaPersonalizada
                    } else {
                        categoriaSeleccionada
                    }
                    usuarioActual?.id?.let { idUsuario ->
                        scope.launch {
                            tareaViewModel.agregar(
                                titulo = titulo,
                                detalle = descripcion,
                                categoria = categoriaFinal,
                                idUsuario = idUsuario,
                                foto = fotoTarea
                            )
                            navController.navigate("pantallaInicio") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        ) {
            Text("Guardar Tarea")
        }

        TextButton(
            onClick = {
                navController.navigate("pantallaInicio") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF1976D2)
            )
        ) {
            Text("Volver")
        }
    }
}
