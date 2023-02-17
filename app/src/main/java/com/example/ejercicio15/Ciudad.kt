package com.example.ejercicio15

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio15.databinding.ActivityCiudadBinding

class Ciudad : AppCompatActivity() {

    private lateinit var binding: ActivityCiudadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrarCiudad.setOnClickListener {
            // Ponemos el valor de CITY_BOSSES_REMAINING a 5 en el archivo "CITY_BOSSES_REMAINING" de las SharedPreferences
            setCityBossesRemainingToFive()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.continuarCiudad.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setCityBossesRemainingToFive() {
        val sharedPreference = getSharedPreferences("CITY_BOSSES_REMAINING", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("CITY_BOSSES_REMAINING", 5)
        editor.apply()
    }
}