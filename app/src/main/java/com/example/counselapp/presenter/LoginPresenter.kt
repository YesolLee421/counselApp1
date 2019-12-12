package com.example.counselapp.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient2
import com.example.counselapp.model.User
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.*

class LoginPresenter : LoginContract.Presenter {
    internal var preferences: SharedPreferences? = null

    var TAG = "LoginPresenter"
    override lateinit var presenterService: CounselAppService
    override lateinit var presenterCompositeDisposable: CompositeDisposable
    lateinit var mContext: Context

    @SuppressLint("CheckResult")
    override fun doLogin(id: String, pw: String){
        val client: OkHttpClient = RetrofitClient2.getClient(mContext,"receiveCookies")
        presenterService = RetrofitClient2.serviceAPI(client)

        Log.d(TAG,"service=${presenterService}")

        val call = presenterService.loginUser(id,pw)
        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message}")
                loginView!!.showToast(t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.code()==200){
                    val arr = response.headers().get("Set-Cookie")!!.split(";")
                    val arr2 = arr[0].toString().split("=")
                    Log.e("getme", arr2[1])
                    getMe(arr2[1])
                    Log.d(TAG,"onResponse: ${response.body()}")
                    loginView!!.showToast(response.body().toString())
                    loginView!!.moveTo() // 인텐트도 오버라이드
                } else{
                    Log.e(TAG, "StatusCode : ${response.code()}")
                    loginView!!.showToast(response.raw().toString())
                }
                Log.e(TAG, "response=" + response.raw())
            }
        })
    }

    private fun getMe(mcookie: String) {
        val client: OkHttpClient = RetrofitClient2.getClient(mContext,"addCookies")
        presenterService = RetrofitClient2.serviceAPI(client)
        val call = presenterService.getUser()
        call.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code()==200){
                    Log.d("getUser_response",response.headers().toString())
                    loginView!!.showToast(response.body().toString())
                    //loginView!!.moveTo() // 인텐트도 오버라이드
                } else{
                    Log.e(TAG, "StatusCode : ${response.code()}")
                }
                Log.e(TAG, "response=" + response.raw())
            }

        })

    }

    // Presenter와 일대일 연결될 뷰 선언
    private var loginView : LoginContract.View? = null

    override fun takeView(view: LoginContract.View) {
        loginView = view
    }

    override fun dropView() {
        loginView = null
    }
}