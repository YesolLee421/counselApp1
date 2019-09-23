package com.example.counselapp.CounselList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.counselapp.R
import kotlinx.android.synthetic.main.activity_counsel_managing.*

class CounselManagingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counsel_managing)

        btn_counsel_managing.setOnClickListener {
            val intent = Intent(this, CounselDetailActivity::class.java)
            startActivity(intent)
        }

    }
}
