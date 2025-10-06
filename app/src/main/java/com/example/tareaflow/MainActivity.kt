package com.example.tareaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tareaflow.ui.Navegacion
import com.example.tareaflow.ui.theme.TareaFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaFlowTheme {
                Navegacion()
            }
        }
    }
}
