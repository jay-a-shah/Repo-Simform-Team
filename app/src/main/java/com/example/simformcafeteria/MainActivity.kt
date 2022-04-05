package com.example.simformcafeteria

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simformcafeteria.OTPScreen.OTPActivity
import com.example.simformcafeteria.Utils.PHONENUMBER_KEY_INTENT
import com.example.simformcafeteria.Utils.TEN
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.btnSignIn
import kotlinx.android.synthetic.main.activity_main.btnSignInMicrosoft
import kotlinx.android.synthetic.main.activity_main.editTextMobileForLogin


class MainActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    val provider = OAuthProvider.newBuilder("microsoft.com")

    companion object{
        var TAG = "Microsoft"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.putExtra(PHONENUMBER_KEY_INTENT,editTextMobileForLogin.text.toString())
                startActivity(intent)
                finish()
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