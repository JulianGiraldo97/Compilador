package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Parametro(var nombre:Token, var tipoDato:Token) {


    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }

    fun getArbolVisual(): TreeItem<String>{
        return TreeItem("Identificador: ${nombre.lexema} ; Tipo de dato: ${tipoDato.lexema}")
    }

    fun getJavaCode():String{
        var codigo=tipoDato.getJavaCode()+" "+nombre.getJavaCode()
        return codigo
    }

}