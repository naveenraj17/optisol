package com.naveenraj.optisolpro.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import soup.neumorphism.NeumorphCardView
import java.text.SimpleDateFormat
import java.util.*

class RoomAdapter(data: ArrayList<RoomData>,listener: ClickListener) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private var data = ArrayList<RoomData>()
    private val listener: ClickListener

    init {
        this.data=data
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_details, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =this.data[position]
        holder.name.text=data.name
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val format = SimpleDateFormat("MMM, dd HH:mm", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = df.parse(data.createDataTime)
        df.timeZone = TimeZone.getDefault()
        val formattedDate: String = format.format(date!!)
        holder.date.text=formattedDate

        if(data.isLive)
            blink(holder.indicator)

        holder.rootView.setOnClickListener{
            listener.clickOperation(data)
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

    private fun blink(tvBlinkText: TextView) {
        tvBlinkText.visibility=View.VISIBLE
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.startOffset = 100
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        tvBlinkText.startAnimation(anim)
    }
}
