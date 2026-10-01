package com.example.b2becommerce.data.remote.dto

data class CartResponse(
    val id: Int,
    val userId: Int,
    val items: List<CartItemDto>,
    val totalPrice: Double
)

data class CartItemDto(
    val id: Int,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String?
)

data class AddToCartRequest(
    val productId: Int,
    val quantity: Int
)

data class RemoveFromCartRequest(
    val cartItemId: Int
) 