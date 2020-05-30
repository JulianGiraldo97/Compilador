package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Ciclo(var expresion: Expresion,var lista:ArrayList<Sentencia>):Sentencia() {
    override fun toString(): String {
        return "Ciclo(expresion=$expresion, lista=$lista)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Ciclo")
        raiz.children.add(expresion.getArbolVisual())

        var raizSentencias = TreeItem("Sentencias")
        for (p in lista){
            raizSentencias.children.add(p.getArbolVisual())
        }

        raiz.children.add(raizSentencias)
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (s in lista){
            s.llenarTablaSimbolos(tablaSimbolos,listaErrores,ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>,simbolo: Simbolo) {

        expresion.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
        for (s in lista){
            s.analizarSemantica(tablaSimbolos,listaErrores, simbolo )
        }
    }

    override fun getJavaCode():String{
        var codigo="while("+expresion.getJavaCode()+"){"
        for(s in lista){
            codigo+=s.getJavaCode()
        }
        codigo+="}"
        return codigo
    }
}