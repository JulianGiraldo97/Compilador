package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Impresion(var impresion: Token):Sentencia() {
    override fun toString(): String {
        return "Impresion(impresion=$impresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Impresion")
        raiz.children.add(TreeItem("Termino: ${impresion.lexema}"))
        return raiz
    }
}