package com.example.counselapp.counselList

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.counselapp.R
import kotlinx.android.synthetic.main.activity_counsel_detail.*

class CounselDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counsel_detail)

        btn_myPage_edit.setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)
            startActivity(intent)
        }
    }
}
