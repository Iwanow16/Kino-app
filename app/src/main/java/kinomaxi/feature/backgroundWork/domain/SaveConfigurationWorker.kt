package kinomaxi.feature.backgroundWork.domain

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SaveConfigurationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val saveConfigurationUseCase: SaveConfigurationUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend  fun doWork(): Result {
        saveConfigurationUseCase()
        return Result.success()
    }
}