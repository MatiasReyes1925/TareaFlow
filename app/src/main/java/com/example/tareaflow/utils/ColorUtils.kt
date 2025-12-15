package com.example.tareaflow.utils

import androidx.compose.ui.graphics.Color

object ColorUtils {
    fun getCategoryColor(categoria: String): Color {
        return when (categoria) {
            "Trabajo"   -> Color(0xFFFF0000)
            "Personal"  -> Color(0xFF0000FF)
            "Estudio"   -> Color(0xFFFFD700)
            "Salud"     -> Color(0xFF00FF00)
            "Finanzas"  -> Color(0xFF8B00FF)
            "Hogar"     -> Color(0xFF00CED1)
            "Deporte"   -> Color(0xFFFFA500)
            "Viajes"    -> Color(0xFF008080)
            "Compras"   -> Color(0xFFFF1493)
            "Otros"     -> Color.Gray
            else        -> Color.DarkGray
        }
    }
}
