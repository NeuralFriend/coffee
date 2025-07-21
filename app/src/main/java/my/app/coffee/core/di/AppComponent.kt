package my.app.coffee.core.di

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.BindsInstance
import dagger.Component
import my.app.coffee.data.ApiService
import my.app.coffee.data.AuthApi
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun authApi(): AuthApi
    fun apiService(): ApiService
    fun provideSharedPreferences(): SharedPreferences
    fun locationProvider(): FusedLocationProviderClient

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}