data class LoginRequest(
    val customerCode: String,
    val password: String
)

data class ResetPasswordRequest(
    val email: String
)

data class ResetPasswordResponse(
    val statusCode: Int,
    val message: String
) 