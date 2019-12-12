package com.example.counselapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.counselapp.base.BaseActivity
import com.example.counselapp.model.User
import com.example.counselapp.presenter.LoginContract
import com.example.counselapp.presenter.LoginPresenter
import com.example.counselapp.Network.CounselAppService
import com.example.counselapp.Network.RetrofitClient
import com.example.counselapp.Network.RetrofitClient2
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class LogInActivity : BaseActivity(), LoginContract.View {
    override fun moveTo() {
        val intent = Intent(this, MainBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    // 일대일 연결할 프레젠터 선언
    private lateinit var loginPresenter: LoginPresenter

    // 인터페이스 선언
    lateinit var service: CounselAppService
    var compositeDisposable = CompositeDisposable()
    var context: Context = this
    lateinit var preference: SharedPreferences


    // retrofitClient 객체 생성
    val retrofitClient: OkHttpClient = RetrofitClient2.getClient(context,"addCookies")
    
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

        initPresenter()
        preference = context.getSharedPreferences("UserSign", Context.MODE_PRIVATE)

        //프레젠터와 뷰 연결
        loginPresenter.takeView(this)
        Log.d(TAG+"sf의 쿠키", preference.getString("Cookie", ""))

        btn_logIn.setOnClickListener {
            val id = et_login_id.text.toString()
            val pw = et_login_pw.text.toString()
            getLog(TAG, "id=$id / pw=$pw")

            loginPresenter.doLogin(id,pw)
        }

        text_logIn_signIn.setOnClickListener {
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }
    }

    // 여기서 데이터 연결?
    override fun initPresenter() {
        // 서비스 시작
        service = RetrofitClient2.serviceAPI(retrofitClient)

        loginPresenter = LoginPresenter().apply {
            presenterService = service
            presenterCompositeDisposable = compositeDisposable
            mContext = context
        }
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
