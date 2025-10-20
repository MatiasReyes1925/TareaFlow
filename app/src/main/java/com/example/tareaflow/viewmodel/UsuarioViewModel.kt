package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.repository.UsuarioRepository

class UsuarioViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    fun registrar(nombre: String, correo: String, contraseña: String): Boolean {
        val nuevo = Usuario(nombre, correo, contraseña)
        val registrado = usuarioRepository.registrar(nuevo)
        println("Usuario Registrado: $registrado → $nuevo")
        return registrado
    }

    fun iniciarSesion(correo: String, contraseña: String): Boolean {
        return usuarioRepository.iniciarSesion(correo, contraseña)
    }

    fun cerrarSesion() {
        usuarioRepository.cerrarSesion()
    }

    fun obtenerUsuarioActivo(): Usuario? {
        return usuarioRepository.obtenerUsuarioActivo()
    }
}
