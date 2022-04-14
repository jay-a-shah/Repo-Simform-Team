package com.example.simformcafeteria.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simformcafeteria.DashboardActivity
import com.example.simformcafeteria.Model.Departments
import com.example.simformcafeteria.Model.User
import com.example.simformcafeteria.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignupViewModel(application: Application): AndroidViewModel(application) {
    var email: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var mobileNo: MutableLiveData<String> = MutableLiveData()
    var emptypetrainee: MutableLiveData<Boolean> = MutableLiveData()
    var emptypeemp: MutableLiveData<Boolean> = MutableLiveData()
    var empCode: MutableLiveData<String> = MutableLiveData()
    var departmentList = MutableLiveData<ArrayList<Departments>>()
    var department: MutableLiveData<String> = MutableLiveData()
    private val logInResult = MutableLiveData<String>()
    fun getLogInResult(): LiveData<String> = logInResult
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.email_empty)
            return
        } else if (mobileNo.value.isNullOrEmpty()) {
            logInResult.value =getApplication<Application>().resources.getString(R.string.mobile_empty)
            return
        } else if (name.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.name_empty)
            return
        } else if (emptypeemp.value == true && empCode.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.employee_code_empty)
            return
        } else if (department.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.enter_department)
        } else {
            firebaseSignup()
        }
    }

     private fun firebaseSignup() {
        var user: User? = if (emptypeemp.value == true) {
            FirebaseAuth.getInstance().currentUser?.let {
                User(it.uid, name.value, email.value, mobileNo.value, department.value, true, empCode.value, false)
            }
        } else {
            FirebaseAuth.getInstance().currentUser?.let {
                User(it.uid, name.value, email.value, mobileNo.value, department.value, false, "", false)
            }
        }

        FirebaseAuth.getInstance().currentUser?.let {
            database.child(getApplication<Application>().resources.getString(R.string.user_node)).child(it.uid).setValue(user)
                .addOnSuccessListener {
                    logInResult.value = getApplication<Application>().resources.getString(R.string.successfully_registred)
                    return@addOnSuccessListener
                }
                .addOnFailureListener {
                    logInResult.value = getApplication<Application>().resources.getString(R.string.registration_failed)
                    return@addOnFailureListener
                }
        }
    }

    fun firebaseLoadDepartment() {
        database.child(getApplication<Application>().resources.getString(R.string.department_node)).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempdepartlistarray = ArrayList<Departments>()
                for (departmentSnapshot in dataSnapshot.children) {
                    var dept = Departments(
                        departmentSnapshot.key?.toInt() ?: 1,
                        departmentSnapshot.value.toString()
                    );
                    tempdepartlistarray.add(dept)
                }
                departmentList.value = tempdepartlistarray
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}