package com.example.b2becommerce.presentation.auth.login

data class LoginState(
    val customerCode: String = "",
    val customerCodeError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
) 