package com.example.finbbyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.finbbyapp.MainActivity
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        login()
    }

    private fun login() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val emailIsValid = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

        when {
            email.isEmpty() -> binding.edtEmail.error = "Email can't be blank"
            password.isEmpty() -> binding.edtPassword.error = "Password must not be blank"
            emailIsValid -> binding.edtEmail.error = "Incorrect email format"
            password.length < 8 -> binding.edtPassword.error = ""
            else -> {

            }
        }
    }
}