package com.example.b2becommerce.data.remote.model

data class ResetPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
) 