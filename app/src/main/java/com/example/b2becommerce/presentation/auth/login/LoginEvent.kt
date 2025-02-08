package com.example.b2becommerce.presentation.auth.login

sealed class LoginEvent {
    // ... mevcut event'ler ...
    data class CustomerCodeChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    object LoginClicked : LoginEvent()
    object ErrorDismissed : LoginEvent()
    data class ForgotPassword(val email: String) : LoginEvent()
} 