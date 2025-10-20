package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun IniciarSesion(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logousuario),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .size(150.dp)
                .padding(top = 16.dp)
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
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
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
                val exito = usuarioViewModel.iniciarSesion(usuario, contraseña)
                if (exito) {
                    error = ""
                    navController.navigate("pantallaInicio")
                } else {
                    error = "Correo o contraseña incorrectos"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        TextButton(onClick = { navController.navigate("pantallaPrincipal") }) {
            Text("Volver")
        }
    }
}
