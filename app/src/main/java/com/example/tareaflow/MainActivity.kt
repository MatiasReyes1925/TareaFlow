package com.example.tareaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.tareaflow.database.AppDatabase
import com.example.tareaflow.repository.TareaRepository
import com.example.tareaflow.repository.UsuarioRepository
import com.example.tareaflow.ui.Navegacion
import com.example.tareaflow.ui.theme.TareaFlowTheme
import com.example.tareaflow.viewmodel.TareaViewModel
import com.example.tareaflow.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaFlowTheme {
                val context = LocalContext.current
                val database = AppDatabase.obtenerInstancia(context)

                val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }
                val usuarioViewModel = remember { UsuarioViewModel(usuarioRepository) }

                val tareaRepository = remember { TareaRepository(database.tareaDao()) }
                val tareaViewModel = remember { TareaViewModel(tareaRepository) }

                Navegacion(usuarioViewModel, tareaViewModel)
            }
        }
    }
}
