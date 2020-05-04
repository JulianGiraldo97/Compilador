package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem

class Argumento(var termino: Termino) {
    override fun toString(): String {
        return "Argumento(termino=$termino)"
    }
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Termino: ${termino.termino.lexema}")
    }
}