package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Condicional(var expresion:Expresion, var listaSentencias:ArrayList<Sentencia>,var sentenciaElse:ArrayList<Sentencia>?): Sentencia() {

    override fun toString(): String {
        return "Condicional(expresion=$expresion, listaSentencias=$listaSentencias, sentenciaElse=$sentenciaElse)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Desicion")

        var condicion=TreeItem("Condicion")
        condicion.children.add(expresion.getArbolVisual())
        raiz.children.add(condicion)


        var raizTrue=TreeItem("Sentencia se cumple")
        for(s in listaSentencias){
            raizTrue.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizTrue)


        if(sentenciaElse!=null){
            var raizFalsa=TreeItem("Sentencia no se logro")
            for(s in sentenciaElse!!){
                raizFalsa.children.add(s.getArbolVisual())
            }
            raiz.children.add(raizFalsa)
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (s in listaSentencias){
s.llenarTablaSimbolos(tablaSimbolos,listaErrores,ambito)
        }
        if (sentenciaElse!=null){
            for (s in sentenciaElse!!){
                s.llenarTablaSimbolos(tablaSimbolos,listaErrores,ambito)
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
        if (expresion!=null) {
            expresion.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
        }
        for (s in listaSentencias){
            s.analizarSemantica(tablaSimbolos,listaErrores,simbolo)
        }
        if (sentenciaElse!=null) {
            for (s in sentenciaElse!!) {
                s.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
            }
        }
    }
    override fun getJavaCode():String{
        var codigo="if("+expresion.getJavaCode()+"){"
        for(s in listaSentencias){
            codigo+=s.getJavaCode()
        }
        codigo+="}"
        if(sentenciaElse!=null){
            codigo+="else{"
            for(s in sentenciaElse!!){
                codigo+=s.getJavaCode()
            }
            codigo+="}"
        }
        return codigo
    }
}