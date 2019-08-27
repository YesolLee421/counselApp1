package com.example.counselapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import kotlinx.android.synthetic.main.activity_write_post.*

class WritePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            drawerL_writePost.openDrawer(GravityCompat.START)
        }
    }
}
