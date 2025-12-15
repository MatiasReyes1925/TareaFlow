package com.example.tareaflow.repository

import com.example.tareaflow.model.TareaPost
import com.example.tareaflow.remote.RetrofitInstance

class TareaPostRepository {
    suspend fun getPosts(): List<TareaPost> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun createPost(post: TareaPost): TareaPost {
        return RetrofitInstance.api.createPost(post)
    }

    suspend fun updatePost(id: Int, post: TareaPost): TareaPost {
        return RetrofitInstance.api.updatePost(id, post)
    }

    suspend fun deletePost(id: Int) {
        RetrofitInstance.api.deletePost(id)
    }
}
