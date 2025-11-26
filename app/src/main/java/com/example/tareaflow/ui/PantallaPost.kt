package com.example.tareaflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tareaflow.model.Post
import com.example.tareaflow.viewmodel.PostViewModel
import com.example.tareaflow.viewmodel.UiState

@Composable
fun PantallaPost(
    navController: NavController,
    viewModel: PostViewModel = PostViewModel()
) {
    val postsState by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gestión de Posts", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        when (postsState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val posts = (postsState as UiState.Success<List<Post>>).data
                LazyColumn {
                    items(posts) { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Título: ${post.title}", style = MaterialTheme.typography.bodyLarge)
                                Text("Descripción: ${post.description}", style = MaterialTheme.typography.bodyMedium)
                                Text("Categoría: ${post.category}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { navController.navigate("pantallaInicio") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver", style = MaterialTheme.typography.bodyLarge)
                }
            }

            is UiState.Error -> {
                val message = (postsState as UiState.Error).message
                Text("⚠️ Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
