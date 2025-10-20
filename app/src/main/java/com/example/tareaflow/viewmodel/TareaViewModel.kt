package com.example.tareaflow.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tareaflow.model.EstadoTarea
import com.example.tareaflow.model.Tarea
import com.example.tareaflow.repository.TareaRepository

class TareaViewModel(private val repository: TareaRepository = TareaRepository()) : ViewModel() {
    private var contadorId = 0
    val tareas = mutableStateListOf<Tarea>()
    private val listaDeTareas = mutableListOf<Tarea>()
    init {
        tareas.addAll(repository.obtenerTareas())
    }

    fun agregar(titulo: String, categoria: String, detalle: String) {
        val nueva = Tarea(
            id = contadorId++,
            titulo = titulo,
            detalle = detalle,
            categoria = categoria,
            estado = EstadoTarea.PENDIENTE
        )
        tareas.add(nueva)
        repository.agregarTarea(nueva)
    }

    fun marcarComoCompletada(tarea: Tarea) {
        val index = tareas.indexOf(tarea)
        if (index != -1) {
            val actualizada = tarea.copy(estado = EstadoTarea.COMPLETADA)
            tareas[index] = actualizada
            repository.actualizarTarea(tarea, actualizada)
        }
    }

    fun actualizar(tareaOriginal: Tarea, nuevoTitulo: String, nuevaCategoria: String, nuevoDetalle: String) {
        val tareaActualizada = tareaOriginal.copy(
            titulo = nuevoTitulo,
            categoria = nuevaCategoria,
            detalle = nuevoDetalle
        )
        val index = tareas.indexOf(tareaOriginal)
        if (index != -1) {
            tareas[index] = tareaActualizada
            repository.actualizarTarea(tareaOriginal, tareaActualizada)
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        tareas.remove(tarea)
        repository.eliminarTarea(tarea)
    }

    fun reiniciarContadorSiListaVacia() {
        if (tareas.isEmpty()) {
            contadorId = 0
        }
    }
}
