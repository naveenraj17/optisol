package com.naveenraj.optisolpro.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.VideoData
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import soup.neumorphism.NeumorphCardView

class VideoAdapter(entireData: ArrayList<VideoData>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>()  {
    private var data = ArrayList<VideoData>()
    init {
        this.data=entireData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_layout, parent, false))

    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {

        holder.name.text = data.get(position).firstName+" "+data.get(position).lastName
        holder.mail.text = data.get(position).email
        Picasso.get().load(data.get(position).avatar).placeholder(R.drawable.profile).fit()
            .centerInside().into(holder.img)
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    fun addData(items:ArrayList<VideoData>){
//        data.addAll(items)
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var img: CircleImageView = itemView.findViewById(R.id.img)
        var mail: TextView = itemView.findViewById(R.id.mail)

    }


}