package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioPost
import com.example.tareaflow.repository.UsuarioPostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioPostViewModel(
    private val repository: UsuarioPostRepository = UsuarioPostRepository()
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    private val _estado = MutableStateFlow<UiState<Usuario>>(UiState.Loading)
    val estado: StateFlow<UiState<Usuario>> = _estado

    private val _usuariosPorId = MutableStateFlow<UiState<Usuario>>(UiState.Loading)
    val usuariosPorId: StateFlow<UiState<Usuario>> = _usuariosPorId

    private val _usuariosLista = MutableStateFlow<UiState<List<Usuario>>>(UiState.Loading)
    val usuariosLista: StateFlow<UiState<List<Usuario>>> = _usuariosLista

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _estado.value = UiState.Loading
                val usuario = repository.login(
                    UsuarioPost(name = "", email = email, password = password, account = 0)
                )
                if (usuario != null) {
                    _usuarioActual.value = usuario
                    _estado.value = UiState.Success(usuario)
                } else {
                    _estado.value = UiState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _estado.value = UiState.Error(e.message ?: "Error en login")
            }
        }
    }

    fun registrar(usuarioPost: UsuarioPost) {
        viewModelScope.launch {
            try {
                _estado.value = UiState.Loading
                val nuevoUsuario = repository.registrar(usuarioPost)
                if (nuevoUsuario != null) {
                    _usuarioActual.value = nuevoUsuario
                    _estado.value = UiState.Success(nuevoUsuario)
                } else {
                    _estado.value = UiState.Error("Error al registrar usuario")
                }
            } catch (e: Exception) {
                _estado.value = UiState.Error(e.message ?: "Error en registro")
            }
        }
    }

    fun obtenerUsuarioPorId(id: Int) {
        viewModelScope.launch {
            try {
                _usuariosPorId.value = UiState.Loading
                val usuario = repository.obtenerPorId(id)
                if (usuario != null) {
                    _usuariosPorId.value = UiState.Success(usuario)
                } else {
                    _usuariosPorId.value = UiState.Error("Usuario no encontrado")
                }
            } catch (e: Exception) {
                _usuariosPorId.value = UiState.Error(e.message ?: "Error al obtener usuario")
            }
        }
    }

    fun obtenerTodosUsuarios() {
        viewModelScope.launch {
            try {
                _usuariosLista.value = UiState.Loading
                val usuarios = repository.obtenerTodos()
                _usuariosLista.value = UiState.Success(usuarios)
            } catch (e: Exception) {
                _usuariosLista.value = UiState.Error(e.message ?: "Error al obtener usuarios")
            }
        }
    }

    fun logout() {
        _usuarioActual.value = null
        _estado.value = UiState.Loading
    }
}
