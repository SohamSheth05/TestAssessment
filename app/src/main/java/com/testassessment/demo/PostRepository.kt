package com.testassessment.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun fetchPosts(page: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPosts(page)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
