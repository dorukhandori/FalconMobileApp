package com.example.b2becommerce.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.b2becommerce.domain.repository.AuthRepository
import com.example.b2becommerce.domain.use_case.auth.LoginUseCase
import com.example.b2becommerce.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.CustomerCodeChanged -> {
                state = state.copy(
                    customerCode = event.value,
                    customerCodeError = validateCustomerCode(event.value)
                )
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    passwordError = validatePassword(event.value)
                )
            }
            is LoginEvent.LoginClicked -> {
                login()
            }
            is LoginEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }
            is LoginEvent.ForgotPassword -> {
                viewModelScope.launch {
                    try {
                        state = state.copy(isLoading = true)
                        authRepository.resetPassword(event.email)
                        state = state.copy(
                            isLoading = false,
                            error = null
                        )
                    } catch (e: Exception) {
                        state = state.copy(
                            isLoading = false,
                            error = e.message ?: "Şifre sıfırlama işlemi başarısız oldu"
                        )
                    }
                }
            }
        }
    }

    private fun validateCustomerCode(code: String): String? {
        if (code.isBlank()) {
            return "Müşteri kodu boş bırakılamaz"
        }
        if (!code.all { it.isLetterOrDigit() }) {
            return "Müşteri kodu yalnızca harf ve rakamlardan oluşmalıdır"
        }
        if (code.length > 20) {
            return "Müşteri kodu 20 karakterden uzun olamaz"
        }
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) {
            return "Şifre boş bırakılamaz"
        }
        return null
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            loginUseCase(state.customerCode, state.password).collect { result ->
                state = when (result) {
                    is Resource.Success -> state.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                    is Resource.Error -> state.copy(
                        isLoading = false,
                        error = result.message
                    )
                    is Resource.Loading -> state.copy(
                        isLoading = true
                    )
                }
            }
        }
    }
} 