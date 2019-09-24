package com.example.counselapp.base


import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initPresenter()

    // TAG, msg 받아서
    fun getLog(TAG: String, msg: String){
        Log.d(TAG,msg)
    }


}
