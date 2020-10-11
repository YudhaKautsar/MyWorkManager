package com.example.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOneTimeTask.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
        }
    }

    private fun startOneTimeTask() {
        textStatus.text = getString(R.string.status)
        val data = Data.Builder().putString(MyWorker.EXTRA_CITY, editCity.text.toString()).build()
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val oneeTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).setInputData(data).setConstraints(constraints).build()
        WorkManager.getInstance().enqueue(oneeTimeWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneeTimeWorkRequest.id).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo: WorkInfo) {
                val status = workInfo.state.name
                textStatus.append("\n" + status)
            }
        })
    }

}