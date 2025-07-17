package my.app.coffee.model

data class AuthResponse(
    val token: String,
    val tokenLifeTime: Long
)