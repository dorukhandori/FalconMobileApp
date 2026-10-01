package com.example.b2becommerce.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val customerCode: String,
    val role: String = "USER" // Varsayılan rol ekledik
) 