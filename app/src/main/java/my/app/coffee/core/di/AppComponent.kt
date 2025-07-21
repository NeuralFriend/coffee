package my.app.coffee.core.di

import android.content.Context
import android.content.SharedPreferences
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}