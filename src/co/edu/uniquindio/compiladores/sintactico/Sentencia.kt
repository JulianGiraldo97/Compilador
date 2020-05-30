package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia {


    open fun getArbolVisual():TreeItem<String>{
        return TreeItem("Sentencia")

    }
    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {

    }
    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<Error>,simbolo: Simbolo){

    }

    open fun getJavaCode():String{
        return ""
    }
}