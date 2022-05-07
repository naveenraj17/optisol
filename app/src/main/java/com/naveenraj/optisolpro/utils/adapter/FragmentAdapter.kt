package com.naveenraj.optisolpro.utils.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naveenraj.optisolpro.view.fragments.FeedsFragment
import com.naveenraj.optisolpro.view.fragments.VideoFragment

class FragmentAdapter (fm: FragmentManager, private var totalTabs: Int,
                       private var listener: RoomAdapter.ClickListener
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()

            1 -> FeedsFragment(listener)

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}