package com.testassessment.demo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(@Query("page") page: Int): Response<List<Post>>
}
