package com.example.counselapp

import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import com.example.counselapp.base.BaseActivity
import com.example.counselapp.model.User
import com.example.counselapp.presenter.LoginContract
import com.example.counselapp.presenter.LoginPresenter
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Retrofit
import retrofit2.create

class LogInActivity : BaseActivity(), LoginContract.View {
    // 일대일 연결할 프레젠터 선언
    private lateinit var loginPresenter: LoginPresenter

    // 인터페이스 선언
    lateinit var service: CounselAppService
    var compositeDisposable = CompositeDisposable()


    var TAG = "LogInActivity"

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter()

        //프레젠터와 뷰 연결
        loginPresenter.takeView(this)

        // 서비스 시작
        val retrofitClient: Retrofit = RetrofitClient.instance
        service = retrofitClient.create(CounselAppService::class.java)

        btn_logIn.setOnClickListener {
            val id = et_login_id.text.toString()
            val pw = et_login_pw.text.toString()
            getLog(TAG, "id=$id / pw=$pw")

            val login = loginPresenter.doLogin(id,pw,service, compositeDisposable)
            getLog(TAG + "login: ", login)

            Toast.makeText(this,login,Toast.LENGTH_SHORT).show()

//            when(login){
//                "100" -> {
//                    val loginIntent = Intent(this, MainBoardActivity::class.java)
//                    startActivity(loginIntent)
//                    finish()
//                }
//                "200" -> Toast.makeText(this,"비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show()
//                "300" -> Toast.makeText(this,"회원정보가 없습니다",Toast.LENGTH_SHORT).show()
//            }

        }

        text_logIn_signIn.setOnClickListener {
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }
    }

    override fun initPresenter() {
        loginPresenter = LoginPresenter()
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkUserList(userList: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
