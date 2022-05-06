package com.naveenraj.optisolpro.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import soup.neumorphism.NeumorphCardView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoomAdapter(data: ArrayList<RoomData>, context: Context,listener: ClickListener) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {
    private var data = ArrayList<RoomData>()
    private val listener: ClickListener
    init {
        this.data=data
        this.listener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_details, parent, false))
    }

    override fun onBindViewHolder(holder: RoomAdapter.ViewHolder, position: Int) {
        holder.name.text=data.get(position).name
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val formate = SimpleDateFormat("MMM, dd HH:mm", Locale.ENGLISH)
        df.setTimeZone(TimeZone.getTimeZone("UTC"))
        val date: Date = df.parse(data.get(position).createDataTime)
        df.setTimeZone(TimeZone.getDefault())
        val formattedDate: String = formate.format(date)
        holder.date.text=formattedDate
        if(data.get(position).isLive)
        holder.indicator.visibility=View.VISIBLE

        holder.rootView.setOnClickListener{
            listener.clickOperation(data.get(position))
        }
        if((position+1)%2==0)
            holder.img.setImageResource(R.drawable.room_pic2)
        else
            holder.img.setImageResource(R.drawable.room_pic)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var date: TextView = itemView.findViewById(R.id.date)
        var indicator: TextView = itemView.findViewById(R.id.indicator)
        var img: ImageView = itemView.findViewById(R.id.img)
        var rootView: NeumorphCardView = itemView.findViewById(R.id.rootView)

    }

    interface ClickListener{
        fun clickOperation(data:RoomData)
    }

}
