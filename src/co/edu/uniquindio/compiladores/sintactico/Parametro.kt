package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token

class Parametro(var nombre:Token, var tipoDato:Token) {


    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }
}