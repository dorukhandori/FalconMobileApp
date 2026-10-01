package com.example.b2becommerce.data.remote.model

data class BaseResponse(
    val statusCode: Int,
    val message: String?,
    val data: Any?
) 