package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.R
import com.example.tareaflow.viewmodel.UsuarioViewModel

@Composable
fun Registro(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var abrirModal by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

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
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmar,
            onValueChange = { confirmar = it },
            label = { Text("Confirmar contraseña", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black) },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
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
                if (contraseña != confirmar) {
                    error = "Las contraseñas no coinciden"
                } else {
                    val exito = usuarioViewModel.registrar(nombre, correo, contraseña)
                    if (exito) {
                        abrirModal = true
                        error = ""
                    } else {
                        error = "Este correo ya está registrado"
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
