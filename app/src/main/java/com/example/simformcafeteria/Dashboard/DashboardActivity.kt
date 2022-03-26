package com.example.simformcafeteria.Dashboard

import Adapter.DashboardAdapter
import Fragments.AvailableFragment
import Fragments.HomeFragment
import Fragments.ProfileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.simformcafeteria.R
import com.example.simformcafeteria.Utils.ONE
import com.example.simformcafeteria.Utils.TWO
import com.example.simformcafeteria.Utils.ZERO
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val availableFragment = AvailableFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        viewpagerTwo.adapter = DashboardAdapter(this.supportFragmentManager, lifecycle)
        replaceFragment(homeFragment)
        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.userHome -> replaceFragment(homeFragment)
                R.id.userAvailable -> replaceFragment(availableFragment)
                R.id.userProfile -> replaceFragment(profileFragment)
            }
            true
        }

        viewpagerTwo.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    ZERO -> bottomNavigation.selectedItemId = R.id.userHome
                    ONE -> bottomNavigation.selectedItemId = R.id.userAvailable
                    TWO -> bottomNavigation.selectedItemId = R.id.userProfile
                }
            }
        })
    }
    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,fragment)
            transaction.commit()
        }

    }
}