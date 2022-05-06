package com.naveenraj.optisolpro.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.VideoData
import com.naveenraj.optisolpro.model.VideoResponse
import okhttp3.internal.notify
import soup.neumorphism.NeumorphCardView

class VideoAdapter() :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>()  {
    private var data = ArrayList<VideoData>()
//    init {
//        this.data=data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_details, parent, false))

    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(items:ArrayList<VideoData>){
        data.addAll(items)
        notifyDataSetChanged()
    }
    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var date: TextView = itemView.findViewById(R.id.date)
        var rootView: NeumorphCardView = itemView.findViewById(R.id.rootView)

    }


}