package com.example.tareaflow.repository

import com.example.tareaflow.model.Usuario

class UsuarioRepository {
    private val usuarios = mutableListOf<Usuario>()
    private var usuarioActivo: Usuario? = null

    fun registrar(usuario: Usuario): Boolean {
        if (usuarios.any { it.correo == usuario.correo }) return false
        usuarios.add(usuario)
        usuarioActivo = usuario
        return true
    }

    fun iniciarSesion(correo: String, contraseña: String): Boolean {
        val usuario = usuarios.find { it.correo == correo && it.contraseña == contraseña }
        usuarioActivo = usuario
        return usuario != null
    }

    fun cerrarSesion() {
        usuarioActivo = null
    }

    fun obtenerUsuarioActivo(): Usuario? = usuarioActivo
}
