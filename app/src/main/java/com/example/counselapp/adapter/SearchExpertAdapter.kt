package com.example.counselapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.counselapp.model.Expert
import com.example.counselapp.model.User

class SearchExpertAdapter(val context: Context): RecyclerView.Adapter<SearchExpViewHolder>(),MainAdapterContract.View, SearchExpertAdapterContract.Model {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchExpViewHolder = SearchExpViewHolder(context,parent,onClickFunc)

    override fun getItemCount(): Int = if(expertList!=null) expertList!!.size else 0


    override fun onBindViewHolder(holder: SearchExpViewHolder, position: Int) {
        expertList?.get(position).let {
            if(it!=null){
                holder.onBind(it,position)
            }
        }
    }

    override fun notifyAdapter() = notifyDataSetChanged()

    override var onClickFunc: ((Int) -> Unit)? = null

    override fun addItems(expertList: List<Expert>){
        this.expertList = expertList
    }

    override fun clearItems(){}

    private var expertList: List<Expert>? = null
    override fun getItem(position: Int): Expert  = expertList?.get(position)!!


}