package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        val exito = repository.registrar(usuario)
        if (exito) _usuarioActual.value = usuario
        return exito
    }

    suspend fun iniciarSesion(correo: String, contraseña: String): Boolean {
        val usuario = repository.obtenerPorCorreo(correo)
        val valido = usuario?.contraseña == contraseña
        if (valido) _usuarioActual.value = usuario
        return valido
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}
