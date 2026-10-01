package com.example.b2becommerce.data.remote.dto

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val categoryId: Int,
    val categoryName: String,
    val imageUrl: String?,
    val isActive: Boolean
)

data class ProductDetailResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val categoryId: Int,
    val categoryName: String,
    val imageUrl: String?,
    val isActive: Boolean,
    val specifications: List<SpecificationDto>
)

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val categoryId: Int,
    val imageUrl: String?,
    val specifications: List<CreateSpecificationDto>
)

data class SpecificationDto(
    val id: Int,
    val name: String,
    val value: String
)

data class CreateSpecificationDto(
    val name: String,
    val value: String
) 