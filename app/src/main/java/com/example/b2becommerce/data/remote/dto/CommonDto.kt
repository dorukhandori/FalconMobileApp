package com.example.b2becommerce.data.remote.dto

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)

data class PaginatedResponse<T>(
    val items: List<T>,
    val totalCount: Int,
    val pageNumber: Int,
    val pageSize: Int
) 