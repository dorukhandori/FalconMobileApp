package com.example.b2becommerce.presentation.auth.login

sealed class LoginEvent {
    // ... mevcut event'ler ...
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    object LoginClicked : LoginEvent()
    data class ForgotPassword(val email: String) : LoginEvent()
    object ErrorDismissed : LoginEvent()
} 