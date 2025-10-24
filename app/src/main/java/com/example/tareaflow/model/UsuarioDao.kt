package com.example.tareaflow.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tareaflow.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun obtenerPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM usuario")
    suspend fun obtenerTodos(): List<Usuario>

    @Query("DELETE FROM usuario WHERE correo = :correo")
    suspend fun eliminarPorCorreo(correo: String)
}
