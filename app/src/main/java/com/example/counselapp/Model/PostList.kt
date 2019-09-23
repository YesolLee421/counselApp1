package com.example.counselapp.Model

import android.content.Context

object PostList {

    val postData : List<Post> = listOf<Post>(
        Post(1,"솔이","사소한 일로 친구랑 싸웠어요.","#대인관계#친구"),
        Post(2,"망고","난 왜 이렇게 일을 못하는걸까? 어떻게 하면 일을 잘 할 수 있을지 모르겠다..","#직장생활#자존감"),
        Post(3,"라트","취준만 2년째, 너무 지쳤다.","#취업#진로#자존감"))

    fun getPostList(context: Context, size: Int) : ArrayList<Post> {
        val list = ArrayList<Post>()
        for(index in 0..size){
            list.add(Post(postData[index].idx, postData[index].nickname, postData[index].title, postData[index].keywords))
        }
        return list
    }


}