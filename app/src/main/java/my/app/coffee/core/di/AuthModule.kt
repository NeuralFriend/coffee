package my.app.coffee.core.di

import dagger.Module
import dagger.Provides
import my.app.coffee.data.AuthApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}