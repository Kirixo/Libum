package com.project.libum.data.remote

import com.project.libum.data.model.BookListResponse
import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("/api/users/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/book/login")
    @Headers("Content-Type: application/json")
    suspend fun getBooksList(): Response<BookListResponse>

}