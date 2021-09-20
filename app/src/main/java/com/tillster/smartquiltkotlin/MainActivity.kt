package com.tillster.smartquiltkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.tillster.smartquiltkotlin.Models.Quilt
import com.tillster.smartquiltkotlin.Retrofit.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity()
{
    private  val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RetrofitService.ServiceBuilder.buildService().getShapes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))

    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(this,t.message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onFailure: " + t.message)

    }

    private fun onResponse(response: Quilt)
    {

        var txt_activityID = findViewById<TextView>(R.id.txt_activytID)
       var txt_activityName = findViewById<TextView>(R.id.txt_activytName)
        var txt_timeOnTask = findViewById<TextView>(R.id.txt_timeOnTask)
        var txt_correct = findViewById<TextView>(R.id.txt_correct)
        var txt_date = findViewById<TextView>(R.id.txt_date)

        txt_activityID.text= response.LearnShapes.activityID
        txt_activityName.text = response.LearnShapes.acticityName
        txt_timeOnTask.text = response.LearnShapes.timeOnTask
        txt_correct.text = response.LearnShapes.correct
        txt_date.text = response.LearnShapes.date


    }

}
