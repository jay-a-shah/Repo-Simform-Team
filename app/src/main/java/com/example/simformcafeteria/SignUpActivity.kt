package com.example.simformcafeteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_sign_up.editTextEmployeeMaterialLayout
import kotlinx.android.synthetic.main.activity_sign_up.radioButtonEmployee


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        if(radioButtonEmployee.isSelected){
            editTextEmployeeMaterialLayout.visibility = View.VISIBLE
        }
    }
}