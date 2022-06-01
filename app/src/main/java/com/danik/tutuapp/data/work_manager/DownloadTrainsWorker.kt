package com.danik.tutuapp.data.work_manager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danik.tutuapp.MainActivity
import com.danik.tutuapp.R
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.remote.TrainApi
import com.danik.tutuapp.data.repository.RemoteDataSourceImpl
import com.danik.tutuapp.data.repository.Repository
import com.danik.tutuapp.domain.use_cases.UseCases
import com.danik.tutuapp.domain.use_cases.get_all_trains.GetAllTrainsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltWorker
class DownloadTrainsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val useCases: UseCases
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {

        val taskData = inputData
        val taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS)
        showNotification("Updating list...", taskDataString.toString())
        return withContext(Dispatchers.IO) {
            try {
                useCases.getAllTrainsUseCase()
                Log.d("DownloadTrainsWorker", "DownloadTrainsWorker Called")
                Result.success()

            } catch (e: IOException) {
                Result.failure()
            }

        }
    }

    private fun showNotification(task: String, desc: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, "download_channel")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_search_document)

        manager.notify(1, builder.build())
    }

    companion object {
        const val WORK_RESULT = "work_result"
    }
}
