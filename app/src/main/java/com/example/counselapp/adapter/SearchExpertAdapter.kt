package com.example.counselapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.User

class SearchExpertAdapter(val context: Context): RecyclerView.Adapter<SearchExpViewHolder>(),MainAdapterContract.View, SearchExpertAdapterContract.Model {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchExpViewHolder = SearchExpViewHolder(context,parent,onClickFunc)

    override fun getItemCount(): Int = if(userList!=null) userList!!.size else 0


    override fun onBindViewHolder(holder: SearchExpViewHolder, position: Int) {
        userList?.get(position).let {
            if(it!=null){
                holder.onBind(it,position)
            }
        }
    }

    override fun notifyAdapter() = notifyDataSetChanged()

    override var onClickFunc: ((Int) -> Unit)? = null

    override fun addItems(userList: List<User>){
        this.userList = userList
    }

    override fun clearItems(){}

    private var userList: List<User>? = null
    override fun getItem(position: Int): User  = userList?.get(position)!!


}