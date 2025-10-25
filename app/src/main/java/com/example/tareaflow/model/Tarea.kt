package com.example.tareaflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val detalle: String,
    val categoria: String,
    val idUsuario: Int,
    val estado: EstadoTarea,
    val fotoUri: String? = null
)

enum class EstadoTarea {
    PENDIENTE,
    COMPLETADA
}
