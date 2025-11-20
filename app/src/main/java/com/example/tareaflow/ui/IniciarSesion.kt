package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.navigation.NavController
import com.example.tareaflow.R
import com.example.tareaflow.viewmodel.UsuarioViewModel
import com.example.tareaflow.viewmodel.TareaViewModel
import kotlinx.coroutines.launch

@Composable
fun IniciarSesion(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    tareaViewModel: TareaViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var mostrarErrores by remember { mutableStateOf(false) }
    var mostrarContraseña by remember { mutableStateOf(false) }
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
            contentDescription = "Logo de la app",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Correo electrónico", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Black) },
            singleLine = true,
            isError = mostrarErrores && usuario.isBlank(),
            supportingText = {
                if (mostrarErrores && usuario.isBlank()) {
                    Text("Campo Obligatorio", color = MaterialTheme.colorScheme.error)
                }
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
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
            singleLine = true,
            isError = mostrarErrores && contraseña.isBlank(),
            supportingText = {
                if (mostrarErrores && contraseña.isBlank()) {
                    Text("Campo Obligatorio", color = MaterialTheme.colorScheme.error)
                }
            },
            visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    mostrarErrores = true
                    if (usuario.isBlank() || contraseña.isBlank()) {
                        error = "Revisa los campos obligatorios"
                    } else {
                        try {
                            val exito = usuarioViewModel.iniciarSesion(usuario, contraseña)
                            if (exito) {
                                error = ""
                                navController.navigate("pantallaInicio")
                            } else {
                                error = "Correo o contraseña incorrectos"
                            }
                        } catch (e: Exception) {
                            error = "Error al iniciar sesión: ${e.message}"
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
            Text("Entrar")
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
}
