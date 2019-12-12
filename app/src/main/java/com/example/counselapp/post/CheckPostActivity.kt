package com.example.counselapp.post

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import com.example.counselapp.counselList.CounselManagingActivity
import com.example.counselapp.MainBoardActivity
import com.example.counselapp.myPage.MyPageExpActivity
import com.example.counselapp.R
import com.example.counselapp.SearchExpertActivity
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Post
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient
import com.example.counselapp.Network.RetrofitClient2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_checkpost.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CheckPostActivity : BaseActivity_noMVP() {

    var TAG = "CheckPostActivity"

    // retrofitClient, service 객체 생성
    val mContext : Context = this
    val retrofitClient: OkHttpClient = RetrofitClient2.getClient(mContext,"addCookies")
    lateinit var service: CounselAppService
    lateinit var post: Post
    lateinit var _id: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkpost)
        // 인텐트로 전달한 _id
        _id = intent.getStringExtra("postId")
        getLog(TAG,_id)

        // 서비스 시작
        service = RetrofitClient2.serviceAPI(retrofitClient)


        // 수정 버튼 클릭
        text_checkPost_update.setOnClickListener {
            val intentUpdate = Intent(this, WritePostActivity::class.java)
            intentUpdate.putExtra("postId", _id)
            intentUpdate.putExtra("title", text_checkPost_title.text.toString())
            intentUpdate.putExtra("content", text_checkPost_content.text.toString())
            getLog(TAG, intentUpdate.getStringExtra("title"))
            startActivity(intentUpdate)
            finish()
        }

        // 삭제 버튼 클릭
        text_checkPost_delete.setOnClickListener {
            // dialog 띄워서 묻고 삭제

            val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
            builder.setMessage("정말로 삭제하시겠습니까?")

            builder.setPositiveButton("확인") { _, _ ->
                val call = service.deletePost(_id)
                call.enqueue(object : Callback<String>{
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d(TAG,"onFailure: ${t.message}")
                        showToast(t.message.toString(), this@CheckPostActivity);
                    }
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.code()==200){
                            Log.d(TAG,"onResponse: 성공")
                            showToast(response.body().toString(), this@CheckPostActivity)
//                            val intent = Intent(this@CheckPostActivity, MainBoardActivity::class.java)
//                            startActivity(intent)
                            finish()
                        }
                    }
                })
            }
            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu_check_post.setOnClickListener {
            drawerL_checkPost.openDrawer(GravityCompat.START)
        }

        //드로워 네비게이션 뷰 등록
        val drawerNav = findViewById<View>(R.id.navigation_check_post) as NavigationView
        drawerNav.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_main_nav_myPage ->{
                    val intentMyPage = Intent(this, MyPageExpActivity::class.java)
                    startActivity(intentMyPage)
                    finish()
                }
                R.id.menu_main_nav_counselList ->{
                    val intentCounselList = Intent(this, CounselManagingActivity::class.java)
                    startActivity(intentCounselList)
                    finish()
                }
                R.id.menu_main_nav_bookmark -> showToast("즐겨찾기 클릭",this)
                R.id.menu_main_nav_logOut -> showToast("로그아웃 클릭",this)
                R.id.menu_main_nav_setting -> showToast("설정 클릭",this)
            }
            true
        }

        //하단 네비게이션뷰 등록
        val bottomNavigation = findViewById<View>(R.id.bottom_nav_check_post) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottonbar_searchExp ->{
                    val intentExp = Intent(this, SearchExpertActivity::class.java)
                    startActivity(intentExp)
                    finish()
                }
                R.id.bottonbar_mainB ->{
                    val intentExp = Intent(this, MainBoardActivity::class.java)
                    startActivity(intentExp)
                    finish()
                }
                R.id.bottonbar_getCousel ->
                    Toast.makeText(this@CheckPostActivity,"상담받기",Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onStart() {
        super.onStart()
        // 데이터 불러오기
        val call = service.getPost(_id)
        call.enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                showToast(t.message.toString(), this@CheckPostActivity);
            }
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.code()==200){
                    post = response.body()!!
                    text_checkPost_title.text = post.title
                    text_checkPost_content.text = post.content
                    text_checkPost_hit_like_comments.text = "조회 ${post.hit} | 격려 ${post.like} | 댓글 ${post.comments}"
                    text_checkPost_commenter_date_lastChanged.text = "${post.commenter} | ${post.date_lastChanged}"
                    //Log.d(TAG,"onResponse: 성공")
                    //view.showToast(response.body().toString())
                }
            }
        })
    }
    /*
 * 뒤로가기 버튼으로 네비게이션 닫기
 *
 * 네비게이션 드로어가 열려 있을 때 뒤로가기 버튼을 누르면 네비게이션을 닫고,
 * 닫혀 있다면 기존 뒤로가기 버튼으로 작동한다.
 */
    override fun onBackPressed() {
        if(drawerL_checkPost.isDrawerOpen(GravityCompat.START)){
            drawerL_checkPost.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }


}
