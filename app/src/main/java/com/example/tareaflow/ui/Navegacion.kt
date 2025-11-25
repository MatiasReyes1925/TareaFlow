package com.example.tareaflow.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareaflow.viewmodel.UsuarioViewModel
import com.example.tareaflow.viewmodel.TareaViewModel

@Composable
fun Navegacion(usuarioViewModel: UsuarioViewModel, tareaViewModel: TareaViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pantallaPrincipal") {
        composable("pantallaPrincipal") { PantallaPrincipal(navController) }
        composable("iniciarSesion") { IniciarSesion(navController, usuarioViewModel, tareaViewModel) }
        composable("registro") { PantallaRegistro(navController, usuarioViewModel) }
        composable("pantallaInicio") { PantallaInicio(navController, usuarioViewModel, tareaViewModel) }
        composable("agregarTarea") { AgregarTarea(navController, tareaViewModel, usuarioViewModel) }
        composable("editarTarea/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) EditarTarea(id, navController, tareaViewModel)
        }
        composable("pantallaCompletadas") { PanTareaCompletada(navController, usuarioViewModel, tareaViewModel) }
        composable("pantallaPerfil") { PantallaPerfil(navController, usuarioViewModel) }
        composable("verTarea/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) PantallaVerTarea(navController, id, tareaViewModel)
        }
    }
}

