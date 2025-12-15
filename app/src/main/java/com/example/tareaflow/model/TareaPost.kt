package com.example.tareaflow.model

data class TareaPost(
    val id: Int? = null,
    val date: String = "",
    val name: String = "",
    val description: String,
    val title: String,
    val category: String
)