package com.example.tareaflow.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.tareaflow.model.Usuario

data class ErroresFormularioUsuario(
    val nombre: String? = null,
    val correo: String? = null,
    val contrasena: String? = null
)

class FormUsuarioViewModel : ViewModel() {

    private val TAG = "FormularioUsuario"

    private val _formulario = MutableStateFlow(Usuario(nombre = "", correo = "", contrasena = ""))
    val formulario: StateFlow<Usuario> = _formulario

    private val _errores = MutableStateFlow(
        ErroresFormularioUsuario(
            nombre = "El nombre es obligatorio",
            correo = "El correo es obligatorio",
            contrasena = "La contrasena es obligatoria"
        )
    )
    val errores: StateFlow<ErroresFormularioUsuario> = _errores

    fun onNombreChange(valor: String) {
        _formulario.value = _formulario.value.copy(nombre = valor)
        validar()
    }

    fun onCorreoChange(valor: String) {
        _formulario.value = _formulario.value.copy(correo = valor)
        validar()
    }

    fun onContrasenaChange(valor: String) {
        _formulario.value = _formulario.value.copy(contrasena = valor)
        validar()
    }

    fun isFormValid(errores: ErroresFormularioUsuario): Boolean {
        Log.d(TAG, "Validando formulario")
        Log.d(TAG, "nombre: ${errores.nombre}, correo: ${errores.correo}, contrasena: ${errores.contrasena}")
        return errores.nombre == null && errores.correo == null && errores.contrasena == null
    }

    private fun validar() {
        val f = _formulario.value
        _errores.value = ErroresFormularioUsuario(
            nombre = if (f.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = if (!f.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))) "Correo invalido" else null,
            contrasena = if (f.contrasena.isBlank()) "La contrasena es obligatoria" else null
        )
    }
}
