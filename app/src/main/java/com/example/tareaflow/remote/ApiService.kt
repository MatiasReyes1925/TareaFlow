package com.example.tareaflow.remote

import com.example.tareaflow.model.Post
import retrofit2.http.*


interface ApiService {
    @GET("task")
    suspend fun getPosts(): List<Post>

    @POST("task")
    suspend fun createPost(@Body post: Post): Post

    @PUT("task/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    @DELETE("task/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}
