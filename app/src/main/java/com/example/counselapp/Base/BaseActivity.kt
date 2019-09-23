package com.example.counselapp.Base


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    abstract fun initPresenter()

    // TAG, msg 받아서
    fun getLog(TAG: String, msg: String){
        Log.d(TAG,msg)
    }


}
