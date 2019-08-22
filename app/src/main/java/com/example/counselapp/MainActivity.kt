package com.example.counselapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

//    override fun onResume() {
//        super.onResume()
//        val intent = Intent(this, LogInActivity::class.java)
//        startActivity(intent)
//    }

}
