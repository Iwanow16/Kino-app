package kinomaxi.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kinomaxi.AppComponent

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
    }
}