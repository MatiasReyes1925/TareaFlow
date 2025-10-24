package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.R
import com.example.tareaflow.viewmodel.UsuarioViewModel

@Composable
fun PantallaPerfil(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuario by usuarioViewModel.usuarioActual.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logousuario),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(150.dp)
                .padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Perfil de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usuario?.nombre ?: "",
            onValueChange = {},
            label = { Text("Nombre completo", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Black) },
            enabled = false,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = usuario?.correo ?: "",
            onValueChange = {},
            label = { Text("Correo electrónico", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Black) },
            enabled = false,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                navController.navigate("pantallaInicio")
            }
        ) {
            Text("Volver al inicio")
        }
        TextButton(
            onClick = {
                usuarioViewModel.cerrarSesion()
                navController.navigate("pantallaPrincipal") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}
