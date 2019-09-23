package com.example.counselapp.adapter

import android.content.Context

import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.Model.Post
import com.example.counselapp.listener.onItemClickListener

class mainAdapter(val context: Context) : RecyclerView.Adapter<mainViewHolder>(), mainAdapterContract.Model, mainAdapterContract.View {
    private lateinit var postList: ArrayList<Post>
    var onItemClickListener: onItemClickListener? = null

    override fun onBindViewHolder(holder: mainViewHolder, position: Int) {
        postList[position].let {
            holder.onBind(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = mainViewHolder(context,parent,
        // !! 강제로 not null 처리
        this.onItemClickListener!!
    )
    override fun getItemCount(): Int = postList.size

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(postList: ArrayList<Post>) {
        this.postList = postList
    }

    override fun clearItems() {
        postList.clear()
    }

}