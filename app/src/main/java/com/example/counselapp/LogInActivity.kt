package com.example.counselapp

import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import com.example.counselapp.base.BaseActivity
import com.example.counselapp.model.User
import com.example.counselapp.presenter.LoginContract
import com.example.counselapp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : BaseActivity(), LoginContract.View {
    // 일대일 연결할 프레젠터 선언
    private lateinit var loginPresenter: LoginPresenter

    var TAG = "LogInActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter()

        //프레젠터와 뷰 연결
        loginPresenter.takeView(this)

        btn_logIn.setOnClickListener {
            val id = et_login_id.text.toString()
            val pw = et_login_pw.text.toString()
            getLog(TAG, "id=$id / pw=$pw")

            val login = loginPresenter.doLogin(id,pw)
            getLog(TAG, login.toString())
            when(login){
                100 -> {
                    val loginIntent = Intent(this, MainBoardActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }
                200 -> Toast.makeText(this,"비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show()
                300 -> Toast.makeText(this,"회원정보가 없습니다",Toast.LENGTH_SHORT).show()
            }

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
