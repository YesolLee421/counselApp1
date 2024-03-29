package com.example.counselapp.expInfo

import android.content.Context
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.counselapp.counselList.CounselManagingActivity
import com.example.counselapp.MainBoardActivity
import com.example.counselapp.myPage.MyPageExpActivity
import com.example.counselapp.R
import com.example.counselapp.SearchExpertActivity
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Expert
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient
import com.example.counselapp.Network.RetrofitClient2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_expert.*
import kotlinx.android.synthetic.main.activity_write_expert_profile.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileExpertActivity : BaseActivity_noMVP() {

    var TAG = "ProfileExpertActivity"
    var portrait_str: String = "http://10.0.2.2:3000/"

    // retrofitClient, service 객체 생성
    var mContext: Context = this
    val retrofitClient: OkHttpClient = RetrofitClient2.getClient(mContext, "AddCookie")
    lateinit var service: CounselAppService
    lateinit var expert: Expert
    var expertId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_expert)

        // 인텐트로 전달한 _id
        expertId = intent.getStringExtra("expertId")
        getLog(TAG,expertId!!)

        // 서비스 시작
        service = RetrofitClient2.serviceAPI(retrofitClient)

        //드로워 네비게이션 뷰 등록
        val drawerNav = findViewById<View>(R.id.navigation_profile_expert) as NavigationView
        drawerNav.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_main_nav_myPage ->{
                    val intentMyPage = Intent(this, MyPageExpActivity::class.java)
                    startActivity(intentMyPage)
                }
                R.id.menu_main_nav_counselList ->{
                    val intentCounselList = Intent(this, CounselManagingActivity::class.java)
                    startActivity(intentCounselList)
                }
                R.id.menu_main_nav_bookmark -> Toast.makeText(this,"즐겨찾기 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_logOut -> Toast.makeText(this,"로그아웃 클릭", Toast.LENGTH_SHORT).show()
                R.id.menu_main_nav_setting -> Toast.makeText(this,"설정 클릭", Toast.LENGTH_SHORT).show()
            }
            true
        }

        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            drawerL_profile_expert.openDrawer(GravityCompat.START)
        }

        // 수정 버튼
        btn_profile_expert_edit.setOnClickListener {
            val intent = Intent(this, WriteExpertProfileActivity::class.java)
            intent.putExtra("expertId", expertId)
            intent.putExtra("isFirst", false)
            startActivity(intent)
            finish()
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_profile_expert) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottonbar_searchExp ->{
                    val intentExp = Intent(this, SearchExpertActivity::class.java)
                    startActivity(intentExp)
                    finish()
                }
                R.id.bottonbar_mainB ->{
                    val intentMain = Intent(this, MainBoardActivity::class.java)
                    startActivity(intentMain)
                    finish()
                }
                R.id.bottonbar_getCousel ->
                    Toast.makeText(this@ProfileExpertActivity,"상담받기",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        // 데이터 불러오기
        val call = service.getExpert(expertId!!)
        call.enqueue(object : Callback<Expert> {
            override fun onFailure(call: Call<Expert>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                showToast(t.message.toString(), this@ProfileExpertActivity);
            }
            override fun onResponse(call: Call<Expert>, response: Response<Expert>) {
                if(response.code()==200){
                    expert= response.body()!!
                    text_profile_expert_name.text = "${expert.name_formal} 상담사"
                    text_profile_expert_about.text = expert.about
                    text_profile_expert_career.text = expert.career
                    text_profile_expert_certificate.text = expert.certificate
                    text_profile_expert_education.text = expert.education
                    text_profile_expert_major.text = expert.major
                    showToast("level: ${expert.level}",this@ProfileExpertActivity)
                    when(expert.level){
                        1->img_profile_expert_lv.setImageResource(R.drawable.lv1)
                        2->img_profile_expert_lv.setImageResource(R.drawable.lv2)
                        3->img_profile_expert_lv.setImageResource(R.drawable.lv3)
                    }
                    // 서버에서 받은 이미지 로드->Picasso
                    // 프로필 사진 넣어주기
                    if(expert.portrait!=null){
                        portrait_str += expert.portrait
                        Glide.with(this@ProfileExpertActivity).load(portrait_str).into(img_profile_expert_lv)
                    }
                    Log.d(TAG,"onResponse: 성공")

                }
            }
        })
    }

    override fun onBackPressed() {
        if(drawerL_profile_expert.isDrawerOpen(GravityCompat.START)){
            drawerL_profile_expert.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}
