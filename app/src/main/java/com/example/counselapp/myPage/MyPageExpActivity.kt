package com.example.counselapp.myPage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.counselapp.R
import kotlinx.android.synthetic.main.activity_my_page_exp.*

class MyPageExpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_exp)

        btn_myPage_edit.setOnClickListener {
            val intent = Intent(this, MyPageEditActivity::class.java)
            startActivity(intent)
        }
    }
}
