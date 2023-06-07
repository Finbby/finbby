package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        register()
    }

    private fun register() {
        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val emailIsValid = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

        when {
            name.isEmpty() -> binding.edtName.error = "Name can't be blank"
            email.isEmpty() -> binding.edtEmail.error = "Email can't be blank"
            password.isEmpty() -> binding.edtPassword.error = "Password must not be blank"
            emailIsValid -> binding.edtEmail.error = "Incorrect email format"
            password.length < 8 -> binding.edtPassword.error = ""
            else -> {

            }
        }
    }
}