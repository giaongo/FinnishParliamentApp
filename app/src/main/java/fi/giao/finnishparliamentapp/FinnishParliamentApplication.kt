package fi.giao.finnishparliamentapp

import android.app.Application
import androidx.work.*
import fi.giao.finnishparliamentapp.worker.GetDataAndSaveWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FinnishParliamentApplication: Application(){
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }
    private fun delayedInit() {
        applicationScope.launch {
            setUpRecurringWork()
        }
    }
    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<GetDataAndSaveWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        Timber.d("Periodic work request is scheduled")
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                GetDataAndSaveWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
    }


}