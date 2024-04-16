package com.testassessment.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = PostRepository()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private var currentPage = 1

    init {
        fetchNextPage()
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            val newPosts = repository.fetchPosts(currentPage)
            _posts.value = (_posts.value ?: emptyList()) + newPosts
            currentPage++
        }
    }
}