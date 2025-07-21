package my.app.coffee.core

import android.app.Application
import my.app.coffee.core.di.AppComponent
import my.app.coffee.core.di.AppModule
import my.app.coffee.core.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .appModule(AppModule(this))
            .build()
    }
}