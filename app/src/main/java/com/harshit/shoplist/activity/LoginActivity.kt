package com.harshit.shoplist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.harshit.shoplist.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var etEmailLogin: EditText
    lateinit var etPasswordLogin: EditText
    lateinit var etEmailRegister: EditText
    lateinit var etPasswordRegister: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        etEmailRegister = findViewById(R.id.etEmailRegister)
        etPasswordRegister = findViewById(R.id.etPasswordRegister)

        btnRegister.setOnClickListener {
            registerUser()
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun registerUser() {
        val email = etEmailRegister.text.toString()
        val password = etPasswordRegister.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                        Toast.makeText(this@LoginActivity, "Thanks for registering!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loginUser() {
        val email = etEmailLogin.text.toString()
        val password = etPasswordLogin.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                        Toast.makeText(this@LoginActivity, "Welcome", Toast.LENGTH_SHORT).show()
                        startActivity()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) { // not logged in
            Toast.makeText(this, "Please Login To Continue", Toast.LENGTH_SHORT).show()
        } else {
            startActivity()
        }
    }

    private fun startActivity(){
        val loginIntent = Intent(this, ShopLists::class.java)
        startActivity(loginIntent)
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}