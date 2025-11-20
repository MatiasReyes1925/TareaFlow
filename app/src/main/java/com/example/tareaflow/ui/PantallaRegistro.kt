package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tareaflow.R
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.viewmodel.FormUsuarioViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaRegistro(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    formViewModel: FormUsuarioViewModel = viewModel()
) {
    val formulario by formViewModel.formulario.collectAsState()
    val errores by formViewModel.errores.collectAsState()

    var confirmar by remember { mutableStateOf("") }
    var mostrarContraseña by remember { mutableStateOf(false) }
    var mostrarConfirmar by remember { mutableStateOf(false) }
    var abrirModal by remember { mutableStateOf(false) }
    var errorGeneral by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logousuario),
            contentDescription = "Logo de registro",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = formulario.nombre,
            onValueChange = { formViewModel.onNombreChange(it) },
            label = { Text("Nombre completo", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formulario.correo,
            onValueChange = { formViewModel.onCorreoChange(it) },
            label = { Text("Correo electrónico", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formulario.contrasena,
            onValueChange = { formViewModel.onContrasenaChange(it) },
            label = { Text("Contraseña", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black) },
            trailingIcon = {
                IconButton(onClick = { mostrarContraseña = !mostrarContraseña }) {
                    Icon(
                        imageVector = if (mostrarContraseña) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Mostrar/Ocultar contraseña"
                    )
                }
            },
            visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmar,
            onValueChange = { confirmar = it },
            label = { Text("Confirmar contraseña", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black) },
            trailingIcon = {
                IconButton(onClick = { mostrarConfirmar = !mostrarConfirmar }) {
                    Icon(
                        imageVector = if (mostrarConfirmar) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Mostrar/Ocultar confirmación"
                    )
                }
            },
            visualTransformation = if (mostrarConfirmar) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorGeneral.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorGeneral, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    when {
                        formulario.nombre.isBlank() || formulario.correo.isBlank() || formulario.contrasena.isBlank() || confirmar.isBlank() -> {
                            errorGeneral = "Debes completar todos los campos"
                        }
                        errores.correo != null -> {
                            errorGeneral = "Correo inválido"
                        }
                        formulario.contrasena != confirmar -> {
                            errorGeneral = "Las contraseñas no coinciden"
                        }
                        else -> {
                            try {
                                val nuevoUsuario = Usuario(
                                    nombre = formulario.nombre,
                                    correo = formulario.correo,
                                    contrasena = formulario.contrasena
                                )
                                val exito = usuarioViewModel.registrarUsuario(nuevoUsuario)
                                if (exito) {
                                    abrirModal = true
                                    errorGeneral = ""
                                } else {
                                    errorGeneral = "Este correo ya está registrado"
                                }
                            } catch (e: Exception) {
                                errorGeneral = "Error al registrar: ${e.message}"
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
            Text("Registrarse")
        }

        TextButton(
            onClick = { navController.navigate("pantallaPrincipal") },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF1976D2)
            )
        ) {
            Text("Volver")
        }
    }

    if (abrirModal) {
        AlertDialog(
            onDismissRequest = { abrirModal = false },
            title = { Text("Confirmación registro") },
            text = { Text("Registro exitoso!!", fontSize = 15.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        abrirModal = false
                        navController.navigate("pantallaPrincipal")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            }
        )
    }
}
