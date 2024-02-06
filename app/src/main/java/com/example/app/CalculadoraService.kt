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

    fun calculaResultados(visor: String): String{
        val digitos = digitos(visor)
        if(digitos.isEmpty()) return ""

        val multDiv = multDivCalculo(digitos)
        if(multDiv.isEmpty()) return ""

        var result = somaSubCalculo(multDiv)
        if(result % 1 == 0f)
            return result.toInt().toString()
        return result.toString()
    }

    private fun somaSubCalculo(list: MutableList<Any>): Float {
        var result = list[0] as Float

        for(i in list.indices)
            if(list[i] is Char && i != list.lastIndex ){
                val operador = list[i]
                val digitoProx = list[i + 1] as Float
                when(operador){
                    '+' -> result += digitoProx
                    '-' -> result -= digitoProx

                }
            }

        return result
    }

    private fun multDivCalculo(digitos: MutableList<Any>): MutableList<Any> {
        var list = digitos
        while (list.contains('x') || list.contains('/') || list.contains('%'))
            list = calcMultDiv(list)
        return list
    }

    private fun calcMultDiv(list: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = list.size
        for(i in list.indices) {
            if (list[i] is Char && i != list.lastIndex && i < restartIndex) {
                val operador = list[i]
                val digitoAnt = list[i - 1] as Float
                val digitoProx = list[i + 1] as Float
                when (operador) {
                    'x' -> {
                        newList.add(digitoAnt * digitoProx)
                        restartIndex = i + 1
                    }

                    '/' -> {
                        newList.add(digitoAnt / digitoProx)
                        restartIndex = i + 1
                    }

                    '%' -> {
                        newList.add(digitoAnt * digitoProx / 100)
                        restartIndex = i + 1
                    }

                    else -> {
                        newList.add(digitoAnt)
                        newList.add(operador)
                    }
                }

            }
            if (i > restartIndex)
                newList.add(list[i])
        }

        return newList
    }


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
            lista.add(digitoAtual.toFloat())
        return lista
    }
}