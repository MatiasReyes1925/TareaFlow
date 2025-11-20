package com.example.tareaflow.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.EstadoTarea
import com.example.tareaflow.model.Tarea
import com.example.tareaflow.repository.TareaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TareaViewModel(private val repository: TareaRepository) : ViewModel() {
    val tareas = mutableStateListOf<Tarea>()

    fun cargarTareas(idUsuario: Int) {
        viewModelScope.launch {
            tareas.clear()
            tareas.addAll(repository.obtenerTareas(idUsuario))
        }
    }

    fun obtenerTareaPorId(id: Int): Flow<Tarea?> {
        return repository.obtenerTareaPorId(id)
    }

    suspend fun agregar(
        titulo: String,
        categoria: String,
        detalle: String,
        idUsuario: Int,
    ) {
        val nueva = Tarea(
            titulo = titulo,
            detalle = detalle,
            categoria = categoria,
            idUsuario = idUsuario,
            estado = EstadoTarea.PENDIENTE,
        )
        repository.agregarTarea(nueva)
        cargarTareas(idUsuario)
    }

    suspend fun marcarComoCompletada(tarea: Tarea) {
        val actualizada = tarea.copy(estado = EstadoTarea.COMPLETADA)
        repository.actualizarTarea(actualizada)
        cargarTareas(tarea.idUsuario)
    }

    suspend fun actualizar(
        tareaOriginal: Tarea,
        nuevoTitulo: String,
        nuevaCategoria: String,
        nuevoDetalle: String,
    ) {
        val tareaActualizada = tareaOriginal.copy(
            titulo = nuevoTitulo,
            categoria = nuevaCategoria,
            detalle = nuevoDetalle,
        )
        repository.actualizarTarea(tareaActualizada)
        cargarTareas(tareaOriginal.idUsuario)
    }

    suspend fun eliminarTarea(tarea: Tarea) {
        repository.eliminarTarea(tarea)
        cargarTareas(tarea.idUsuario)
    }
}
