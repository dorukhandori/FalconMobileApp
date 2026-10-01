package com.example.b2becommerce.presentation.auth.register

fun validateFullName(value: String): String? {
    if (value.isBlank()) {
        return "Ad Soyad boş bırakılamaz"
    }
    if (value.length < 3) {
        return "Ad Soyad en az 3 karakter olmalıdır"
    }
    return null
}

fun validateEmail(value: String): String? {
    if (value.isBlank()) {
        return "E-posta boş bırakılamaz"
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
        return "Geçerli bir e-posta adresi giriniz"
    }
    return null
}

fun validatePhone(value: String): String? {
    if (value.isBlank()) {
        return "Telefon numarası boş bırakılamaz"
    }
    if (!value.matches(Regex("^[0-9]{10}$"))) {
        return "Geçerli bir telefon numarası giriniz"
    }
    return null
}

fun validateAddress(value: String): String? {
    if (value.isBlank()) {
        return "Adres boş bırakılamaz"
    }
    if (value.length < 10) {
        return "Adres en az 10 karakter olmalıdır"
    }
    return null
} 