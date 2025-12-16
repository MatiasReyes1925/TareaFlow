package com.example.tareaflow.utils

import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.junit.Assert.*

class ColorUtilsTest {

    @Test
    fun testCategoryColorForTrabajo() {
        val color = ColorUtils.getCategoryColor("Trabajo")
        assertEquals(Color(0xFFFF0000), color)
    }

    @Test
    fun testCategoryColorForPersonal() {
        val color = ColorUtils.getCategoryColor("Personal")
        assertEquals(Color(0xFF0000FF), color)
    }

    @Test
    fun testCategoryColorForEstudio() {
        val color = ColorUtils.getCategoryColor("Estudio")
        assertEquals(Color(0xFFFFD700), color)
    }

    @Test
    fun testCategoryColorForSalud() {
        val color = ColorUtils.getCategoryColor("Salud")
        assertEquals(Color(0xFF00FF00), color)
    }

    @Test
    fun testCategoryColorForFinanzas() {
        val color = ColorUtils.getCategoryColor("Finanzas")
        assertEquals(Color(0xFF8B00FF), color)
    }

    @Test
    fun testCategoryColorForHogar() {
        val color = ColorUtils.getCategoryColor("Hogar")
        assertEquals(Color(0xFF00CED1), color)
    }

    @Test
    fun testCategoryColorForDeporte() {
        val color = ColorUtils.getCategoryColor("Deporte")
        assertEquals(Color(0xFFFFA500), color)
    }

    @Test
    fun testCategoryColorForViajes() {
        val color = ColorUtils.getCategoryColor("Viajes")
        assertEquals(Color(0xFF008080), color)
    }

    @Test
    fun testCategoryColorForCompras() {
        val color = ColorUtils.getCategoryColor("Compras")
        assertEquals(Color(0xFFFF1493), color)
    }

    @Test
    fun testCategoryColorForOtros() {
        val color = ColorUtils.getCategoryColor("Otros")
        assertEquals(Color.Gray, color)
    }

    @Test
    fun testCategoryColorForUnknownCategory() {
        val color = ColorUtils.getCategoryColor("CategoriaDesconocida")
        assertEquals(Color.DarkGray, color)
    }

    @Test
    fun testCategoryColorForEmptyString() {
        val color = ColorUtils.getCategoryColor("")
        assertEquals(Color.DarkGray, color)
    }

    @Test
    fun testCategoryColorIsCaseSensitive() {
        val colorLowercase = ColorUtils.getCategoryColor("trabajo")
        assertEquals(Color.DarkGray, colorLowercase)
        
        val colorUppercase = ColorUtils.getCategoryColor("TRABAJO")
        assertEquals(Color.DarkGray, colorUppercase)
    }
}
