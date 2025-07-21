package my.app.coffee.model

data class CoffeeLocation(
    val id: String,
    val name: String,
    val point: Point
)

data class Point(
    val latitude: Double,
    val longitude: Double
)