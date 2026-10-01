package com.example.b2becommerce.presentation.auth.register

data class RegisterState(
    val customerType: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val taxOffice: String = "",
    val taxNumber: String = "",
    val isAccessories: Boolean = false,
    val isService: Boolean = false,
    val isAvm: Boolean = false,
    val isOil: Boolean = false,
    val isOto: Boolean = false,
    val isMarket: Boolean = false,
    val address: String = "",
    val address2: String = "",
    val country: String = "",
    val city: String = "",
    val region: String = "",
    val postalCode: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    
    // Validation errors
    val fullNameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null
) 