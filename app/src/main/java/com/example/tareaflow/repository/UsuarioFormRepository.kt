package com.example.tareaflow.repository

import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioDao

class UsuarioFormRepository(private val dao: UsuarioDao) {
    suspend fun validarCorreo(correo: String): Boolean {
        return dao.obtenerPorCorreo(correo) == null
    }
}
