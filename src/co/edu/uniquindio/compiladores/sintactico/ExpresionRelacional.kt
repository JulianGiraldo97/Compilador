package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional(var exp: ExpresionAritmetica,var operador:Token, var exp2: ExpresionAritmetica):Expresion(){
    override fun toString(): String {
        return "ExpresionRelacional(exp=$exp, operador=$operador, exp2=$exp2)"
    }
    override open fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Expresion relacional")
        raiz.children.add(exp.getArbolVisual())
        raiz.children.add(TreeItem("Operador: ${operador!!.lexema}"))
        raiz.children.add(exp2.getArbolVisual())

        return raiz
    }
}