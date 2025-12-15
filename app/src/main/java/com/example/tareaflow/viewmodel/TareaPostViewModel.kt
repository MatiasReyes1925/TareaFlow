package com.example.tareaflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareaflow.model.TareaPost
import com.example.tareaflow.repository.TareaPostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: TareaPostRepository = TareaPostRepository()) : ViewModel() {
    private val _posts = MutableStateFlow<UiState<List<TareaPost>>>(UiState.Loading)
    val posts: StateFlow<UiState<List<TareaPost>>> = _posts
    
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

    fun updatePost(id: Int, post: TareaPost) {
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
