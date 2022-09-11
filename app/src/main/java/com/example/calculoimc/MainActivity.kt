package com.example.calculoimc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.pow

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcular.setOnClickListener {
            var s = 0

            if (altura.text.toString() == "" || altura.text.toString().toDouble() == 0.0){
                if (altura.text.toString() == "") {
                    altura.animation = shakeError()
                    altura.error = "O campo não pode estar vazio"
                }
                else if(altura.text.toString().toDouble() == 0.0) {
                    altura.animation = shakeError()
                    altura.error = "Os valores devem ser maior que 0"
                }
            } else {
                s += 1
            }

            if (peso.text.toString() == "" || peso.text.toString().toDouble() == 0.0){
                if (peso.text.toString() == "") {
                    peso.animation = shakeError()
                    peso.error = "O campo não pode estar vazio"
                }
                else if(peso.text.toString().toDouble() == 0.0) {
                    peso.animation = shakeError()
                    peso.error = "Os valores devem ser maior que 0"
                }
            } else {
                s += 1
            }

             if (s == 2){
                 calcular.isClickable = false

                 val height = altura.text.toString().toDouble()
                 val weight = peso.text.toString().toDouble()

                 var r = calculate(height, weight)
                 Handler(Looper.getMainLooper()).postDelayed({
                     if (resultado.text.toString() != "") resultado.text = ""
                 }, 500)

                 Handler(Looper.getMainLooper()).postDelayed({
                     if (r < 18.5) resultado.text = "Magreza"

                     else if (r >= 18.5 && r < 24.9) resultado.text = "Normal"

                     else if (r >= 24.9 && r < 30) resultado.text = "Sobrepeso (I)"

                     else if (r >= 30 && r < 40) resultado.text = "Obesidade (II)"

                     else if (r > 40) resultado.text = "Obesidade Grave (III)"


                 }, 750)

                 Handler(Looper.getMainLooper()).postDelayed({
                     calcular.isClickable = true
                 }, 600)

             }
        }
    }

    fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 250
        shake.interpolator = CycleInterpolator(3F)
        return shake
    }

    //Mostrar o botão de configuração
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.aboutDeveloper -> {
            val uri: Uri = Uri.parse("https://github.com/Pedro-1302")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun calculate(height: Double, weight: Double): Double {
        var imc = weight.toFloat() / pow(height, 2.0)
        return imc
    }
}