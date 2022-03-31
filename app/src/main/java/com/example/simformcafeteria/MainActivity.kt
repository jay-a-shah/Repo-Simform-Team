package com.example.simformcafeteria

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.btnSignIn
import kotlinx.android.synthetic.main.activity_main.btnSignInMicrosoft
import kotlinx.android.synthetic.main.activity_main.editTextMobileForLogin


class MainActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    val provider = OAuthProvider.newBuilder("microsoft.com")
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    companion object{
        var TAG = "Microsoft"
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

    private fun init() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MainActivity,"Verfication Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext,SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun phoneAuthentication() {
            if(TextUtils.isEmpty(editTextMobileForLogin.text.toString())){
                Toast.makeText(this,getString(R.string.toast_enter_phone),Toast.LENGTH_SHORT).show()
            }else if(editTextMobileForLogin?.text?.length!! < 10) {
                Toast.makeText(this,getString(R.string.toast_enter_valid_phone),Toast.LENGTH_SHORT).show()
            } else {
                firebasePhoneAuth()
            }
    }

    private fun firebasePhoneAuth() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber("+91" + editTextMobileForLogin.text.toString()).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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