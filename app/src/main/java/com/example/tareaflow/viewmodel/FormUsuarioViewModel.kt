package com.example.tareaflow.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.tareaflow.model.Usuario

data class ErroresFormularioUsuario(
    val nombre: String? = null,
    val correo: String? = null,
    val contraseña: String? = null
)

class FormUsuarioViewModel : ViewModel() {

    private val TAG = "FormularioUsuario"

    private val _formulario = MutableStateFlow(Usuario(nombre = "", correo = "", contraseña = ""))
    val formulario: StateFlow<Usuario> = _formulario

    private val _errores = MutableStateFlow(
        ErroresFormularioUsuario(
            nombre = "El nombre es obligatorio",
            correo = "El correo es obligatorio",
            contraseña = "La contraseña es obligatoria"
        )
    )
    val errores: StateFlow<ErroresFormularioUsuario> = _errores

    fun cambiarNombre(valor: String) {
        _formulario.value = _formulario.value.copy(nombre = valor)
        validar()
    }

    fun cambiarCorreo(valor: String) {
        _formulario.value = _formulario.value.copy(correo = valor)
        validar()
    }

    fun cambiarContraseña(valor: String) {
        _formulario.value = _formulario.value.copy(contraseña = valor)
        validar()
    }

    fun esFormularioValido(errores: ErroresFormularioUsuario): Boolean {
        Log.d(TAG, "Validando formulario")
        Log.d(TAG, "nombre: ${errores.nombre}, correo: ${errores.correo}, contraseña: ${errores.contraseña}")
        return errores.nombre == null && errores.correo == null && errores.contraseña == null
    }

    private fun validar() {
        val f = _formulario.value
        _errores.value = ErroresFormularioUsuario(
            nombre = if (f.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = if (!f.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))) "Correo inválido" else null,
            contraseña = if (f.contraseña.isBlank()) "La contraseña es obligatoria" else null
        )
    }
}


