package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class AsignacionVariable(var identificador: Token, var expresion:Expresion):Sentencia() {
    override fun toString(): String {
        return "AsignacionVariable(identificador=$identificador, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Asignacion variable")
        raiz.children.add(TreeItem("Identificador: ${identificador.lexema}"))
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }

}