package com.example.counselapp

import android.content.Intent

import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.R.layout.activity_search_expert
import com.example.counselapp.adapter.SearchExpertAdapter
import com.example.counselapp.base.BaseActivity
import com.example.counselapp.counselList.CounselManagingActivity
import com.example.counselapp.expInfo.ProfileExpertActivity
import com.example.counselapp.myPage.MyPageExpActivity
import com.example.counselapp.presenter.MainboardContract
import com.example.counselapp.presenter.SearchExpPresenter
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_search_expert.*
import retrofit2.Retrofit

class SearchExpertActivity : BaseActivity(), MainboardContract.View {

    override fun moveTo(_id: String?, type: String) {
        var intent:Intent? = null

        if(type.equals("logout")){
            intent = Intent(this, LogInActivity::class.java)
        } else{
            intent = Intent(this,ProfileExpertActivity::class.java)
            intent.putExtra("expertId", _id)
        }
        startActivity(intent)
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.rc_searchExp) }
    private lateinit var presenter: SearchExpPresenter
    private lateinit var adapter: SearchExpertAdapter

    override fun initPresenter() {
        // 서비스 시작
        service = retrofitClient.create(CounselAppService::class.java)
        presenter = SearchExpPresenter().apply {
            view = this@SearchExpertActivity
            presenterService = service
            adapterModel = adapter
            adapterView = adapter
            // 데이터는 loadItems() 메소드 안에서 선언
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this,"$message 상담사 선택",Toast.LENGTH_SHORT).show()
    }
    // 서비스 선언
    lateinit var service: CounselAppService
    // retrofitClient 객체 생성
    val retrofitClient: Retrofit = RetrofitClient.instance
    var TAG = "SearchExpActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_search_expert)

        //리사이클러뷰, 어댑터, 레이아웃 매니저, 프레젠터 선언 및 loadItems()
        adapter = SearchExpertAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        initPresenter()

        //드로워 네비게이션 뷰 등록
        val drawerNav = findViewById<View>(R.id.navigation_searchExp) as NavigationView
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
                R.id.menu_main_nav_bookmark-> Toast.makeText(this,"즐겨찾기 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_logOut-> Toast.makeText(this,"로그아웃 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_setting-> Toast.makeText(this,"설정 클릭", Toast.LENGTH_SHORT).show()
            }
            true
        }

        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            //drawerL_searchExp.openDrawer(GravityCompat.START)
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_searchExp) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottonbar_searchExp->
                    Toast.makeText(this@SearchExpertActivity,"상담사찾기",Toast.LENGTH_SHORT).show()
                R.id.bottonbar_mainB->{
                    val intentExp = Intent(this,MainBoardActivity::class.java)
                    startActivity(intentExp)
                    finish()
                }
                R.id.bottonbar_getCousel->
                    Toast.makeText(this@SearchExpertActivity,"상담받기",Toast.LENGTH_SHORT).show()
            }
            true
        }


    }

    override fun onStart() {
        super.onStart()
        presenter.loadItems(this,false)
    }

    override fun onBackPressed() {
        if(drawerL_searchExp.isDrawerOpen(GravityCompat.START)){
            drawerL_searchExp.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }

}
