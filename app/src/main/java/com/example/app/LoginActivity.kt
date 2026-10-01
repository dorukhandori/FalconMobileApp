class LoginActivity : AppCompatActivity() {
    private lateinit var errorMessageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        errorMessageTextView = findViewById(R.id.error_message)

        // Giriş işlemi sırasında hata kontrolü
        performLogin()
    }

    private fun performLogin() {
        // Giriş işlemi...
        val hasError = true // Örnek hata durumu

        if (hasError) {
            showError("Giriş başarısız. Lütfen bilgilerinizi kontrol edin.")
        }
    }

    private fun showError(message: String) {
        errorMessageTextView.text = message
        errorMessageTextView.visibility = View.VISIBLE
    }
} 