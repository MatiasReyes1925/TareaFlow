package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.Post
import com.example.tareaflow.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository = PostRepository()) : ViewModel() {
    private val _posts = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val posts: StateFlow<UiState<List<Post>>> = _posts
    
    private var postsLoaded = false

    fun loadPosts() {
        if (postsLoaded) return
        
        viewModelScope.launch {
            try {
                _posts.value = UiState.Loading
                val postsList = repository.getPosts()
                _posts.value = UiState.Success(postsList)
                postsLoaded = true
            } catch (e: Exception) {
                _posts.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updatePost(id: Int, post: Post) {
        viewModelScope.launch {
            try {
                repository.updatePost(id, post)
                loadPosts()
            } catch (e: Exception) {
                _posts.value = UiState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePost(id)
                loadPosts()
            } catch (e: Exception) {
                _posts.value = UiState.Error(e.message ?: "Error al eliminar")
            }
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
