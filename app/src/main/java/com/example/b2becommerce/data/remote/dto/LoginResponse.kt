package com.example.b2becommerce.data.remote.dto

data class LoginResponse(
    val statusCode: Int,
    val message: String?,
    val data: LoginData?
)

data class LoginData(
    val accessToken: String?,
    val data: UserData?
)

data class UserData(
    val id: String?,
    val email: String?,
    val name: String?,
    val customerCode: String?
) 