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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_otp.btnVerify
import kotlinx.android.synthetic.main.activity_otp.pinViewOtp
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        init()
        firebasePhoneAuth(intent.getStringExtra(PHONENUMBER_KEY_INTENT))
        btnVerify.setOnClickListener {
            if (TextUtils.isEmpty(pinViewOtp.text.toString())) {
                Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show()
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

    private fun firebasePhoneAuth(phone: String?) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber("+91" + phone)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun init() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OTPActivity, getString(R.string.phone_verfication_failed), Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }
}