package com.example.counselapp.adapter

import android.content.Context

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.Post
import com.example.counselapp.listener.onItemClickListener

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainViewHolder>(), MainAdapterContract.Model, MainAdapterContract.View {
    override fun getItem(position: Int): Post = postList.get(position)

    override var onClickFunc: ((Int) -> Unit)? = null

    private lateinit var postList: ArrayList<Post>
    var onItemClickListener: onItemClickListener? = null

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        postList[position].let {
            holder.onBind(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(context,parent,onClickFunc)
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