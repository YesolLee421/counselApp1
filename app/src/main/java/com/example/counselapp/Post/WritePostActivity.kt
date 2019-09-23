package com.example.counselapp.Post

import android.content.Intent

import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.counselapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_write_post.*

class WritePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            //drawerL_writePost.openDrawer(GravityCompat.START)
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_write_post) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_bar_writePost_camera ->
                    Toast.makeText(this,"카메라실행", Toast.LENGTH_SHORT).show()
                R.id.bottom_bar_writePost_gallery ->{
                    Toast.makeText(this,"앨범 열기", Toast.LENGTH_SHORT).show()
                }
                R.id.bottom_bar_writePost_save -> {
                    val intent = Intent(this, CheckPostActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }
}
