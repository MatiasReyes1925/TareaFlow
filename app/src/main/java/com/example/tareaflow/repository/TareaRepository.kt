package com.example.tareaflow.repository

import com.example.tareaflow.model.Tarea
import com.example.tareaflow.model.TareaDao

class TareaRepository(private val dao: TareaDao) {
    suspend fun obtenerTareas(idUsuario: Int): List<Tarea> {
        return dao.obtenerPorUsuario(idUsuario)
    }

    suspend fun agregarTarea(tarea: Tarea) {
        dao.insertar(tarea)
    }

    suspend fun actualizarTarea(tarea: Tarea) {
        dao.actualizar(tarea)
    }

    suspend fun eliminarTarea(tarea: Tarea) {
        dao.eliminar(tarea)
    }
}
