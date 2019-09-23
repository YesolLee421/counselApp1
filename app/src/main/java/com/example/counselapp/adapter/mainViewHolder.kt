package com.example.counselapp.adapter

import android.content.Context

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.Model.Post
import com.example.counselapp.R
import com.example.counselapp.listener.onItemClickListener

class mainViewHolder(val context: Context, parent: ViewGroup?, val onItemClickListener: onItemClickListener) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.item_mainboard,parent,false)) {


    // 원래 text_title : TextView?=null 한 후 값 넣거나 lateinit 등도 가능
    // by lazy{} 는 해당 속성을 처음 사용하는 지점에서 초기화 과정 실행함
    val text_title by lazy {
        itemView.findViewById(R.id.item_mainboard_title) as TextView
    }
    val text_nickname by lazy {
        itemView.findViewById(R.id.item_mainboard_nickname) as TextView
    }
    val text_keywords by lazy {
        itemView.findViewById(R.id.item_mainboard_keywords) as TextView
    }

    fun onBind(item: Post, position: Int){
        text_title.text = item.title
        text_nickname.text = item.nickname
        text_keywords.text = item.keywords

        itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }






}


