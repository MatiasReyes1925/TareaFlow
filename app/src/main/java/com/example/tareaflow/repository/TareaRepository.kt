package com.example.tareaflow.repository

import com.example.tareaflow.model.Tarea

class TareaRepository {
    private val listaDeTareas = mutableListOf<Tarea>()

    fun obtenerTareas(): List<Tarea> {
        return listaDeTareas
    }

    fun agregarTarea(tarea: Tarea) {
        listaDeTareas.add(tarea)
    }

    fun actualizarTarea(original: Tarea, actualizada: Tarea) {
        val index = listaDeTareas.indexOf(original)
        if (index != -1) {
            listaDeTareas[index] = actualizada
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        listaDeTareas.remove(tarea)
    }
}
