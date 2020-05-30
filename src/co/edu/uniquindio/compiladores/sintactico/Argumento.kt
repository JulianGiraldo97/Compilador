package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Argumento(var termino: Termino) {
    override fun toString(): String {
        return "Argumento(termino=$termino)"
    }
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Termino: ${termino.termino.lexema}")
    }

     fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String):String{
       if (termino!=null){
           if (termino.termino.categoria==Categoria.ENTERO){
               return "Entero"
           }else if (termino.termino.categoria==Categoria.DECIMAL){
               return "Decimal"
           }else if (termino.termino.categoria==Categoria.CADENA_CARACTERES){
               return "Cadena"
           }else if (termino.termino.categoria==Categoria.CARACTER){
               return "Caracter"
           }else if (termino.termino.categoria==Categoria.LOGICO){
               return "Logico"
           }else if (termino.termino.categoria==Categoria.IDENTIFICADOR){
               var simbolo=tablaSimbolos.buscarSimboloValor(termino.termino.lexema,ambito)
               if (simbolo!=null){
                   return simbolo.tipo
               }           }
       }
        return ""
    }

    fun getJavaCode():String{
        return termino.getJavaCode();
    }
}