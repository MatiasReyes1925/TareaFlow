package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    fun login(correo: String) {
        viewModelScope.launch {
            val usuario = repository.obtenerPorCorreo(correo)
            _usuarioActual.value = usuario
        }
    }

    suspend fun iniciarSesion(correo: String, contraseña: String): Boolean {
        val usuario = repository.obtenerPorCorreo(correo)
        return if (usuario != null && usuario.contrasena == contraseña) {
            _usuarioActual.value = usuario
            true
        } else {
            false
        }
    }

    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        val exito = repository.registrar(usuario)
        if (exito) {
            _usuarioActual.value = usuario
        }
        return exito
    }

    fun registrar(usuario: Usuario) {
        viewModelScope.launch {
            registrarUsuario(usuario)
        }
    }

    fun logout() {
        _usuarioActual.value = null
    }

    fun cerrarSesion() {
        logout()
    }
}
