package com.example.b2becommerce.data.repository

import android.content.SharedPreferences
import com.example.b2becommerce.data.model.LoginResponse
import com.example.b2becommerce.data.model.UserData
import com.example.b2becommerce.data.remote.api.ApiService
import com.example.b2becommerce.data.remote.dto.LoginRequest
import com.example.b2becommerce.data.remote.model.RegisterFormRequest
import com.example.b2becommerce.data.remote.model.BaseResponse
import com.example.b2becommerce.data.remote.model.ResetPasswordRequest
import com.example.b2becommerce.data.remote.model.ResetPasswordResponse
import com.example.b2becommerce.domain.model.User
import com.example.b2becommerce.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun login(customerCode: String, password: String): User {
        val request = LoginRequest(customerCode = customerCode, password = password)
        val response = api.login(request)
        if (response.isSuccessful) {
            val loginResponse: LoginResponse? = response.body()
            // Token'ı kaydet
            loginResponse?.token?.let { token ->
                saveAuthToken(token)
            }
            // User modelini dönüştür ve döndür
            return User(
                id = loginResponse?.userId ?: "",
                email = "", // Email bilgisi yoksa boş bırakın
                name = "", // Name bilgisi yoksa boş bırakın
                customerCode = customerCode,
                role = "USER"
            )
        } else {
            throw Exception(response.errorBody()?.string() ?: "Giriş başarısız")
        }
    }

    override suspend fun register(request: RegisterFormRequest): Result<BaseResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.register(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(Exception("Yanıt alınamadı"))
                    }
                } else {
                    Result.failure(Exception(response.errorBody()?.string() ?: "Kayıt başarısız"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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

    override suspend fun resetPassword(request: ResetPasswordRequest): Result<ResetPasswordResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.resetPassword(request)
                if (response.statusCode == 200) {
                    Result.success(response)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserData(userId: String): UserData {
        // Geçici bir UserData nesnesi döndürün
        return UserData(
            id = userId,
            name = "Default Name", // Varsayılan bir isim
            email = "default@example.com" // Varsayılan bir e-posta
        )
    }

    companion object {
        private const val AUTH_TOKEN_KEY = "auth_token"
    }
} 