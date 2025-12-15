package com.example.tareaflow.remote

import com.example.tareaflow.model.TareaPost
import retrofit2.http.*


interface ApiService {
    @GET("task")
    suspend fun getPosts(): List<TareaPost>

    @POST("task")
    suspend fun createPost(@Body post: TareaPost): TareaPost

    @PUT("task/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: TareaPost): TareaPost

    @DELETE("task/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}
