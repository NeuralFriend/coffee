package my.app.coffee.data

import my.app.coffee.model.CoffeeLocation
import retrofit2.http.GET

interface ApiService {
    @GET("locations")
    suspend fun getLocations(): List<CoffeeLocation>
}