package com.example.b2becommerce.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import android.util.Patterns
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun ForgotPasswordDialog(
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf("") }

    fun validateEmail(): Boolean {
        return if (email.isEmpty()) {
            isEmailError = true
            emailErrorMessage = "E-posta adresi boş olamaz"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailError = true
            emailErrorMessage = "Geçerli bir e-posta adresi giriniz"
            false
        } else {
            isEmailError = false
            emailErrorMessage = ""
            true
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Şifre Sıfırlama",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Şifrenizi sıfırlamak için e-posta adresinizi giriniz.",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (isEmailError) validateEmail()
                    },
                    label = { Text("E-posta Adresi") },
                    singleLine = true,
                    isError = isEmailError,
                    supportingText = if (isEmailError) {
                        { Text(emailErrorMessage) }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (validateEmail()) {
                        onSubmit(email)
                        onDismiss()
                    }
                }
            ) {
                Text("Gönder")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
} 