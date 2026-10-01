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
import kotlinx.coroutines.delay
import kotlin.Result
import com.example.b2becommerce.domain.model.User

data class LoginState(
    val emailOrUsername: String = "",
    val password: String = "",
    val emailOrUsernameError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
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
            is LoginEvent.EmailOrUsernameChanged -> {
                state = state.copy(emailOrUsername = event.value, emailOrUsernameError = null)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.value, passwordError = validatePassword(event.value))
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
            is LoginEvent.UsernameChanged -> {
                // Eğer kullanıcı adı ile ilgili bir işlem yapılacaksa buraya ekleyin
            }
            is LoginEvent.EmailChanged -> {
                // Eğer e-posta ile ilgili bir işlem yapılacaksa buraya ekleyin
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
        val emailOrUsernameError = if (state.emailOrUsername.isEmpty()) "E-posta veya kullanıcı adı boş olamaz" else null
        val passwordError = validatePassword(state.password)

        state = state.copy(
            emailOrUsernameError = emailOrUsernameError,
            passwordError = passwordError
        )

        return emailOrUsernameError == null && passwordError == null
    }

    private fun login() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                
                val user: User = repository.login(state.emailOrUsername, state.password)
                state = state.copy(
                    isLoading = false,
                    isSuccess = true,
                    error = null,
                    successMessage = "Giriş başarılı!"
                )
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("404") == true -> "Kullanıcı bulunamadı"
                    e.message?.contains("401") == true -> "E-posta veya şifre hatalı"
                    e.message?.contains("network") == true -> "İnternet bağlantınızı kontrol edin"
                    else -> "Giriş yapılırken bir hata oluştu"
                }
                state = state.copy(
                    isLoading = false,
                    error = errorMessage,
                    successMessage = null
                )

                // Hata mesajını 3 saniye sonra temizle
                delay(3000)
                state = state.copy(error = null)
            }
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
                        val errorMessage = e.message ?: "Şifre sıfırlama işlemi başarısız oldu"
                        state = state.copy(
                            isLoading = false,
                            error = errorMessage,
                            successMessage = null
                        )
                        // Hata mesajını 3 saniye sonra temizle
                        delay(3000)
                        state = state.copy(error = null)
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
        
        // 3 saniye sonra hata mesajını temizle
        viewModelScope.launch {
            delay(3000)
            state = state.copy(error = null)
        }
    }
} 