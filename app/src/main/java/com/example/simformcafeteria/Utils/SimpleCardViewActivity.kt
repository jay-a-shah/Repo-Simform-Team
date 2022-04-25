package com.example.simformcafeteria.Utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simformcafeteria.R
import com.example.simformcafeteria.fragments.HomeFragment

class SimpleCardViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_card_view)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment()).commit()
    }
}