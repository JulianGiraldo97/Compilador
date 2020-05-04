package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionArreglo(var tipoDato: Token, var idVariable: Token): Sentencia() {
    override fun toString(): String {
        return "DeclaracionArreglo(tipoDato=$tipoDato, idVariable=$idVariable)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Declaracion arreglo")
        raiz.children.add(TreeItem("Tipo dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Identificador: ${idVariable.lexema}"))
        return raiz
    }
}