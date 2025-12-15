package com.example.tareaflow.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.EstadoTarea
import com.example.tareaflow.model.Tarea
import com.example.tareaflow.remote.RetrofitInstance
import com.example.tareaflow.repository.TareaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class TareaViewModel(private val repository: TareaRepository) : ViewModel() {
    val tareas = mutableStateListOf<Tarea>()
    private var context: Context? = null

    fun setContext(ctx: Context) {
        context = ctx
    }

    fun cargarTareas(idUsuario: Int) {
        viewModelScope.launch {
            tareas.clear()
            tareas.addAll(repository.obtenerTareas(idUsuario))
        }
    }

    fun cargarDesdeXano() {
        viewModelScope.launch {
            try {
                val remotas = RetrofitInstance.api.getPosts()
                tareas.clear()
                tareas.addAll(remotas.map {
                    Tarea(
                        titulo = it.title,
                        detalle = it.description,
                        categoria = it.category,
                        idUsuario = 0,
                        estado = EstadoTarea.PENDIENTE,
                        fotoUri = null
                    )
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun obtenerTareaPorId(id: Int): Flow<Tarea?> {
        return repository.obtenerTareaPorId(id)
    }

    private fun guardarBitmap(bitmap: Bitmap): String? {
        return try {
            context?.let { ctx ->
                val fileName = "tarea_${System.currentTimeMillis()}.jpg"
                val file = File(ctx.filesDir, fileName)
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
                file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun agregar(
        titulo: String,
        categoria: String,
        detalle: String,
        idUsuario: Int,
        foto: Bitmap? = null,
    ) {
        val fotoUri = foto?.let { guardarBitmap(it) }
        val nueva = Tarea(
            titulo = titulo,
            detalle = detalle,
            categoria = categoria,
            idUsuario = idUsuario,
            estado = EstadoTarea.PENDIENTE,
            fotoUri = fotoUri
        )
        repository.agregarTarea(nueva)
        cargarTareas(idUsuario)
    }

    suspend fun marcarComoCompletada(tarea: Tarea) {
        val actualizada = tarea.copy(estado = EstadoTarea.COMPLETADA)
        repository.actualizarTarea(actualizada)
        cargarTareas(tarea.idUsuario)
    }

    suspend fun actualizar(
        tareaOriginal: Tarea,
        nuevoTitulo: String,
        nuevaCategoria: String,
        nuevoDetalle: String,
        foto: Bitmap? = null,
    ) {
        val fotoUri = foto?.let { guardarBitmap(it) } ?: tareaOriginal.fotoUri
        val tareaActualizada = tareaOriginal.copy(
            titulo = nuevoTitulo,
            categoria = nuevaCategoria,
            detalle = nuevoDetalle,
            fotoUri = fotoUri
        )
        repository.actualizarTarea(tareaActualizada)
        cargarTareas(tareaOriginal.idUsuario)
    }

    suspend fun eliminarTarea(tarea: Tarea) {
        repository.eliminarTarea(tarea)
        cargarTareas(tarea.idUsuario)
    }
}
