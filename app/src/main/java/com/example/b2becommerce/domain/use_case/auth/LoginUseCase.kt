package com.example.b2becommerce.domain.use_case.auth

import com.example.b2becommerce.domain.model.User
import com.example.b2becommerce.domain.repository.AuthRepository
import com.example.b2becommerce.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(customerCode: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.login(customerCode, password)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Giriş yapılırken bir hata oluştu"))
        }
    }
} 