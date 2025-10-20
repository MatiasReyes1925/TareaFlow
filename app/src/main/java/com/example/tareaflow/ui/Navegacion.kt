package com.example.tareaflow.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareaflow.viewmodel.UsuarioViewModel
import com.example.tareaflow.viewmodel.TareaViewModel
import com.example.tareaflow.ui.EditarTarea

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Navegacion(usuarioViewModel: UsuarioViewModel) {
    val navController = rememberNavController()
    val tareaViewModel: TareaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "pantallaPrincipal") {
        composable("pantallaPrincipal") { PantallaPrincipal(navController) }
        composable("iniciarSesion") { IniciarSesion(navController, usuarioViewModel) }
        composable("registro") { Registro(navController, usuarioViewModel) }
        composable("pantallaInicio") { PantallaInicio(navController, usuarioViewModel, tareaViewModel) }
        composable("agregarTarea") { AgregarTarea(navController, tareaViewModel) }
        composable("editarTarea/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) { EditarTarea(id, navController, tareaViewModel) } }

        composable("pantallaCompletadas") { PanTareaCompletada(navController, usuarioViewModel, tareaViewModel) }

    }
}
