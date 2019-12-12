package com.example.counselapp.post

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.counselapp.R
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Post
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient
import com.example.counselapp.Network.RetrofitClient2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_write_post.*
import kotlinx.android.synthetic.main.activity_write_post.ic_toolbar_menu
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class WritePostActivity : BaseActivity_noMVP() {

    var TAG = "WritePostActivity"

    // retrofitClient, service 객체 생성
    lateinit var service: CounselAppService
    lateinit var post: Post
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        // 인텐트 있으면 (수정 시)
        val postId: String? = intent.getStringExtra("postId")
        var title = intent.getStringExtra("title")
        var content = intent.getStringExtra("content");
        val picture: File? = null

        et_writePost_title.setText(title)
        et_writePost_content.setText(content)

        // 서비스 시작
        //service = retrofitClient.create(CounselAppService::class.java)


        //툴바- 메뉴 클릭 등록
        ic_toolbar_menu.setOnClickListener {
            drawerL_writePost.openDrawer(GravityCompat.START)
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
                R.id.bottom_bar_writePost_save -> { // 저장 버튼 클릭
                    title = et_writePost_title.text.toString()
                    content = et_writePost_content.text.toString()
                    val client : OkHttpClient = RetrofitClient2.getClient(context, "addCookies")
                    service = RetrofitClient2.serviceAPI(client)

                    if(title!="" && content!=""){
                        if(postId==null){ // _id ==null 이면 새로 생성, 아니면 수정
                            val call = service.writePost(title!!, content!!, picture)
                            call.enqueue(object : Callback<String>{
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d(TAG,"onFailure: ${t.message}")
                                    showToast(t.message.toString(), this@WritePostActivity);
                                }
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    if(response.code()==201){
                                        Log.d(TAG,"onResponse: 성공 ${response.body()}")
                                        showToast(response.body().toString(), this@WritePostActivity)
                                        val intent = Intent(this@WritePostActivity, CheckPostActivity::class.java)
                                        intent.putExtra("postId",response.body().toString())
                                        startActivity(intent)
                                        finish()
                                    }
                                    Log.d(TAG,"onResponse: ${response.raw()}")
                                }
                            })
                        }else{
                            // 게시물 수정
                            val call = service.updatePost(postId, title!!, content!!,picture)
                            call.enqueue(object : Callback<String>{
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d(TAG,"onFailure: ${t.message}")
                                    showToast(t.message.toString(), this@WritePostActivity);
                                }
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    if(response.code()==200){
                                        Log.d(TAG,"onResponse: 성공")
                                        showToast(response.body().toString(), this@WritePostActivity)
                                        val intent = Intent(this@WritePostActivity, CheckPostActivity::class.java)
                                        intent.putExtra("postId",response.body().toString())
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Log.d(TAG,"onResponse: 실패 = ${response.body()}")
                                    }
                                    Log.d(TAG,"onResponse: ${response.raw()}")
                                }
                            })
                        }

                    }else{
                        showToast("제목과 내용을 모두 입력해 주십시오.", this)
                    }
                }
            }
            true
        }
    }

    /*
 * 뒤로가기 버튼으로 네비게이션 닫기
 *
 * 네비게이션 드로어가 열려 있을 때 뒤로가기 버튼을 누르면 네비게이션을 닫고,
 * 닫혀 있다면 기존 뒤로가기 버튼으로 작동한다.
 */
    override fun onBackPressed() {
        if(drawerL_writePost.isDrawerOpen(GravityCompat.START)){
            drawerL_writePost.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}
