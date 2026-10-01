import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.b2becommerce.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var errorMessageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        errorMessageTextView = findViewById(R.id.error_message)

        // Kayıt işlemi sırasında hata kontrolü
        performRegister()
    }

    private fun performRegister() {
        // Kayıt işlemi...
        val hasError = true // Örnek hata durumu

        if (hasError) {
            showError("Kayıt başarısız. Lütfen bilgilerinizi kontrol edin.")
        }
    }

    private fun showError(message: String) {
        errorMessageTextView.text = message
        errorMessageTextView.visibility = View.VISIBLE
    }
} 