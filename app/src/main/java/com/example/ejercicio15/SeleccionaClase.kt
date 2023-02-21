package com.example.ejercicio15

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejercicio15.databinding.ActivitySeleccionaClaseBinding
import com.google.gson.Gson

class SeleccionaClase : AppCompatActivity() {

    private lateinit var binding: ActivitySeleccionaClaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionaClaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personaje = recuperarPersonaje()

        binding.imagen.setBackgroundResource(R.drawable.inicio)

        binding.magoBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.mago)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.ladronBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.ladron)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.guerreroBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.guerrero)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.berserkerBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.berserker)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.mercaderBtn.setOnClickListener {
            binding.imagen.setBackgroundResource(R.drawable.mercader)
            if(!binding.aceptarBtn.isEnabled)
                binding.aceptarBtn.isEnabled = true
        }

        binding.aceptarBtn.setOnClickListener {
            val imagen = binding.imagen.background
            if(imagen == getDrawable(R.drawable.mago))
                personaje.clase = "Mago"
            else if(imagen == getDrawable(R.drawable.ladron))
                personaje.clase = "Ladron"
            else if(imagen == getDrawable(R.drawable.guerrero))
                personaje.clase = "Guerrero"
            else if(imagen == getDrawable(R.drawable.berserker))
                personaje.clase = "Berserker"
            else if(imagen == getDrawable(R.drawable.mercader))
                personaje.clase = "Mercader"

            aplicarCambiosPersonaje(personaje)
            startActivity(Intent(this, SeleccionaRaza::class.java))
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