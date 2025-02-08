package com.example.b2becommerce.data.repository

import android.content.SharedPreferences
import com.example.b2becommerce.data.remote.api.ApiService
import com.example.b2becommerce.data.remote.dto.LoginRequest
import com.example.b2becommerce.domain.model.User
import com.example.b2becommerce.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun login(customerCode: String, password: String): User {
        try {
            val response = api.login(LoginRequest(customerCode, password))
            
            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: "Giriş başarısız")
            }

            val loginResponse = response.body() ?: throw Exception("Yanıt alınamadı")
            
            // Token'ı kaydet
            saveAuthToken(loginResponse.token)

            // User modelini dönüştür ve döndür
            return with(loginResponse.user) {
                User(
                    id = id.toString(),
                    email = email,
                    name = name,
                    role = role
                )
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "Giriş yapılırken bir hata oluştu")
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        companyName: String
    ): Boolean {
        // Register implementasyonu
        TODO("Not yet implemented")
    }

    override suspend fun saveAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    override suspend fun getAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN_KEY, null)
    }

    override suspend fun clearAuthToken() {
        prefs.edit().remove(AUTH_TOKEN_KEY).apply()
    }

    override suspend fun resetPassword(email: String) {
        try {
            val response = api.resetPassword(email)
            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: "Şifre sıfırlama başarısız")
            }
        } catch (e: Exception) {
            throw Exception("Şifre sıfırlama işlemi başarısız oldu: ${e.localizedMessage}")
        }
    }

    companion object {
        private const val AUTH_TOKEN_KEY = "auth_token"
    }
} 