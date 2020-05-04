package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem

class Retorno(var termino:Termino):Sentencia(){
    override fun toString(): String {
        return "Retorno(termino=$termino)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz=TreeItem("Retorno")
        raiz.children.add(TreeItem("Termino: ${termino.termino.lexema}"))
        return raiz
    }
}