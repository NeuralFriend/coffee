package my.app.coffee.data

import my.app.coffee.model.CoffeeLocation
import my.app.coffee.model.MenuItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("locations")
    suspend fun getLocations(): List<CoffeeLocation>

    @GET("location/{id}/menu")
    suspend fun getMenu(@Path("id") locationId: Int): List<MenuItem>
}

