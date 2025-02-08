package com.example.b2becommerce.domain.repository

import com.example.b2becommerce.domain.model.User

interface AuthRepository {
    suspend fun login(customerCode: String, password: String): User
    suspend fun register(email: String, password: String, name: String, companyName: String): Boolean
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
    suspend fun clearAuthToken()
    suspend fun resetPassword(email: String)
} 