package com.example.tareaflow.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDao {
    @Query("SELECT * FROM tareas WHERE idUsuario = :idUsuario")
    suspend fun obtenerPorUsuario(idUsuario: Int): List<Tarea>

    @Insert
    suspend fun insertar(tarea: Tarea)

    @Update
    suspend fun actualizar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)
}
