package com.example.simformcafeteria.adapters

import com.example.simformcafeteria.fragments.AvailableFragment
import com.example.simformcafeteria.fragments.HomeFragment
import com.example.simformcafeteria.fragments.ProfileFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.simformcafeteria.Utils.ONE
import com.example.simformcafeteria.Utils.THREE
import com.example.simformcafeteria.Utils.TWO
import com.example.simformcafeteria.Utils.ZERO

class DashboardAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return THREE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ZERO -> {
                HomeFragment()
            }
            ONE -> {
                AvailableFragment()
            }
            TWO -> {
                ProfileFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }
}