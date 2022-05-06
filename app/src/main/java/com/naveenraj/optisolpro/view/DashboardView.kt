package com.naveenraj.optisolpro.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.textfield.TextInputEditText
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.FragmentAdapter
import com.naveenraj.optisolpro.utils.RoomAdapter
import com.naveenraj.optisolpro.utils.SQLiteManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardView : AppCompatActivity(),RoomAdapter.ClickListener {
    private lateinit var tabLayout : TabLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initViews()
        tabLayout.addTab(tabLayout.newTab().setText("  Videos  "))
        tabLayout.addTab(tabLayout.newTab().setText("  Feeds  "))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = FragmentAdapter(supportFragmentManager, tabLayout!!.tabCount,this@DashboardView)

        val myviewpager : ViewPager = findViewById(R.id.view_pager)
        myviewpager.adapter = adapter
        myviewpager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                myviewpager.currentItem = tab.position

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
//        tabLayout.post { tabLayout.setupWithViewPager(myviewpager) }
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab)
    }

    override fun clickOperation(data: RoomData) {
        var mBottomSheetDialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
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
            var sqLiteManager = SQLiteManager(this)
            if(sqLiteManager.updateMatchDetails(data)){

            }

        }
    }
}