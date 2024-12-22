package com.project.libum.data.remote

import com.project.libum.data.model.BookListResponse
import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/users/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/book/login")
    @Headers("Content-Type: application/json")
    suspend fun getBooksList(): Response<BookListResponse>

    @GET("/api/books/file/reader")
    @Headers("Content-Type: */*")
    suspend fun getBookFile(@Query("id") bookId: Int): Response<ResponseBody>

}