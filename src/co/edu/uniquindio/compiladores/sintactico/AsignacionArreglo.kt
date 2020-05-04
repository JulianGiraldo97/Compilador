package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class AsignacionArreglo( var identificador: Token,var terminos:ArrayList<Argumento>):Sentencia() {
    override fun toString(): String {
        return "AsignacionArreglo(identificador=$identificador, terminos=$terminos)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Asignacion arreglo")
        raiz.children.add(TreeItem("Identificador: ${identificador.lexema}"))
        var raizArgumentos = TreeItem("Argumentos")
        for (p in terminos){
            raizArgumentos.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizArgumentos)
        return raiz
    }
}