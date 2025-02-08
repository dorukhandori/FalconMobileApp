package com.example.b2becommerce.domain.model

data class Cart(
    val id: String,
    val items: List<CartItem>,
    val totalPrice: Double
)

data class CartItem(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String
) 