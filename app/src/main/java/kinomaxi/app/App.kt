package kinomaxi.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import kinomaxi.feature.backgroundWork.domain.SaveConfigurationWorker
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        val workRequest = OneTimeWorkRequestBuilder<SaveConfigurationWorker>()
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}