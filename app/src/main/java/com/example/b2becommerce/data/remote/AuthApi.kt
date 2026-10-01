package com.example.b2becommerce.data.remote

import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response

interface AuthApi {
    // ... diğer metodlar ...

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body email: String
    ): Response<Unit>
} 