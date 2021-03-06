package com.naveenraj.optisolpro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.adapter.FragmentAdapter
import com.naveenraj.optisolpro.utils.adapter.RoomAdapter
import com.naveenraj.optisolpro.utils.SQLiteManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardView : AppCompatActivity(), RoomAdapter.ClickListener {
    private lateinit var tabLayout : TabLayout

    lateinit var viewPager : ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initViews()
        tabLayout.addTab(tabLayout.newTab().setText("   Videos   "))
        tabLayout.addTab(tabLayout.newTab().setText("   Feeds   "))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = FragmentAdapter(supportFragmentManager, tabLayout.tabCount,this@DashboardView)

        viewPager= findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        var position = 0
        try {
            position = intent.getStringExtra("item")?.toInt()!!
        } catch (e: Exception) {
        }

        viewPager.currentItem = position

    }

     private fun currentPage() {
         startActivity(Intent(this,DashboardView::class.java)
             .putExtra("item","1"))
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab)
    }

    override fun clickOperation(data: RoomData) {
        val mBottomSheetDialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val sheetView = this.layoutInflater.inflate(R.layout.create_room, null)
        mBottomSheetDialog.setContentView(sheetView)
        val title: TextView = sheetView.findViewById(R.id.title)
        val action: Button = sheetView.findViewById(R.id.action_btn)
        val createL: LinearLayout = sheetView.findViewById(R.id.createL)
        val updateL: LinearLayout = sheetView.findViewById(R.id.updateL)
        updateL.visibility = View.VISIBLE
        createL.visibility = View.GONE
        mBottomSheetDialog.show()
        when{
            data.isLive ->{
                title.text = "Are You Sure that want to End the Live ?"
                action.text = "End Live"
            }
            !data.isLive ->{
                title.text = "Are You Sure that want to Start a Live ?"
                action.text = "Start Live"
            }
        }
        action.setOnClickListener {
            val sqLiteManager = SQLiteManager(this)
            if(sqLiteManager.updateMatchDetails(data)){
                currentPage()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}