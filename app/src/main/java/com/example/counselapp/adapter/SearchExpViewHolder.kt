package com.example.counselapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.R
import com.example.counselapp.model.User

class SearchExpViewHolder(val context: Context, parent: ViewGroup?, val onClicFunc : ((Int)->Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.item_search_expert,parent,false)) {

    private val imgProfile by lazy {
        itemView.findViewById(R.id.item_searchExp_img) as ImageView
    }
    private val textName by lazy {
        itemView.findViewById(R.id.item_searchExp_name) as TextView
    }
    private val imgHeart by lazy {
        itemView.findViewById(R.id.item_searchExp_heart) as ImageView
    }
    private val textDescription by lazy {
        itemView.findViewById(R.id.item_searchExp_description) as TextView
    }
    private val textBelongTo by lazy {
        itemView.findViewById(R.id.item_searchExp_belongTo) as TextView
    }
    fun onBind(item: User, position: Int){
        textName.text = item.name
        textDescription.text = item.description
        textBelongTo.text = item.belongTo

        itemView.setOnClickListener { onClicFunc?.invoke(position) }
    }
}