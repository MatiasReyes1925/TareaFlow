package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tareaflow.R

@Composable
fun IniciarSesion(navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }

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
            label = { Text("Nombre Usuario") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "Ícono de usuario")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Ícono de contraseña")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                println("Usuario: $usuario, Contraseña: $contraseña")
                navController.navigate("pantallaInicio")
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
