package com.example.ejercicio15

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejercicio15.databinding.ActivitySeleccionaRazaBinding
import com.google.gson.Gson

class SeleccionaRaza : AppCompatActivity() {

    private lateinit var binding: ActivitySeleccionaRazaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionaRazaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personaje = recuperarPersonaje()

        binding.imagen.setBackgroundResource(R.drawable.inicio)

        binding.elfoBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.elfo)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.humanoBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.humano)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.enanoBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.enano)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.goblinBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.goblin)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.aceptarBtn.setOnClickListener {
            val imagen = binding.imagen.background
            if(imagen == getDrawable(R.drawable.elfo))
                personaje.raza = "Elfo"
            else if(imagen == getDrawable(R.drawable.humano))
                personaje.raza = "Humano"
            else if(imagen == getDrawable(R.drawable.enano))
                personaje.raza = "Enano"
            else if(imagen == getDrawable(R.drawable.goblin))
                personaje.raza = "Goblin"

            aplicarCambiosPersonaje(personaje)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun recuperarPersonaje(): Personaje {
        val sharedPreferences = getSharedPreferences("PERSONAJE_APLICACION", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("PERSONAJE", "")
        return gson.fromJson(json, Personaje::class.java)
    }

    private fun aplicarCambiosPersonaje(personaje: Personaje) {
        val sharedPreference = getSharedPreferences("PERSONAJE_APLICACION", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        val gson = Gson()
        val json = gson.toJson(personaje)
        editor.putString("PERSONAJE", json)
        editor.apply()
    }
}