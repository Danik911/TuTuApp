package com.danik.tutuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.danik.tutuapp.data.work_manager.DownloadTrainsWorker
import com.danik.tutuapp.navigation.SetupNavigation
import com.danik.tutuapp.ui.theme.TuTuAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var worker: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TuTuAppTheme {
                navHostController = rememberNavController()
                SetupNavigation(navController = navHostController)
                worker = WorkManager.getInstance(applicationContext)
                val textInput = remember { mutableStateOf("") }
                val periodicWork =
                    PeriodicWorkRequestBuilder<DownloadTrainsWorker>(5, TimeUnit.SECONDS)
                        .setConstraints(
                            Constraints.Builder()
                                //.setRequiredNetworkType(NetworkType.UNMETERED)
                                //.setRequiresBatteryNotLow(true)
                                //.setRequiresCharging(true)
                                //.setRequiresDeviceIdle(true)
                                .build()
                        ).build()
                worker.enqueueUniquePeriodicWork(
                    "train_loader",
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWork
                )

                worker.getWorkInfoByIdLiveData(periodicWork.id).observe(this, Observer { workInfo ->
                    workInfo.let {
                        if (it.state.isFinished) {
                            val outputData = it.outputData
                            val taskResult = outputData.getString(DownloadTrainsWorker.WORK_RESULT)
                            if (taskResult != null) {
                                textInput.value = taskResult
                            }
                        } else {
                            val workStatus = workInfo.state
                            textInput.value = workStatus.toString()
                        }
                    }
                })
            LaunchedEffect(key1 = true){
                worker.enqueue(periodicWork)
            }
            }
        }
    }
    companion object {
        const val MESSAGE_STATUS = "message_status"
    }
}

