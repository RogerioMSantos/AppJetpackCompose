package com.example.app

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.text.isDigitsOnly

class CalculadoraService : Service(){

    private val binder = CalculadoraBinder()

    inner class CalculadoraBinder : Binder(){
        fun getService(): CalculadoraService = this@CalculadoraService
    }

    override fun onBind(intent: Intent): IBinder = binder

    fun calculaResultados(visor: String): String{
        val digitos = digitos(visor)
        if(digitos.isEmpty()) return ""
        val result = calculaResultadosRecursivo(digitos)
        return result.last().toString()
    }

    fun calculaResultadosRecursivo(digitos: MutableList<Any>): MutableList<Any>{
        val multDiv = multDivCalculo(digitos)
        if(multDiv.isEmpty()) return multDiv

        var result = mutableListOf<Any>(somaSubCalculo(multDiv))

        return result
    }

    private fun somaSubCalculo(list: MutableList<Any>): Float {
        var qtdParenteses = 0
        var parenteses = false
        var result = 0f
        if(list[0] == '('){
            parenteses = true
            val teste = calculaResultadosRecursivo(list.subList(1,list.lastIndex)).last()
            val num = teste.toString()
            result = num.toFloat()
        }
        else if(list[0] == ')'){
            return 0f
        }
        else result = list[0].toString().toFloat()
        for(i in list.indices) {
            if(parenteses) {
                if (list[i] == ')') {
                    qtdParenteses--
                    if(qtdParenteses == 0) parenteses = true
                    continue
                }
                if (list[i] == '(') {
                    qtdParenteses++
                    continue
                }

            }
            if(qtdParenteses == 0) {
                if (list[i] is Char && i != list.lastIndex) {
                    val operador = list[i]
                    if (operador == ')') {
                        return result
                    }
                    if (list[i + 1] == '(') {
                        val digitoProx = calculaResultadosRecursivo(
                            list.subList(
                                i + 2,
                                list.lastIndex + 1
                            )
                        ).last() as Float
                        parenteses = true
                        when (operador) {
                            '+' -> result += digitoProx
                            '-' -> result -= digitoProx

                        }
                        continue
                    }
                    if (list[i + 1] == ')') {
                        return result
                    }
                    val digitoProx = list[i + 1] as Float
                    when (operador) {
                        '+' -> result += digitoProx
                        '-' -> result -= digitoProx

                    }
                }
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
        var qtdParenteses = 0
        var parenteses = false
        for(i in list.indices) {
            if(parenteses) {
                if (list[i] == '(') {
                    qtdParenteses++
                    continue
                }
                if (list[i] == ')') {
                    qtdParenteses--
                    if(qtdParenteses == 0) parenteses = false
                    continue
                }
            }
            if (list[i] is Char && i != list.lastIndex && i < restartIndex) {
                val operador = list[i]
                if (operador == ')') return newList
                val digitoAnt = list[i - 1] as Float
                var digitoProx = list[i + 1]
                if (digitoProx == ')') return newList
                if (digitoProx == '(') {
                    digitoProx = calculaResultadosRecursivo(
                        list.subList(
                            i + 2,
                            list.lastIndex + 1
                        )
                    ).last() as Float
                    parenteses = true
                } else digitoProx = digitoProx as Float
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
        var digitoAtual = ""
        val lista = mutableListOf<Any>()

        for(caracter in visor){
            if(caracter == '(' && digitoAtual.isNotBlank()){
                lista.add(digitoAtual)
                digitoAtual = ""
                lista.add('*')
                lista.add('(')
                continue
            }

            if(caracter == ')' && digitoAtual.isNotBlank()){
                lista.add(digitoAtual)
                digitoAtual = ""
                lista.add(')')
                continue
            }
            if(caracter.isDigit() || caracter ==',' || caracter == '.'){
                if (caracter == ',') digitoAtual +='.'
                else digitoAtual += caracter
            }
            else if(caracter == '-' && digitoAtual == "")
                digitoAtual = "-"
            else{
                if(digitoAtual != "")
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