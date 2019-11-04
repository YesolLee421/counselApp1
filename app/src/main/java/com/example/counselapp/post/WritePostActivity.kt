package com.example.counselapp.post

import android.content.Intent

import android.os.Bundle
import android.text.Editable
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.counselapp.MainBoardActivity
import com.example.counselapp.R
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Post
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_mainboard.*
import kotlinx.android.synthetic.main.activity_write_post.*
import kotlinx.android.synthetic.main.activity_write_post.ic_toolbar_menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WritePostActivity : BaseActivity_noMVP() {

    var TAG = "WritePostActivity"

    // retrofitClient, service 객체 생성
    val retrofitClient: Retrofit = RetrofitClient.instance
    lateinit var service: CounselAppService
    lateinit var post: Post



    // String -> editable로 바꾸는 함수
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        // 인텐트 있으면 (수정 시)
        val _id: String? = intent.getStringExtra("postId")
        val title: String? = intent.getStringExtra("title")
        val content: String? = intent.getStringExtra("content");

        et_writePost_title.setText(intent.getStringExtra("title"))
        et_writePost_content.setText(intent.getStringExtra("content"))

        // 서비스 시작
        service = retrofitClient.create(CounselAppService::class.java)


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
                R.id.bottom_bar_writePost_save -> {
                    val title = et_writePost_title.text.toString()
                    val content = et_writePost_content.text.toString()
                    val commenter = "Homer Simpson"
                    if(title!="" && content!=""){
                        if(_id==null){ // _id ==null 이면 새로 생성, 아니면 수정
                            val call = service.writePost(commenter, title, content)
                            call.enqueue(object : Callback<String>{
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d(TAG,"onFailure: ${t.message}")
                                    showToast(t.message.toString(), this@WritePostActivity);
                                }
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    if(response.code()==201){
                                        Log.d(TAG,"onResponse: 성공")
                                        showToast(response.body().toString(), this@WritePostActivity)
                                        val intent = Intent(this@WritePostActivity, CheckPostActivity::class.java)
                                        intent.putExtra("postId",response.body().toString())
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            })
                        }else{
                            // 게시물 수정
                            val call = service.updatePost(_id!!,title,content)
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
                                    }
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
