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
import kotlinx.coroutines.launch

@Composable
fun EditarTarea(
    tareaId: Int,
    navController: NavController,
    tareaViewModel: TareaViewModel
) {
    val tarea = tareaViewModel.tareas.find { it.id == tareaId }
    val scope = rememberCoroutineScope()

    tarea?.let {
        var nuevoTitulo by remember { mutableStateOf(it.titulo) }
        var nuevoDetalle by remember { mutableStateOf(it.detalle) }

        val categorias = listOf("Estudio", "Trabajo", "Personal", "Otros")
        val esCategoriaPersonalizada = it.categoria !in categorias
        var categoriaSeleccionada by remember { mutableStateOf(if (esCategoriaPersonalizada) "Otros" else it.categoria) }
        var categoriaPersonalizada by remember { mutableStateOf(if (esCategoriaPersonalizada) it.categoria else "") }

        var fotoEditada by remember { mutableStateOf<Bitmap?>(null) }

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

            Spacer(modifier = Modifier.height(16.dp))

            Camara(
                bitmap = fotoEditada,
                onFotoTomada = { nuevaFoto -> fotoEditada = nuevaFoto }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        val categoriaFinal = if (categoriaSeleccionada == "Otros" && categoriaPersonalizada.isNotBlank()) {
                            categoriaPersonalizada
                        } else {
                            categoriaSeleccionada
                        }

                        tareaViewModel.actualizar(
                            tareaOriginal = it,
                            nuevoTitulo = nuevoTitulo,
                            nuevaCategoria = categoriaFinal,
                            nuevoDetalle = nuevoDetalle
                        )
                        navController.navigate("pantallaInicio")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                )
            ) {
                Text("Guardar cambios", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = { navController.navigate("pantallaInicio") },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF1976D2)
                    )
                ) {
                    Text("Volver", fontSize = 16.sp)
                }
            }
        }
    }
}
