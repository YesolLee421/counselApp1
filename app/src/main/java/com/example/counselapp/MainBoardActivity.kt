package com.example.counselapp

import android.content.Intent

import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.Base.BaseActivity
import com.example.counselapp.CounselList.CounselManagingActivity
import com.example.counselapp.MyPage.MyPageExpActivity
import com.example.counselapp.Post.CheckPostActivity
import com.example.counselapp.Post.WritePostActivity
import com.example.counselapp.Presenter.MainboardContract
import com.example.counselapp.Presenter.MainboardPresenter
import com.example.counselapp.adapter.mainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_mainboard.*

class MainBoardActivity : BaseActivity() , MainboardContract.View{
    // 일대일 연결할 프레젠터 선언
    // 리사이클러뷰, 레이아웃매니저, 데이터리스트 등

    private val recyclerView by lazy {
        findViewById(R.id.rc_mainB) as RecyclerView
    }
    private lateinit var presenter: MainboardPresenter
    private lateinit var adapter: mainAdapter

    override fun initPresenter() {
        presenter = MainboardPresenter().apply{
            view = this@MainBoardActivity
            postList = postList
            adapterModel = adapter
            adapterView = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainboard)

        // 리사이클러뷰, 어댑터, 프레젠터
        adapter = mainAdapter(this)
        recyclerView.adapter = adapter
        initPresenter()
        presenter.loadItems(this,false)

        // 드로워 네비게이션 뷰 등록
        val drawerNav = findViewById<View>(R.id.navigation_mainB) as NavigationView
        drawerNav.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_main_nav_myPage->{
                    val intentMyPage = Intent(this, MyPageExpActivity::class.java)
                    startActivity(intentMyPage)
                }
                R.id.menu_main_nav_counselList->{
                    val intentCounselList = Intent(this, CounselManagingActivity::class.java)
                    startActivity(intentCounselList)
                }
                R.id.menu_main_nav_bookmark-> Toast.makeText(this,"즐겨찾기 클릭",Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_logOut-> Toast.makeText(this,"로그아웃 클릭",Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_setting-> Toast.makeText(this,"설정 클릭",Toast.LENGTH_SHORT).show()
            }
            true
        }

        //툴바 메뉴 ->드로워 오픈
        ic_toolbar_menu.setOnClickListener {
            drawerL_mainB.openDrawer(GravityCompat.START)
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_mainB) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottonbar_mainB->
                    Toast.makeText(this@MainBoardActivity,"메인화면",Toast.LENGTH_SHORT).show()
                R.id.bottonbar_searchExp->{
                    val intentExp = Intent(this,SearchExpertActivity::class.java)
                    startActivity(intentExp)
                }
                R.id.bottonbar_getCousel-> {
                    val intentCase = Intent(this, CounselManagingActivity::class.java)
                    startActivity(intentCase)
                }
            }
            true
        }
        btn_mainB_addPost.setOnClickListener {
            val intent = Intent(this, WritePostActivity::class.java)
            startActivity(intent)
        }
    }








    /*
     * 뒤로가기 버튼으로 네비게이션 닫기
     *
     * 네비게이션 드로어가 열려 있을 때 뒤로가기 버튼을 누르면 네비게이션을 닫고,
     * 닫혀 있다면 기존 뒤로가기 버튼으로 작동한다.
     */
    override fun onBackPressed() {
        if(drawerL_mainB.isDrawerOpen(GravityCompat.START)){
            drawerL_mainB.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }

}
