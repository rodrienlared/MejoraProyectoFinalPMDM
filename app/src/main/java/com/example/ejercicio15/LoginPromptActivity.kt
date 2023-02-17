package com.example.ejercicio15

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.ejercicio15.databinding.ActivityLoginPromptBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private lateinit var binding: ActivityLoginPromptBinding

class LoginPromptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPromptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Chequeamos si ya hay un usuario logueado
        checkUserPreviousLogin()

        // Botones de login y registro
        setup()

    }

    private fun checkUserPreviousLogin() {
        val sharedPreference = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val email = sharedPreference.getString("USER_EMAIL", null)

        if(email != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setup() {
        binding.signup.setOnClickListener {
            if(binding.email.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()) {
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful) {
                        showHome(email)
                    } else {
                        showAlert(it.exception?.message.toString())
                    }
                }
            }
        }

        binding.login.setOnClickListener {
            if(binding.email.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()) {
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful) {
                        showHome(email)
                    } else {
                        showAlert(it.exception?.message.toString())
                    }
                }
            }
        }

        binding.googleButton.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, 100)
        }
    }

    private fun showHome(email: String) {
        val sharedPreference = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_EMAIL", email)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showAlert(exception: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario.\n\nError: $exception")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(Exception::class.java)
                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful) {
                            showHome(account.email.toString())
                        } else {
                            showAlert(it.exception?.message.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                showAlert(e.message.toString())
            }
        }
    }
}