package com.example.b2becommerce.domain.repository

import com.example.b2becommerce.data.remote.model.ResetPasswordRequest
import com.example.b2becommerce.data.remote.model.ResetPasswordResponse
import com.example.b2becommerce.domain.model.User
import com.example.b2becommerce.data.remote.model.RegisterFormRequest
import com.example.b2becommerce.data.remote.model.BaseResponse

interface AuthRepository {
    suspend fun login(customerCode: String, password: String): User
    suspend fun register(request: RegisterFormRequest): Result<BaseResponse>
    suspend fun resetPassword(request: ResetPasswordRequest): Result<ResetPasswordResponse>
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
    suspend fun clearAuthToken()
} 