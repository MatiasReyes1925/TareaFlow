package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.tareaflow.model.Usuario

data class UsuarioFormError(
    val nombre: String? = null,
    val correo: String? = null,
    val contraseña: String? = null
)

class UsuarioFormViewModel : ViewModel() {
    private val _form = MutableStateFlow(Usuario(nombre = "", correo = "", contraseña = ""))
    val form: StateFlow<Usuario> = _form

    private val _errors = MutableStateFlow(UsuarioFormError(
        nombre = "El nombre es obligatorio",
        correo = "El correo es obligatorio",
        contraseña = "La contraseña es obligatoria"
    ))
    val errors: StateFlow<UsuarioFormError> = _errors

    fun onNombreChange(value: String) {
        _form.value = _form.value.copy(nombre = value)
        validate()
    }

    fun onCorreoChange(value: String) {
        _form.value = _form.value.copy(correo = value)
        validate()
    }

    fun onContraseñaChange(value: String) {
        _form.value = _form.value.copy(contraseña = value)
        validate()
    }

    fun isFormValid(errors: UsuarioFormError): Boolean {
        return errors.nombre == null && errors.correo == null && errors.contraseña == null
    }

    private fun validate() {
        val f = _form.value
        _errors.value = UsuarioFormError(
            nombre = if (f.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = if (!f.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))) "Correo inválido" else null,
            contraseña = if (f.contraseña.length < 6) "Mínimo 6 caracteres" else null
        )
    }
}
