package com.example.counselapp.presenter

import com.example.counselapp.model.User
import com.example.counselapp.model.UserList

class LoginPresenter : LoginContract.Presenter {

    override fun doLogin(id: String, pw: String): Int {
        // 로그인하기
        val userList = UserList.getUserList()
        // login성공, 비밀번호 틀림, 회원정보 없음
        var loginSuccess = 100;
        var loginfailPw = 200;
        var loginfailId = 300;
        for (i in userList.indices){ //유저리스트 검사
            if (userList[i].id==id){
                if(userList[i].pw==pw){ //아이디 동일&비밀번호 일치
                    return loginSuccess
                }else{ // 아이디 있으나 비밀번호 불일치
                    return loginfailPw;
                }
            }
        } //반복문 종료
        return loginfailId
    }

    // Presenter와 일대일 연결될 뷰 선언
    private var loginView : LoginContract.View? = null
    var user : User? = null

    // 새로운 유저 생성?
    private fun initUser(id: String, pw: String){
        user = User(0,id,pw)
    }


    override fun getUserList() {
        // 로딩 시작 -> 모델에서 DogList 전달받기 ->View에 전달 ->로딩 완료
        //loginView?.showLoading() // 아마 변수가 null 아닐 때 함수 작동할듯
        val userList = UserList.getUserList()
        // 입력된 아이디, 비밀번호 비교해야되는데?
        loginView?.checkUserList(userList) // 정보를 뷰에 전달

    }

    override fun takeView(view: LoginContract.View) {
        loginView = view
    }

    override fun dropView() {
        loginView = null
    }
}