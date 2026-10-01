package com.example.b2becommerce.presentation.auth.register

import android.net.Uri

sealed class RegisterEvent {
    data class CustomerTypeChanged(val value: String) : RegisterEvent()
    data class FullNameChanged(val value: String) : RegisterEvent()
    data class EmailChanged(val value: String) : RegisterEvent()
    data class PhoneChanged(val value: String) : RegisterEvent()
    data class TaxOfficeChanged(val value: String) : RegisterEvent()
    data class TaxNumberChanged(val value: String) : RegisterEvent()
    data class AddressChanged(val value: String) : RegisterEvent()
    data class Address2Changed(val value: String) : RegisterEvent()
    data class CountryChanged(val value: String) : RegisterEvent()
    data class CityChanged(val value: String) : RegisterEvent()
    data class RegionChanged(val value: String) : RegisterEvent()
    data class PostalCodeChanged(val value: String) : RegisterEvent()
    data class IsAccessoriesChanged(val value: Boolean) : RegisterEvent()
    data class IsServiceChanged(val value: Boolean) : RegisterEvent()
    data class IsAvmChanged(val value: Boolean) : RegisterEvent()
    data class IsOilChanged(val value: Boolean) : RegisterEvent()
    data class IsOtoChanged(val value: Boolean) : RegisterEvent()
    data class IsMarketChanged(val value: Boolean) : RegisterEvent()
    data class FileSelected(val uri: Uri) : RegisterEvent()
    object Submit : RegisterEvent()
} 