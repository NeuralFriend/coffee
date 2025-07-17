package my.app.coffee.core.di

import dagger.Component
import my.app.coffee.data.AuthApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AuthModule::class])
interface AppComponent {
    fun retrofit(): Retrofit
    fun authApi(): AuthApi
}