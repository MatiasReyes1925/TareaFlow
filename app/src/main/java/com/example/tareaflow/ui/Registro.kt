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
import androidx.navigation.NavController
import com.example.tareaflow.R
import com.example.tareaflow.viewmodel.UsuarioFormViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun Registro(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    formViewModel: UsuarioFormViewModel = remember { UsuarioFormViewModel() }
) {
    val formulario by formViewModel.form.collectAsState()
    val errores by formViewModel.errors.collectAsState()
    val scope = rememberCoroutineScope()

    var confirmar by remember { mutableStateOf("") }
    var confirmarValidado by remember { mutableStateOf(false) }
    var abrirModal by remember { mutableStateOf(false) }
    var errorGeneral by remember { mutableStateOf("") }

    var mostrarContraseña by remember { mutableStateOf(false) }
    var mostrarConfirmar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logousuario),
            contentDescription = "Logo de registro",
            modifier = Modifier
                .size(150.dp)
                .padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = formulario.nombre,
            onValueChange = { formViewModel.onNombreChange(it) },
            label = { Text("Nombre completo", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Black) },
            isError = errores.nombre != null,
            supportingText = {
                errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formulario.correo,
            onValueChange = { formViewModel.onCorreoChange(it) },
            label = { Text("Correo electrónico", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Black) },
            isError = errores.correo != null,
            supportingText = {
                errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formulario.contraseña,
            onValueChange = { formViewModel.onContraseñaChange(it) },
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
            isError = errores.contraseña != null,
            supportingText = {
                errores.contraseña?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmar,
            onValueChange = {
                confirmar = it
                confirmarValidado = true
            },
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
            isError = confirmarValidado && confirmar != formulario.contraseña,
            supportingText = {
                if (confirmarValidado && confirmar != formulario.contraseña) {
                    Text("Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error)
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
                    confirmarValidado = true
                    val valido = formViewModel.isFormValid(errores)

                    if (!valido) {
                        errorGeneral = "Revisa los campos marcados"
                    } else if (formulario.contraseña != confirmar) {
                        errorGeneral = "Las contraseñas no coinciden"
                    } else {
                        val exito = usuarioViewModel.registrarUsuario(formulario)
                        if (exito) {
                            abrirModal = true
                            errorGeneral = ""
                        } else {
                            errorGeneral = "Este correo ya está registrado"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        TextButton(onClick = { navController.navigate("pantallaPrincipal") }) {
            Text("Volver")
        }
    }

    if (abrirModal) {
        AlertDialog(
            onDismissRequest = { abrirModal = false },
            title = { Text("Confirmación registro") },
            text = { Text("¡¡Registro exitoso!!", fontSize = 15.sp) },
            confirmButton = {
                Button(onClick = {
                    abrirModal = false
                    navController.navigate("pantallaPrincipal")
                }) {
                    Text("OK")
                }
            }
        )
    }
}