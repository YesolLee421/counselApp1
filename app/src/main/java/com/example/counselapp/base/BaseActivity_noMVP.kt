package com.example.counselapp.base

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity_noMVP  : AppCompatActivity(){

    fun getLog(TAG: String, msg: String){
        Log.d(TAG,msg)
    }
    fun showToast(msg: String, context: Context) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
}