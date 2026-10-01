package com.example.b2becommerce

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.b2becommerce.R

class LoginActivity : AppCompatActivity() {
    // ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Buton tıklama olayını bağlayın
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            onLoginButtonClick()
        }
    }

    private fun validateInput(username: String?, email: String?): Boolean {
        return when {
            username.isNullOrEmpty() -> {
                showToast("Kullanıcı adı veya müşteri kodu giriniz.")
                false
            }
            email.isNullOrEmpty() -> {
                showToast("E-posta adresi giriniz.")
                false
            }
            !isValidEmail(email) -> {
                showToast("Geçerli bir e-posta adresi giriniz.")
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun onLoginButtonClick() {
        val username = findViewById<EditText>(R.id.usernameEditText).text.toString()
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()

        if (validateInput(username, email)) {
            // Giriş işlemlerini gerçekleştir
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
} 