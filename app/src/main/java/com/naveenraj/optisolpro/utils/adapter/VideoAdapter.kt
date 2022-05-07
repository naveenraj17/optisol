package com.naveenraj.optisolpro.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.VideoData
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class VideoAdapter() :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>()  {
    private var data = ArrayList<VideoData>()

//    init {
//        this.data=entireData
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.data[position]
        holder.name.text = data.firstName+" "+data.lastName
        holder.mail.text = data.email
        Picasso.get().load(data.avatar).placeholder(R.drawable.no_image).fit()
            .centerInside().into(holder.img)
    }

    fun addItem(items:ArrayList<VideoData>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var img: CircleImageView = itemView.findViewById(R.id.img)
        var mail: TextView = itemView.findViewById(R.id.mail)

    }
}