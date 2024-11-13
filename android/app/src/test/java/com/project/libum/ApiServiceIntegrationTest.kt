package com.project.libum

import com.project.libum.data.model.LoginRequest
import com.project.libum.data.remote.ApiService
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceIntegrationTest {

    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)  // Ваш URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun testLoginRealRequest() = runBlocking {
        val request = LoginRequest("user@example.com", "81dc9bdb52d04dc20036dbd8313ed055")
        val response = apiService.login(request)
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

}