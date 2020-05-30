package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion(var expresion: Expresion):Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Impresion")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }

    override fun toString(): String {
        return "Impresion(Expresion=$expresion)"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
        expresion.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
    }

    override fun getJavaCode(): String {
        return "JOptionPane.showMessageDialog(null.\""+expresion.getJavaCode()+"\");"
    }
}