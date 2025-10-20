package com.example.tareaflow.model


data class Tarea(
    val id: Int,
    val titulo: String,
    val detalle: String,
    val categoria: String,
    val estado: EstadoTarea = EstadoTarea.PENDIENTE
)

enum class EstadoTarea {
    PENDIENTE,
    COMPLETADA
}
