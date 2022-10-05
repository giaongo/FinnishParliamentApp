package fi.giao.finnishparliamentapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.repository.AppRepository
import timber.log.Timber
/**
 * Date: 5/10/2022
 * Name:Giao Ngo
 * Student id: 2112622
 * This worker class gets data from network and saves to database
 */
class GetDataAndSaveWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){
    companion object {
        const val WORK_NAME = "fetch_network_and_save_to_database_work"
    }
    override suspend fun doWork(): Result {
        val repository = AppRepository(AppDatabase.getInstance(applicationContext))
        return try {
            repository.getDataFromNetworkAndSave()
            Timber.d("Networking fetching and saving is run")
            Result.success()
        } catch(throwable:Throwable) {
            Timber.e("error fetching network and save")
            Result.retry()
        }
    }
}