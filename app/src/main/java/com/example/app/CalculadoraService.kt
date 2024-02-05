package com.example.app

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class CalculadoraService : Service(){

    private val binder = CalculadoraBinder()

    inner class CalculadoraBinder : Binder(){
        fun getService(): CalculadoraService = this@CalculadoraService
    }

    override fun onBind(intent: Intent): IBinder = binder
    fun digitos(visor:String) : MutableList<Any>{
        val lista = mutableListOf<Any>()
        var digitoAtual = ""
        for(caracter in visor){
            if(caracter.isDigit() || caracter ==',' || caracter == '.'){
                if (caracter == ',') digitoAtual +='.'
                else digitoAtual += caracter
            }
            else{
                lista.add(digitoAtual.toFloat())
                digitoAtual = ""
                lista.add(caracter)
            }
        }
        if (digitoAtual != "")
            lista.add(digitoAtual)
        return lista
    }
}