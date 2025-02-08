package com.example.b2becommerce.presentation.auth.register

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.b2becommerce.data.remote.model.RegisterFormRequest
import com.example.b2becommerce.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val context: Context
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    // Dosya yolları için state'ler
    private val _filePath1 = mutableStateOf<String?>(null)
    val filePath1: MutableState<String?> = _filePath1

    private val _filePath2 = mutableStateOf<String?>(null)
    val filePath2: MutableState<String?> = _filePath2

    private val _filePath3 = mutableStateOf<String?>(null)
    val filePath3: MutableState<String?> = _filePath3

    private val _filePath4 = mutableStateOf<String?>(null)
    val filePath4: MutableState<String?> = _filePath4

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.CustomerTypeChanged -> {
                state = state.copy(customerType = event.value)
            }
            is RegisterEvent.FullNameChanged -> {
                state = state.copy(
                    fullName = event.value,
                    fullNameError = validateFullName(event.value)
                )
            }
            is RegisterEvent.EmailChanged -> {
                state = state.copy(
                    email = event.value,
                    emailError = validateEmail(event.value)
                )
            }
            is RegisterEvent.PhoneChanged -> {
                state = state.copy(
                    phone = event.value,
                    phoneError = validatePhone(event.value)
                )
            }
            is RegisterEvent.TaxOfficeChanged -> {
                state = state.copy(taxOffice = event.value)
            }
            is RegisterEvent.TaxNumberChanged -> {
                state = state.copy(taxNumber = event.value)
            }
            is RegisterEvent.AddressChanged -> {
                state = state.copy(
                    address = event.value,
                    addressError = validateAddress(event.value)
                )
            }
            is RegisterEvent.Address2Changed -> {
                state = state.copy(address2 = event.value)
            }
            is RegisterEvent.CountryChanged -> {
                state = state.copy(country = event.value)
            }
            is RegisterEvent.CityChanged -> {
                state = state.copy(city = event.value)
            }
            is RegisterEvent.RegionChanged -> {
                state = state.copy(region = event.value)
            }
            is RegisterEvent.PostalCodeChanged -> {
                state = state.copy(postalCode = event.value)
            }
            is RegisterEvent.IsAccessoriesChanged -> {
                state = state.copy(isAccessories = event.value)
            }
            is RegisterEvent.IsServiceChanged -> {
                state = state.copy(isService = event.value)
            }
            is RegisterEvent.IsAvmChanged -> {
                state = state.copy(isAvm = event.value)
            }
            is RegisterEvent.IsOilChanged -> {
                state = state.copy(isOil = event.value)
            }
            is RegisterEvent.IsOtoChanged -> {
                state = state.copy(isOto = event.value)
            }
            is RegisterEvent.IsMarketChanged -> {
                state = state.copy(isMarket = event.value)
            }
            is RegisterEvent.Submit -> {
                if (validateInputs()) {
                    register()
                }
            }
            is RegisterEvent.FileSelected -> handleFileSelection(event.uri)
            else -> Unit
        }
    }

    private fun validateForm(): Boolean {
        val fullNameError = validateFullName(state.fullName)
        val emailError = validateEmail(state.email)
        val phoneError = validatePhone(state.phone)
        val addressError = validateAddress(state.address)

        state = state.copy(
            fullNameError = fullNameError,
            emailError = emailError,
            phoneError = phoneError,
            addressError = addressError
        )

        return fullNameError == null && emailError == null && 
               phoneError == null && addressError == null
    }

    private fun validateInputs(): Boolean {
        val fullNameError = validateFullName(state.fullName)
        val emailError = validateEmail(state.email)
        val phoneError = validatePhone(state.phone)
        val addressError = validateAddress(state.address)

        state = state.copy(
            fullNameError = fullNameError,
            emailError = emailError,
            phoneError = phoneError,
            addressError = addressError
        )

        return fullNameError == null && emailError == null && 
               phoneError == null && addressError == null
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val request = RegisterFormRequest(
                    customerType = state.customerType,
                    fullName = state.fullName,
                    email = state.email,
                    phone = state.phone,
                    taxOffice = state.taxOffice,
                    taxNumber = state.taxNumber,
                    isAccessories = state.isAccessories,
                    isService = state.isService,
                    isAvm = state.isAvm,
                    isOil = state.isOil,
                    isOto = state.isOto,
                    isMarket = state.isMarket,
                    address = state.address,
                    address2 = state.address2,
                    country = state.country,
                    city = state.city,
                    region = state.region,
                    postalCode = state.postalCode,
                    filePath1 = _filePath1.value,
                    filePath2 = _filePath2.value,
                    filePath3 = _filePath3.value,
                    filePath4 = _filePath4.value
                )
                
                authRepository.register(request)
                    .onSuccess {
                        state = state.copy(
                            isLoading = false,
                            successMessage = "Kayıt başarıyla oluşturuldu"
                        )
                    }
                    .onFailure { e ->
                        state = state.copy(
                            isLoading = false,
                            error = e.message ?: "Kayıt oluşturulamadı",
                            successMessage = null
                        )
                    }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Bir hata oluştu"
                )
            }
        }
    }

    private fun handleFileSelection(uri: Uri) {
        val fileName = getFileNameFromUri(uri)
        
        when {
            _filePath1.value == null -> _filePath1.value = fileName
            _filePath2.value == null -> _filePath2.value = fileName
            _filePath3.value == null -> _filePath3.value = fileName
            _filePath4.value == null -> _filePath4.value = fileName
        }
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var fileName = ""
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }
        return fileName
    }

    // Dosya yollarını güncelleyen fonksiyonlar
    fun updateFilePath1(path: String?) {
        _filePath1.value = path
    }

    fun updateFilePath2(path: String?) {
        _filePath2.value = path
    }

    fun updateFilePath3(path: String?) {
        _filePath3.value = path
    }

    fun updateFilePath4(path: String?) {
        _filePath4.value = path
    }
} 