package co.edu.uniquindio.compiladores.test

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintactico.AnalizadorSintactico

fun main(){
    val lexico = AnalizadorLexico("fun sumar Entero(Entero a, Real b){{ }")
    lexico.analizar()

    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)
}