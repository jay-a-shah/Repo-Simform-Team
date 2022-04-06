package com.example.simformcafeteria.OTPScreen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simformcafeteria.MainActivity
import com.example.simformcafeteria.R
import com.example.simformcafeteria.SignUpActivity
import com.example.simformcafeteria.Utils.PHONENUMBER_KEY_INTENT
import com.example.simformcafeteria.Utils.STOREDVERFICATIONID_KEY_INTENT
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_otp.btnVerify
import kotlinx.android.synthetic.main.activity_otp.pinViewOtp

class OTPActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        storedVerificationId = intent.getStringExtra(STOREDVERFICATIONID_KEY_INTENT)
        btnVerify.setOnClickListener {
            if (TextUtils.isEmpty(pinViewOtp.text.toString())) {
                Toast.makeText(this, getString(R.string.toast_wrong_otp), Toast.LENGTH_SHORT).show()
            } else {
                storedVerificationId?.let { it -> verifyPhoneNumberWithCode(it, pinViewOtp.text.toString())
                }
            }
        }

    }

    private fun verifyPhoneNumberWithCode(verficationCode: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verficationCode, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                if (task.result.additionalUserInfo?.isNewUser == true) {
                    val intent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //To be Implemented Later
                }
            }

        }
    }


}