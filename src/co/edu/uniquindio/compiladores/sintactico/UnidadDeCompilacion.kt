package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

class UnidadDeCompilacion (var listaFunciones:ArrayList<Funcion>) {

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual():TreeItem<String>{
        var raiz = TreeItem("Unidad de compilación")
        for (f in listaFunciones){
            raiz.children.add(f.getArbolVisual())

        }
        return raiz
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<Error>){

        for (f in listaFunciones){
            f.llenarTablaSimbolos(tablaSimbolos, listaErrores,"unidadcompilacion")

        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<Error>){
        for (f in listaFunciones){
            f.analizarSemantica(tablaSimbolos, listaErrores)

        }
    }

    fun getJavaCode():String{
        var codigo="import javax.swing.JOptionPane; public class Principal{"

        for(f in listaFunciones){
            codigo+=f.getJavaCode();
        }
        codigo+="}"
        return codigo
    }
}
