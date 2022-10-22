package com.example.appcontactuser.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcontactuser.databinding.SignInBinding
import com.google.android.material.snackbar.Snackbar

class SingInActivity: AppCompatActivity() {

    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authorization()
    }

    private fun authorization() {
        binding.signInButton.setOnClickListener {
            if (binding.etLogin.text.toString() == SECRET_KEY && binding.etPassword.text.toString() == SECRET_KEY) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                Snackbar.make(it,
                    "Wrong username or password, please try again",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val SECRET_KEY = "admin"
    }
}