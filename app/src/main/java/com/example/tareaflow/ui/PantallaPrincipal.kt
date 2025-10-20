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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaflow.R

@Composable
fun PantallaPrincipal(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Image(
            painter = painterResource(id = R.drawable.logotareaflow),
            contentDescription = "Logo de TareaFlow",
            modifier = Modifier.size(400.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Bienvenidos a TareaFlow",
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("iniciarSesion") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 18.sp)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta?", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = { navController.navigate("registro") }) {
                Text("Regístrate aquí", fontSize = 16.sp)
            }
        }
    }
}
