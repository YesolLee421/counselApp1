package com.example.counselapp.expInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.counselapp.LogInActivity
import com.example.counselapp.R
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Expert
import com.example.counselapp.myPage.MyPageExpActivity
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_write_expert_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WriteExpertProfileActivity : BaseActivity_noMVP() {

    var TAG = "WriteExpertProfileActivity"

    // retrofitClient, service 객체 생성
    val retrofitClient: Retrofit = RetrofitClient.instance
    lateinit var service: CounselAppService
    lateinit var expert: Expert
    lateinit var expertId: String
    var isFirst: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_expert_profile)

        expertId= intent.getStringExtra("expertId")
        isFirst = intent.getBooleanExtra("isFirst",false)

        // 서비스 시작
        service = retrofitClient.create(CounselAppService::class.java)

        btn_writeExpert_save.setOnClickListener {
//            getLog(TAG,expertId)
//            if(isFirst){
//                val intent = Intent(this@WriteExpertProfileActivity, LogInActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else{
//                getLog(TAG, "false")
//            }

            val name_formal = et_writeExpert_name_formal.text.toString()
            val about = et_writeExpert_about.text.toString()
            val belongTo = et_writeExpert_belongTo.text.toString()
            val career = et_writeExpert_career.text.toString()
            val certificate = et_writeExpert_certificate.text.toString()
            val education = et_writeExpert_education.text.toString()
            val major = et_writeExpert_major.text.toString()
            val call = service.updateExpert(expertId,name_formal, about,belongTo,education,career,certificate, major)

            if(name_formal!=""){
                call.enqueue(object : Callback<String>{
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        getLog(TAG,"onFailure: ${t.message}")
                        showToast(t.message.toString(), this@WriteExpertProfileActivity)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.code()==200){
                            getLog(TAG,"onResponse: 성공")
                            showToast(response.body().toString(), this@WriteExpertProfileActivity)
                            // 여기서 갈라야 함
                            // 회원가입하면서 첫 생성 -> 로그인 이동
                            if(isFirst){
                                val intent = Intent(this@WriteExpertProfileActivity, LogInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this@WriteExpertProfileActivity, MyPageExpActivity::class.java)
                                intent.putExtra("expertId", expertId)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                })
            }else{
                showToast("실명은 반드시 입력해야 합니다.", this)
            }

        }


    }

    override fun onStart() {
        super.onStart()
        val call = service.getExpert(expertId)
        call.enqueue(object : Callback<Expert>{
            override fun onFailure(call: Call<Expert>, t: Throwable) {
                getLog(TAG,"onFailure: ${t.message}")
                showToast(t.message.toString(), this@WriteExpertProfileActivity)
            }

            override fun onResponse(call: Call<Expert>, response: Response<Expert>) {
                if(response.code()==200){
                    expert = response.body()!!
                    et_writeExpert_name_formal.setText(expert.name_formal)
                    et_writeExpert_about.setText(expert.about)
                    et_writeExpert_belongTo.setText(expert.belongTo)
                    et_writeExpert_career.setText(expert.career)
                    et_writeExpert_certificate.setText(expert.certificate)
                    et_writeExpert_education.setText(expert.education)
                    et_writeExpert_major.setText(expert.major)
                }
            }

        })
    }
}
