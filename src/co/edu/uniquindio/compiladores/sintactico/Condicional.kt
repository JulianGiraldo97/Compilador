package co.edu.uniquindio.compiladores.sintactico

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
}