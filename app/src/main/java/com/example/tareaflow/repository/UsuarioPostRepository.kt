package com.example.tareaflow.repository

import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioPost
import com.example.tareaflow.remote.RetrofitInstance
import com.example.tareaflow.remote.UsuarioApi

class UsuarioPostRepository {

    suspend fun registrar(usuarioPost: UsuarioPost): Usuario? {
        return try {
            RetrofitInstance.usuarioApi.crearUsuario(usuarioPost)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun login(usuarioPost: UsuarioPost): Usuario? {
        return try {
            RetrofitInstance.usuarioApi.login(usuarioPost)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerTodos(): List<Usuario> {
        return try {
            RetrofitInstance.usuarioApi.getUsuarios()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerPorId(id: Int): Usuario? {
        return try {
            RetrofitInstance.usuarioApi.getUsuario(id)
        } catch (e: Exception) {
            null
        }
    }
}
