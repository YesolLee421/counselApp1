package com.example.counselapp.adapter

import android.content.Context

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.Post
import com.example.counselapp.listener.onItemClickListener

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainViewHolder>(), MainAdapterContract.Model, MainAdapterContract.View {
    override fun getItem(position: Int): Post = postList?.get(position)!!

    override var onClickFunc: ((Int) -> Unit)? = null

    private var postList: List<Post>? = null
    //var onItemClickListener: onItemClickListener? = null

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        postList?.get(position).let {
            if (it != null) {
                holder.onBind(it,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(context,parent,onClickFunc)


    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(postList: List<Post>) {
        // 여기서 받아오기?
        this.postList = postList
    }
    override fun getItemCount(): Int = if(postList!=null) postList!!.size else 0

    override fun clearItems() {}

}