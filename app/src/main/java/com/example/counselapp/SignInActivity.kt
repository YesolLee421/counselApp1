package com.example.counselapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btn_signIn.setOnClickListener {
            val nextIntent = Intent(this, MainBoardActivity::class.java)
            startActivity(nextIntent)
        }
    }
}
