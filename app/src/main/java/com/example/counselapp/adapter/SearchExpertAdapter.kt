package com.example.counselapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.User

class SearchExpertAdapter(val context: Context): RecyclerView.Adapter<SearchExpViewHolder>(),MainAdapterContract.View, SearchExpertAdapterContract.Model {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchExpViewHolder = SearchExpViewHolder(context,parent,onClickFunc)

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: SearchExpViewHolder, position: Int) {
        userList[position].let { holder.onBind(it,position) }
    }

    override fun notifyAdapter() = notifyDataSetChanged()

    override var onClickFunc: ((Int) -> Unit)? = null


    override fun addItems(userList: ArrayList<User>){
        this.userList = userList
    }

    override fun clearItems() = userList.clear()

    private lateinit var userList: ArrayList<User>
    override fun getItem(position: Int): User  = userList[position]


}