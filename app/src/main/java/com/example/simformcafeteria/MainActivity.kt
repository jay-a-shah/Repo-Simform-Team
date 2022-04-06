package com.example.simformcafeteria

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simformcafeteria.OTPScreen.OTPActivity
import com.example.simformcafeteria.Utils.INDIA_COUNTRY_CODE
import com.example.simformcafeteria.Utils.PHONENUMBER_KEY_INTENT
import com.example.simformcafeteria.Utils.STOREDVERFICATIONID_KEY_INTENT
import com.example.simformcafeteria.Utils.TAG_MICROSOFT
import com.example.simformcafeteria.Utils.TEN
import com.example.simformcafeteria.Utils.TIMEOUT_SIXTY
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.btnSignIn
import kotlinx.android.synthetic.main.activity_main.btnSignInMicrosoft
import kotlinx.android.synthetic.main.activity_main.editTextMobileForLogin
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    var  provider = OAuthProvider.newBuilder("microsoft.com")
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    companion object{
        var TAG = TAG_MICROSOFT
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        btnSignIn.setOnClickListener {
            phoneAuthentication()
        }
       btnSignInMicrosoft.setOnClickListener {
           signInMicrosoftfirst()
            signinToMicrosoft()
       }
    }



    private fun phoneAuthentication() {
        when {
            TextUtils.isEmpty(editTextMobileForLogin.text.toString()) -> {
                Toast.makeText(this,getString(R.string.toast_enter_mobile_no),Toast.LENGTH_SHORT).show()
            }
            editTextMobileForLogin?.text?.length!! < TEN -> {
                Toast.makeText(this,getString(R.string.toast_enter_valid_mobile_no),Toast.LENGTH_SHORT).show()
            }
            else -> {
                firebasePhoneAuth(editTextMobileForLogin.text.toString())
            }
        }
    }
    private fun firebasePhoneAuth(phone: String?) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(INDIA_COUNTRY_CODE + phone)
            .setTimeout(TIMEOUT_SIXTY, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun init() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MainActivity, getString(R.string.phone_verfication_failed), Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.apply {
                    putExtra(STOREDVERFICATIONID_KEY_INTENT, verificationId)
                    startActivity(this)
                    finish()
                }
            }
        }
    }



    private fun signInMicrosoftfirst() {
        firebaseAuth
            .startActivityForSignInWithProvider( /* activity= */this, provider.build())
            .addOnSuccessListener {
                Toast.makeText(this,it.user.toString(),Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
    }

    private fun signinToMicrosoft() {

        val pendingResultTask : Task<AuthResult>? = firebaseAuth.getPendingAuthResult()
        Log.e(TAG,"Signing with microsoft");
        if (pendingResultTask != null) {
            pendingResultTask.addOnSuccessListener(OnSuccessListener<AuthResult> { authResult ->
                Log.d(TAG, "checkPending:onSuccess:$authResult")
            }).addOnFailureListener(OnFailureListener { e ->
                Log.w(TAG, "checkPending:onFailure", e)
            })
        } else {
            Log.d(TAG, "pending: null")
        }
        val firebaseUser: FirebaseUser? = firebaseAuth.getCurrentUser()

        firebaseUser?.startActivityForLinkWithProvider( /* activity= */this, provider.build())?.addOnSuccessListener {
                Toast.makeText(this, it.user.toString(), Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                // Handle failure.
            }

    }
}