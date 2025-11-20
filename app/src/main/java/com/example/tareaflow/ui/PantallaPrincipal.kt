package com.example.tareaflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
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
            TextButton(
                onClick = { navController.navigate("registro") },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF1976D2)
                )
            ) {
                Text("Regístrate aquí", fontSize = 16.sp)
            }
        }
    }
}
