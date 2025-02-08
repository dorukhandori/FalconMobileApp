package com.example.b2becommerce.data.remote.api

import com.example.b2becommerce.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth endpoints
    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("api/Auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    
    // Product endpoints
    @GET("api/Product")
    suspend fun getProducts(): Response<List<ProductResponse>>
    
    @GET("api/Product/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Response<ProductDetailResponse>
    
    @POST("api/Product")
    suspend fun createProduct(@Body request: CreateProductRequest): Response<ProductResponse>
    
    // Category endpoints
    @GET("api/Category")
    suspend fun getCategories(): Response<List<CategoryResponse>>
    
    @GET("api/Category/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Response<CategoryResponse>
    
    // Cart endpoints
    @GET("api/Cart")
    suspend fun getCart(): Response<CartResponse>
    
    @POST("api/Cart/add")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<CartResponse>
    
    @POST("api/Cart/remove")
    suspend fun removeFromCart(@Body request: RemoveFromCartRequest): Response<CartResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body email: String): Response<Unit>
} 