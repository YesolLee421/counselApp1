package com.example.counselapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_profile_expert.*

class ProfileExpertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_expert)

        //드로워 네비게이션 뷰 등록
        val drawerNav = findViewById<View>(R.id.navigation_profile_expert) as NavigationView
        drawerNav.setNavigationItemSelectedListener { item ->
            when(item!!.itemId){
                R.id.menu_main_nav_myPage-> Toast.makeText(this,"마이페이지", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_bookmark-> Toast.makeText(this,"즐겨찾기 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_logOut-> Toast.makeText(this,"로그아웃 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_setting-> Toast.makeText(this,"설정 클릭", Toast.LENGTH_SHORT).show()
            }
            true
        }

        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            drawerL_profile_expert.openDrawer(GravityCompat.START)
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_profile_expert) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item!!.itemId){
                R.id.bottonbar_searchExp->{
                    val intentExp = Intent(this,SearchExpertActivity::class.java)
                    startActivity(intentExp)
                    finish()
                }
                R.id.bottonbar_mainB->{
                    val intentMain = Intent(this,MainBoardActivity::class.java)
                    startActivity(intentMain)
                    finish()
                }
                R.id.bottonbar_getCousel->
                    Toast.makeText(this@ProfileExpertActivity,"상담받기",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onBackPressed() {
        if(drawerL_profile_expert.isDrawerOpen(GravityCompat.START)){
            drawerL_profile_expert.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}
