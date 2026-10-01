package com.example.b2becommerce.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val stock: Int
)

data class ProductDetail(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val stock: Int,
    val specifications: List<Specification>,
    val reviews: List<Review>
)

data class Specification(
    val id: String,
    val name: String,
    val value: String
)

data class Review(
    val id: String,
    val userId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: String
) 