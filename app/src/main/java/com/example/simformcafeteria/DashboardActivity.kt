package com.example.simformcafeteria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.simformcafeteria.fragments.AvailableFragment
import com.example.simformcafeteria.fragments.HomeFragment
import com.example.simformcafeteria.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_dashboard.bottomNavigation

class DashboardActivity : AppCompatActivity() {
    var homeFragment = HomeFragment()
    var availableFragment = AvailableFragment()
    var profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setBottomNavigation()
    }

    fun setBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.userHome -> swapFragments(homeFragment)
                R.id.userAvailable -> swapFragments(availableFragment)
                R.id.userProfile -> swapFragments(profileFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}

