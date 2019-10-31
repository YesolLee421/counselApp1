package com.example.counselapp.presenter

import android.R
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.example.counselapp.LogInActivity
import com.example.counselapp.model.User
import com.example.counselapp.model.UserList
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.*

class LoginPresenter : LoginContract.Presenter {

    var TAG = "LoginPresenter"
    override lateinit var presenterService: CounselAppService

    override lateinit var presenterCompositeDisposable: CompositeDisposable

    //override var userData: ArrayList<User> = UserList.getUserList()

    @SuppressLint("CheckResult")
    override fun doLogin(id: String, pw: String){

        Log.d(TAG,"service=${presenterService}")
        Log.d(TAG,"composite=${presenterCompositeDisposable}")

        val call = presenterService.loginUser(id,pw)
        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                loginView!!.showToast(t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.code()==200){
                    Log.d(TAG,"onResponse: ${response.body()}")
                    loginView!!.showToast(response.body().toString())
                    loginView!!.moveTo() // 인텐트도 오버라이드
                }
            }
        })

//        presenterService.loginUser(id,pw)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                msg = it.name
//            },{
//                Log.d(TAG,"ERROR message : ${it.message}")
//            })


//        presenterCompositeDisposable.add(presenterService.loginUser(id,pw)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                msg = it
//            }, {
//                Log.d(TAG,"ERROR message : ${it.message}")
//            })
//        )

//        presenterCompositeDisposable.add(presenterService.loginUser(id,pw)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe( { message ->
//                if(message.contains("name")){
//                    msg = "로그인 성공"
//                    Log.d(TAG,"로그인 성공: "+ message.toString())
//                }else{
//                    msg = message
//                    Log.d(TAG,"CUSTOM ERROR:"+ message.toString())
//                }
//            }, { throwable -> msg = throwable.localizedMessage; Log.d(TAG,"SERVER ERROR:"+ msg) } )
//        )

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