package com.example.b2becommerce.data.remote.dto

data class LoginRequest(
    val customerCode: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val companyName: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String
) 