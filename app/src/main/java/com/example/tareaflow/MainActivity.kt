package com.example.tareaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.example.tareaflow.repository.UsuarioRepository
import com.example.tareaflow.ui.Navegacion
import com.example.tareaflow.ui.theme.TareaFlowTheme
import com.example.tareaflow.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaFlowTheme {
                val usuarioRepository = remember { UsuarioRepository() }
                val usuarioViewModel = remember { UsuarioViewModel(usuarioRepository) }
                Navegacion(usuarioViewModel)
            }
        }
    }
}

