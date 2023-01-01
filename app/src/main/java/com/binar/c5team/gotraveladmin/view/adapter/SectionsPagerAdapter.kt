package com.binar.c5team.gotraveladmin.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.binar.c5team.gotraveladmin.view.BookingFragment
import com.binar.c5team.gotraveladmin.view.BookingRoundTripFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = BookingFragment()
            1 -> fragment = BookingRoundTripFragment()
        }
        return fragment as Fragment
    }

}