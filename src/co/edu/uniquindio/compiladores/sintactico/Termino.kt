package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token

class Termino(var termino: Token) {
    override fun toString(): String {
        return "Termino(termino=$termino)"
    }

    open fun obtenerTipo():String{
    return ""
    }

    fun getJavaCode():String{
        return termino.getJavaCode()
    }
}