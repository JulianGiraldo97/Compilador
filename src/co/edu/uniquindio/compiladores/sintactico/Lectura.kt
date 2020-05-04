package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Lectura(var lectura:Token):Sentencia() {
    override fun toString(): String {
        return "Lectura(lectura=$lectura)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Lectura")
        raiz.children.add(TreeItem("Dato: ${lectura.lexema}"))
        return raiz
    }
}