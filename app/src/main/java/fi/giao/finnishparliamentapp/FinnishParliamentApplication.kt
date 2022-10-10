package fi.giao.finnishparliamentapp

import android.app.Application
import androidx.work.*
import fi.giao.finnishparliamentapp.worker.GetDataAndSaveWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Date: 5/10/2022
 * Name:Giao Ngo
 * Student id: 2112622
 * This application app contains periodic work in background to get data from network
 * and save to database once a day. When the process for the application is created, this
 * application class is instantiated before any other class.
 */
class FinnishParliamentApplication : Application() {
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