package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintactico.UnidadDeCompilacion

class AnalizadorSemantico(var unidadDeCompilacion: UnidadDeCompilacion) {

    var listaErrores: ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos =
        TablaSimbolos(listaErrores)

    fun llenarTablaSimbolos() {
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolos, listaErrores)
    }
    fun analizarSemantica() {
        unidadDeCompilacion.analizarSemantica(tablaSimbolos, listaErrores)
    }

}