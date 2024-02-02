package com.example.app

import android.content.Context

class Calculator (context : Context){

    fun digitos(visor:String) : MutableList<Any>{
        val lista = mutableListOf<Any>()
        var digitoAtual = ""
        for(caracter in visor){
            if(caracter.isDigit() || caracter =='.'){
                digitoAtual += caracter
            }
            else{
                lista.add(digitoAtual.toFloat())
                digitoAtual = ""
                lista.add(caracter)
            }
        }
        for(digito in lista){
            println(digito)
        }
        return lista
    }
}