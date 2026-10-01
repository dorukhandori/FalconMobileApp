package com.example.b2becommerce

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.b2becommerce.presentation.auth.login.SplashScreen
import com.example.b2becommerce.presentation.navigation.NavGraph
import com.example.b2becommerce.ui.theme.B2BEcommerceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash Screen'i göster
        setContent {
            B2BEcommerceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen() // SplashScreen bileşenini çağırıyoruz
                }
            }
        }

        // 2 saniye sonra ana içeriği ayarla
        Handler(Looper.getMainLooper()).postDelayed({
            setContent {
                B2BEcommerceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavGraph(navController = navController)
                    }
                }
            }
        }, 2000) // 2 saniye bekle
    }
} 