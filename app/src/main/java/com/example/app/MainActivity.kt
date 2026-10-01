import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.b2becommerce.R
import com.example.app.PreferencesHelper
import com.example.app.ApiService

class MainActivity : AppCompatActivity() {
    private lateinit var preferencesHelper: PreferencesHelper
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencesHelper = PreferencesHelper(this)
        apiService = ApiService(preferencesHelper)

        // xcmzkey'i saklayın
        preferencesHelper.saveXcmzKey("NX3qKA25bqwquuFdOcckvNdWkZYIy0RF4tNw+hwgYS43jsm07rwosdpO0Meh1I/gzVXt580rIOGdYFMBDLwo3vBfFxeuOPvu6x0Fa+n2s/XPcHVaCiEnoL0mdN3pCOPLv4UnPBJZGtZdEYwo1//0qHdif7TcnvrWUCyGtJLUTR/eOLo4bY64d5tebRU/wovQ")

        // Login işlemi
        apiService.login("TEST", "123456") { success ->
            if (success) {
                // Giriş başarılı
            } else {
                // Giriş başarısız
            }
        }
    }
} 