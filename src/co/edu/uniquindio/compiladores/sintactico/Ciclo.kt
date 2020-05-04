package co.edu.uniquindio.compiladores.sintactico

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
}