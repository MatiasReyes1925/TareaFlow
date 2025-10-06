package com.example.tareaflow.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "pantallaPrincipal") {
        composable("pantallaPrincipal") { PantallaPrincipal(navController) }
        composable("iniciarSesion") { IniciarSesion(navController) }
        composable("registro") { Registro(navController) }

    }
}
