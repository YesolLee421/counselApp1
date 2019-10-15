package com.example.counselapp.presenter

import android.R
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

    // 로그인하기
    //val userList = UserList.getUserList()
    // login성공, 비밀번호 틀림, 회원정보 없음
    var loginSuccess = 100;
    var loginfailPw = 200;
    var loginfailId = 300;

    // 인터페이스 선언
    lateinit var service: CounselAppService
    var compositeDisposable = CompositeDisposable()

    override var userData: ArrayList<User> = UserList.getUserList()

    override fun doLogin(id: String, pw: String, service: CounselAppService, compositeDisposable: CompositeDisposable): String {

        var msg: String? = null

        compositeDisposable.add(service.loginUser(id,pw)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { message ->
                if(message.contains("name")){
                    msg = "로그인 성공"
                    Log.d(TAG,"로그인 성공: "+ message.toString())
                }else{
                    msg = message
                    Log.d(TAG,"CUSTOM ERROR:"+ message.toString())
                }
            }, { throwable -> msg = throwable.localizedMessage; Log.d(TAG,"SERVER ERROR:"+ msg) } )
        )
        return msg.toString()

    }


    // Presenter와 일대일 연결될 뷰 선언
    private var loginView : LoginContract.View? = null

    override fun getUserList() {
        // 로딩 시작 -> 모델에서 DogList 전달받기 ->View에 전달 ->로딩 완료
        //loginView?.showLoading() // 아마 변수가 null 아닐 때 함수 작동할듯

        // 입력된 아이디, 비밀번호 비교해야되는데?
        loginView?.checkUserList(userData) // 정보를 뷰에 전달

    }

    override fun takeView(view: LoginContract.View) {
        loginView = view
    }

    override fun dropView() {
        loginView = null
    }
}