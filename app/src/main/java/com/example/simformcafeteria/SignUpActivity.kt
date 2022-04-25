package com.example.simformcafeteria

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.simformcafeteria.model.Departments
import com.example.simformcafeteria.Utils.ONE
import com.example.simformcafeteria.adapters.departmentAdapter
import com.example.simformcafeteria.databinding.ActivitySignUpBinding
import com.example.simformcafeteria.viewModel.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.btnSignUp
import kotlinx.android.synthetic.main.activity_sign_up.editTextEmployeeMaterialLayout
import kotlinx.android.synthetic.main.activity_sign_up.radioButtonEmployee

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.apply {
            editTextMobileNo.setText(FirebaseAuth.getInstance().currentUser?.phoneNumber.toString())
            radioGroup.setOnCheckedChangeListener { _, p1 ->
                if (p1 == R.id.radioButtonEmployee) {
                    editTextEmployeeMaterialLayout.visibility = View.VISIBLE
                } else {
                    editTextEmployeeMaterialLayout.visibility = View.GONE
                }
            }
            signup = signupViewModel
            lifecycleOwner = lifecycleOwner
        }
        signupViewModel.firebaseLoadDepartment()
        val departmentAdapter = departmentAdapter(
            this, R.layout.autocompleteitem, signupViewModel.departmentList.value ?: arrayListOf(
                Departments(ONE, getString(R.string.department_name_mobile))
            )
        )
        binding.autoCompleteTextView.setAdapter(departmentAdapter)
        signupViewModel.departmentList.observe(this, Observer {
            if (it != null) {
                departmentAdapter.setListData(it)
                departmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, getString(R.string.department_data_not_updated), Toast.LENGTH_SHORT).show()
            }
        })
        signupViewModel.getLogInResult().observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }

        if (radioButtonEmployee.isChecked) {
            editTextEmployeeMaterialLayout.visibility = View.VISIBLE
        }
        btnSignUp.setOnClickListener {
            signupViewModel.performValidation()
        }
    }
}