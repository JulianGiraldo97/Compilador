package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>,simbolo: Simbolo) {
        var s= tablaSimbolos.buscarSimboloValor(lectura.lexema,simbolo.nombre)
        if (s==null){
            listaErrores.add(Error("El campo ${lectura.lexema} no existe dentro del ambito ${simbolo.nombre}",lectura.fila,lectura.columna))
        }
    }

    override fun getJavaCode(): String {
        return lectura.getJavaCode()+"=JOptionPane.showInputDialog(null,\"Ingrese el dato:\");"
    }
}