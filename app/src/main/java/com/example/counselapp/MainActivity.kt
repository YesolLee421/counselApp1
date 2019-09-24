package com.example.counselapp

import android.content.Intent

import android.os.Bundle
import com.example.counselapp.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        val nextIntent = Intent(this, LogInActivity::class.java)
        startActivity(nextIntent)
    }

}
