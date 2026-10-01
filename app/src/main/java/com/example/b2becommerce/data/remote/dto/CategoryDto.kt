package com.example.b2becommerce.data.remote.dto

data class CategoryResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val isActive: Boolean,
    val products: List<ProductResponse>?
)

data class CreateCategoryRequest(
    val name: String,
    val description: String?,
    val imageUrl: String?
) 