package com.example.tareaflow.ui

import android.graphics.Bitmap
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

@Composable
fun AgregarTarea(
    navController: NavController,
    tareaViewModel: TareaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")
    var categoriaSeleccionada by remember { mutableStateOf("Estudio") }
    var categoriaPersonalizada by remember { mutableStateOf("") }

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

        Text("Categoría", fontSize = 16.sp, modifier = Modifier.align(Alignment.Start))
        categorias.forEach { categoria ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Start)
            ) {
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
                                idUsuario = idUsuario
                            )
                            // Navegar y limpiar el backstack
                            navController.navigate("pantallaInicio") {
                                popUpTo("pantallaInicio") { inclusive = true }
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
            onClick = { navController.navigate("pantallaInicio") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF1976D2)
            )
        ) {
            Text("Volver")
        }
    }
}
