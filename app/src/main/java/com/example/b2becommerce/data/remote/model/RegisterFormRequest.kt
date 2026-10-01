package com.example.b2becommerce.data.remote.model

data class RegisterFormRequest(
    val customerType: Int,
    val fullName: String,
    val email: String,
    val phone: String,
    val taxOffice: String,
    val taxNumber: String,
    val isAccessories: Boolean,
    val isService: Boolean,
    val isAvm: Boolean,
    val isOil: Boolean,
    val isOto: Boolean,
    val isMarket: Boolean,
    val address: String,
    val address2: String?,
    val country: String,
    val city: String,
    val region: String,
    val postalCode: String,
    val filePath1: String?,
    val filePath2: String?,
    val filePath3: String?,
    val filePath4: String?
) 