package com.example.counselapp.adapter

import com.example.counselapp.model.Post

interface MainAdapterContract {

    interface View{
        fun notifyAdapter()
        var onClickFunc : ((Int)->Unit)?
        // onClick따로 만들지 않고 function 넘겨주기: 변수에 바로 사용할 함수에 대한 정보 담기
        // Int 받아서 return 값 없는 함수
    }
    interface Model{
        fun addItems(postList: ArrayList<Post>)
        fun clearItems()
        fun getItem(position: Int): Post
    }
}