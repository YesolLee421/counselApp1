package com.example.counselapp

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignInActivity : BaseActivity_noMVP() {

    var TAG = "SignInActivity"

    // 서비스 선언
    val retrofitClient: Retrofit = RetrofitClient.instance
    val service = retrofitClient.create(CounselAppService::class.java)

    fun goLogIn(){
        val logInIntent = Intent(this, LogInActivity::class.java)
        startActivity(logInIntent)
        finish()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btn_signIn.setOnClickListener {

            if(et_signIn_putNickName.text.isEmpty()){
                showToast("이름을 입력해 주십시오", this)
                return@setOnClickListener
            }
            if(et_signIn_putEmail.text.isEmpty()){
                showToast("이메일을 입력해 주십시오", this)
                return@setOnClickListener
            }
            if(et_signIn_putPw.text.isEmpty()){
                showToast("비밀번호를 입력해 주십시오", this)
                return@setOnClickListener
            }
            if(et_signIn_checkPw.text.isEmpty()|| et_signIn_putPw.text.toString()!=et_signIn_checkPw.text.toString()) {
                showToast("비밀번호를 다시 확인해 주십시오", this)
                return@setOnClickListener
            }

            if(!radioButton_signIn_normal.isChecked && !radioButton_signIn_expert.isChecked){
                showToast("사용자 종류를 선택해 주십시오", this)
                return@setOnClickListener
            }

            val email = et_signIn_putEmail.text.toString() // = id
            val password = et_signIn_checkPw.text.toString() // = pw
            val nickname = et_signIn_putNickName.text.toString() // = name
            val type = if (radioButton_signIn_normal.isChecked) 1 else 2 //= type
            // type: 1 = 일반사용자, 2 = 전문상담가

            registerUser(email,password,nickname, type)
        }
    }

    fun registerUser(email: String, password:String, nickname: String, type: Int){

        val call = service.registerUser(email,password,nickname,type)

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                showToast(t.message!!,this@SignInActivity)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG,"onResponse: ${response.body()}")
                showToast(response.body()!!,this@SignInActivity)
                if(response.code()==201){ // 새로운 유저 객체 생성 시만 이동
                    goLogIn()
                }
            }
        })

    }
}
