package com.example.b2becommerce.data.remote.dto

data class OrderResponse(
    val id: Int,
    val userId: Int,
    val orderNumber: String,
    val orderDate: String,
    val status: String,
    val totalPrice: Double,
    val items: List<OrderItemDto>
)

data class OrderItemDto(
    val id: Int,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val price: Double
)

data class CreateOrderRequest(
    val cartId: Int,
    val shippingAddress: String,
    val billingAddress: String
) 