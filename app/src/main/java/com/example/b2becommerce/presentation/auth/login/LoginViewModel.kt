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
import com.example.b2becommerce.data.remote.model.ResetPasswordRequest

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(
                    email = event.value,
                    emailError = validateEmail(event.value)
                )
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    passwordError = validatePassword(event.value)
                )
            }
            is LoginEvent.LoginClicked -> {
                if (validateInputs()) {
                    login()
                }
            }
            is LoginEvent.ForgotPassword -> {
                forgotPassword(event.email)
            }
            is LoginEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }
        }
    }

    private fun validateEmail(email: String): String? {
        return if (email.isEmpty()) {
            "E-posta boş olamaz"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Geçerli bir e-posta adresi giriniz"
        } else {
            null
        }
    }

    private fun validatePassword(password: String): String? {
        return if (password.isEmpty()) {
            "Şifre boş olamaz"
        } else if (password.length < 6) {
            "Şifre en az 6 karakter olmalıdır"
        } else {
            null
        }
    }

    private fun validateInputs(): Boolean {
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)

        state = state.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        return emailError == null && passwordError == null
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            // loginUseCase(state.customerCode, state.password).collect { result ->
            //     state = when (result) {
            //         is Resource.Success -> state.copy(
            //             isLoading = false,
            //             isSuccess = true
            //         )
            //         is Resource.Error -> state.copy(
            //             isLoading = false,
            //             error = result.message
            //         )
            //         is Resource.Loading -> state.copy(
            //             isLoading = true
            //         )
            //     }
            // }
        }
    }

    private fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                val request = ResetPasswordRequest(
                    email = email,
                    code = "",
                    newPassword = ""
                )
                repository.resetPassword(request)
                    .onSuccess {
                        state = state.copy(
                            isLoading = false,
                            error = null,
                            successMessage = "Şifre sıfırlama bağlantısı e-posta adresinize gönderildi"
                        )
                    }
                    .onFailure { e ->
                        state = state.copy(
                            isLoading = false,
                            error = e.message ?: "Şifre sıfırlama işlemi başarısız oldu",
                            successMessage = null
                        )
                    }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(exception: Throwable) {
        state = state.copy(
            isLoading = false,
            error = exception.message ?: "Bilinmeyen bir hata oluştu"
        )
    }
} 