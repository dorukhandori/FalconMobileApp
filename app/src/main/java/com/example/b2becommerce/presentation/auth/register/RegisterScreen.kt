package com.example.b2becommerce.presentation.auth.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.b2becommerce.presentation.common.components.CustomTextField
import com.example.b2becommerce.presentation.common.components.CustomButton
import com.example.b2becommerce.util.Constants.TURKISH_CITIES
import androidx.compose.foundation.lazy.grid.*
import com.example.b2becommerce.presentation.common.components.CustomDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var cityExpanded by remember { mutableStateOf(false) }
    val customerTypes = listOf("KURUMSAL", "BİREYSEL", "GERÇEK KİŞİ")

    // Dosya yollarını al
    val filePath1 by viewModel.filePath1
    val filePath2 by viewModel.filePath2
    val filePath3 by viewModel.filePath3
    val filePath4 by viewModel.filePath4

    // Dosya seçici
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            viewModel.onEvent(RegisterEvent.FileSelected(selectedUri))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Yeni Üye Formu",
            style = MaterialTheme.typography.headlineMedium
        )

        // Müşteri Tipi
        CustomDropdownMenu(
            label = "Müşteri Tipi",
            selectedOption = state.customerType,
            options = customerTypes,
            onOptionSelected = { viewModel.onEvent(RegisterEvent.CustomerTypeChanged(it)) },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        )

        // İki sütunlu form alanları
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Sol sütun
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = state.fullName,
                    onValueChange = { viewModel.onEvent(RegisterEvent.FullNameChanged(it)) },
                    label = "Ad Soyad",
                    error = state.fullNameError
                )

                CustomTextField(
                    value = state.phone,
                    onValueChange = { viewModel.onEvent(RegisterEvent.PhoneChanged(it)) },
                    label = "Telefon",
                    error = state.phoneError
                )

                CustomTextField(
                    value = state.taxNumber,
                    onValueChange = { viewModel.onEvent(RegisterEvent.TaxNumberChanged(it)) },
                    label = "Vergi No"
                )

                CustomTextField(
                    value = state.address2,
                    onValueChange = { viewModel.onEvent(RegisterEvent.Address2Changed(it)) },
                    label = "Adres 2"
                )

                CustomDropdownMenu(
                    label = "Şehir",
                    selectedOption = state.city,
                    options = TURKISH_CITIES,
                    onOptionSelected = { viewModel.onEvent(RegisterEvent.CityChanged(it)) },
                    expanded = cityExpanded,
                    onExpandedChange = { cityExpanded = it }
                )
            }

            // Sağ sütun
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
                    label = "E-posta",
                    error = state.emailError
                )

                CustomTextField(
                    value = state.taxOffice,
                    onValueChange = { viewModel.onEvent(RegisterEvent.TaxOfficeChanged(it)) },
                    label = "Vergi Dairesi"
                )

                CustomTextField(
                    value = state.address,
                    onValueChange = { viewModel.onEvent(RegisterEvent.AddressChanged(it)) },
                    label = "Adres",
                    error = state.addressError
                )

                CustomTextField(
                    value = state.country,
                    onValueChange = { viewModel.onEvent(RegisterEvent.CountryChanged(it)) },
                    label = "Ülke"
                )

                CustomTextField(
                    value = state.region,
                    onValueChange = { viewModel.onEvent(RegisterEvent.RegionChanged(it)) },
                    label = "İlçe"
                )
            }
        }

        CustomTextField(
            value = state.postalCode,
            onValueChange = { viewModel.onEvent(RegisterEvent.PostalCodeChanged(it)) },
            label = "Posta Kodu",
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox'lar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CheckboxWithLabel(
                checked = state.isAccessories,
                onCheckedChange = { viewModel.onEvent(RegisterEvent.IsAccessoriesChanged(it)) },
                label = "Aksesuar"
            )
            CheckboxWithLabel(
                checked = state.isService,
                onCheckedChange = { viewModel.onEvent(RegisterEvent.IsServiceChanged(it)) },
                label = "Servis"
            )
            CheckboxWithLabel(
                checked = state.isAvm,
                onCheckedChange = { viewModel.onEvent(RegisterEvent.IsAvmChanged(it)) },
                label = "AVM"
            )
        }

        // Dosya yükleme alanları
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FileUploadField(
                label = "Ticaret Sicil Gazetesi",
                fileName = filePath1,
                onSelectFile = { filePickerLauncher.launch("*/*") }
            )
            FileUploadField(
                label = "Faaliyet Belgesi",
                fileName = filePath2,
                onSelectFile = { filePickerLauncher.launch("*/*") }
            )
            FileUploadField(
                label = "Vergi Levhası",
                fileName = filePath3,
                onSelectFile = { filePickerLauncher.launch("*/*") }
            )
            FileUploadField(
                label = "İmza beyanı",
                fileName = filePath4,
                onSelectFile = { filePickerLauncher.launch("*/*") }
            )
        }

        // Butonlar
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.onEvent(RegisterEvent.Submit) },
                modifier = Modifier.weight(1f),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Formu Ekle")
                }
            }

            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Girişe Dön")
            }
        }

        // Hata ve başarı mesajları
        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (state.successMessage != null) {
            Text(
                text = state.successMessage,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CheckboxWithLabel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun FileUploadField(
    label: String,
    fileName: String?,
    onSelectFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedButton(
            onClick = onSelectFile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = "Dosya Seç"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = fileName?.substringAfterLast('/') ?: "Dosya Seç",
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (fileName == null) 0.6f else 1f)
            )
        }
    }
} 