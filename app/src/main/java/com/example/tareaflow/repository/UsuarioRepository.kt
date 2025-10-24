package com.example.tareaflow.repository

import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioDao

class UsuarioRepository(private val dao: UsuarioDao) {
    suspend fun registrar(usuario: Usuario): Boolean {
        val existente = dao.obtenerPorCorreo(usuario.correo)
        return if (existente == null) {
            dao.insertar(usuario)
            true
        } else {
            false
        }
    }

    suspend fun obtenerPorCorreo(correo: String): Usuario? {
        return dao.obtenerPorCorreo(correo)
    }
}
