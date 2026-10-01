package com.example.b2becommerce.data.remote.api

import com.example.b2becommerce.data.remote.dto.*
import com.example.b2becommerce.data.remote.model.ResetPasswordRequest
import com.example.b2becommerce.data.remote.model.ResetPasswordResponse
import com.example.b2becommerce.data.remote.model.RegisterFormRequest
import com.example.b2becommerce.data.remote.model.BaseResponse
import com.example.b2becommerce.data.remote.dto.LoginRequest
import com.example.b2becommerce.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth endpoints
    @POST("v1/Login/token")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse
    
    @POST("auth/send-reset-email")
    suspend fun sendResetPasswordEmail(@Body request: ResetPasswordRequest): ResetPasswordResponse
    
    @POST("v1/Login/registerForm")
    suspend fun register(@Body request: RegisterFormRequest): Response<BaseResponse>
    
    // Product endpoints
    @GET("api/Product")
    suspend fun getProducts(): Response<List<ProductResponse>>
    
    @GET("api/product/{id}")
    suspend fun getProductDetail(@Path("id") id: String): ProductDetailResponse
    
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
} 