package com.example.counselapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.Post
import com.example.counselapp.R

class MainViewHolder(val context: Context, parent: ViewGroup?, val onClickFunc : ((Int)->Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.item_mainboard,parent,false)) {


    // 원래 text_title : TextView?=null 한 후 값 넣거나 lateinit 등도 가능
    // by lazy{} 는 해당 속성을 처음 사용하는 지점에서 초기화 과정 실행함
    private val text_title by lazy {
        itemView.findViewById(R.id.item_mainboard_title) as TextView
    }
    private val text_commenter by lazy {
        itemView.findViewById(R.id.item_mainboard_commenter) as TextView
    }
    private val text_content by lazy {
        itemView.findViewById(R.id.item_mainboard_content) as TextView
    }

    fun onBind(item: Post, position: Int){
        text_title.text = item.title
        text_commenter.text = item.commenter
        text_content.text = item.content

        itemView.setOnClickListener {
            onClickFunc?.invoke(position)
        }
    }






}


