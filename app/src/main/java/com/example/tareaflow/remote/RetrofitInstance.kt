package com.example.tareaflow.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Uu8UhYYg/"
    private const val USUARIO_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:tcRj6KLS/user"

    val api: TareaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TareaApi::class.java)
    }

    val usuarioApi: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(USUARIO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApi::class.java)
    }
}

