package com.project.libum.data.remote

import com.project.libum.data.dto.UserList
import com.project.libum.data.model.BookListResponse
import com.project.libum.data.model.BookResponse
import com.project.libum.data.model.BooksResponse
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

    @GET("/api/books/file/reader")
    @Headers("Content-Type: */*")
    suspend fun getBookFile(@Query("id") bookId: Int): Response<ResponseBody>

    @GET("/api/books/list")
    suspend fun getUserBooksList(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BooksResponse>


    @GET("/api/booklist/list")
    @Headers("Content-Type: */*")
    suspend fun getListsId(): Response<List<BookListResponse>>

    @GET("/api/booklists/userlist")
    suspend fun getBooksList(
        @Query("user_id") userId: String,
        @Query("list_id") listId: String,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?
    ): Response<BookResponse>


}