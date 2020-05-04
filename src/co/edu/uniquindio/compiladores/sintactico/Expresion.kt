package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem

open class Expresion {

    open fun getArbolVisual(): TreeItem<String> {

         return TreeItem("Expresion")
    }
}