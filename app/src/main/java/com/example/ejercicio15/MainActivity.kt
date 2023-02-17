package com.example.ejercicio15

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio15.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        welcomeUser()

        cambiarImagenSuperiorConTiempo()
        cambiarImagenInferiorConTiempo()

        initCityBossesFile()
        crearPersonaje()
        funcionDado()
    }

    private fun welcomeUser() {
        // Recuperar email desde sharedprefs
        val sharedPreference = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val email = sharedPreference.getString("USER_EMAIL", null)

        if(email == null)
            binding.welcome.text = "Bienvenido, jugador"
        else
            binding.welcome.text = "Bienvenido,\n$email"

        binding.logoff.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
            val sharedPreference = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.clear()
            editor.apply()
        }
    }

    private fun initCityBossesFile() {
        val sharedPreference = getSharedPreferences("CITY_BOSSES_REMAINING", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        if(sharedPreference.getInt("CITY_BOSSES_REMAINING", 0) == 0){
            editor.putInt("CITY_BOSSES_REMAINING", 0)
            editor.apply()
        }
    }

    private fun crearPersonaje() {
        val personaje = Personaje()

        val sharedPreference = getSharedPreferences("PERSONAJE_APLICACION", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val gson = Gson()
        val jsonPersonajeCheck = sharedPreference.getString("PERSONAJE", null)

        if(jsonPersonajeCheck == null){
            val json = gson.toJson(personaje)
            editor.putString("PERSONAJE", json)
            editor.apply()
        }
    }

    private fun funcionDado() {
        binding.imagenInferior.setOnClickListener {
            val bossesRestantes = getCityBossesRemaining()
            if(bossesRestantes > 0){
                when((0..3).random()){
                    1 -> startActivity(Intent(this, Objeto::class.java))
                    2 -> startActivity(Intent(this, Mercader::class.java))
                    3 -> startActivity(Intent(this, Enemigo::class.java))
                }
            } else{
                when((0..4).random()){
                    1 -> startActivity(Intent(this, Objeto::class.java))
                    2 -> startActivity(Intent(this, Ciudad::class.java))
                    3 -> startActivity(Intent(this, Mercader::class.java))
                    4 -> startActivity(Intent(this, Enemigo::class.java))
                }
            }
        }
    }

    private fun getCityBossesRemaining(): Int {
        val sharedPreference = getSharedPreferences("CITY_BOSSES_REMAINING", Context.MODE_PRIVATE)
        return sharedPreference.getInt("CITY_BOSSES_REMAINING", 0)
    }

    private fun cambiarImagenInferiorConTiempo() {
        var i = 0
        val imagenes = arrayOf(R.drawable.dado_normal, R.drawable.dado_invertido)
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                binding.imagenInferior.setImageResource(imagenes[i++ % imagenes.size])
                handler.postDelayed(this, 500) // delay de 500ms
            }
        }
        handler.post(runnable)
    }

    private fun cambiarImagenSuperiorConTiempo() {
        var i = 0
        val imagenes = arrayOf(R.drawable.campo, R.drawable.montana, R.drawable.cueva)
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                binding.imagenSuperior.setImageResource(imagenes[i++ % imagenes.size])
                handler.postDelayed(this, 2000) // delay de 1 segundo
            }
        }
        handler.post(runnable)
    }
}