package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionCampo(var tipoDato:Token, var idVariable:Token): Sentencia() {
    override fun toString(): String {
        return "DeclaracionCampo(tipoDato=$tipoDato, idVariable=$idVariable)"
    }

    override fun getArbolVisual():TreeItem<String>{
var raiz=TreeItem("Declaracion variable")
        raiz.children.add(TreeItem("Tipo dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Identificador: ${idVariable.lexema}"))
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(idVariable.lexema,tipoDato.lexema,ambito,idVariable.fila,idVariable.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {

    }
    override fun getJavaCode():String{
        var codigo=tipoDato.getJavaCode()+" "+idVariable.lexema+";"
        return codigo
    }
}