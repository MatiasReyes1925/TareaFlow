package com.example.tareaflow.remote

import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioPost
import retrofit2.http.*

interface UsuarioApi {
    @GET("user")
    suspend fun getUsuarios(): List<Usuario>

    @GET("user/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Usuario

    @POST("user")
    suspend fun crearUsuario(@Body usuarioPost: UsuarioPost): Usuario

    @POST("user/login")
    suspend fun login(@Body usuarioPost: UsuarioPost): Usuario
}
