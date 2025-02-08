package com.example.b2becommerce.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
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

data class UserDto(
    val id: Int,
    val email: String,
    val name: String,
    val companyName: String,
    val role: String
) 