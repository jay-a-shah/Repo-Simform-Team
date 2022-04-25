package com.example.simformcafeteria.model

class User(
    val uid: String,
    val name: String?,
    val email: String?,
    val mobileNo: String?,
    val department: String?,
    val employeeType: Boolean,
    val empCode: String?,
    val admin: Boolean
) {
}