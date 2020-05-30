package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class AsignacionVariable(var identificador: Token,var operador:Token, var expresion:Expresion):Sentencia() {
    override fun toString(): String {
        return "AsignacionVariable(identificador=$identificador, asignacion=$operador expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Asignacion variable")
        raiz.children.add(TreeItem("Identificador: ${identificador.lexema}"))
        raiz.children.add(TreeItem("Asignacion:${operador.lexema}"))
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, s: Simbolo) {
        var simbolo=tablaSimbolos.buscarSimboloValor(identificador.lexema,s.nombre)
        if (simbolo==null){
            listaErrores.add(Error("La variable ${identificador.lexema} no existe dentro del ambito ${s.nombre}",identificador.fila,identificador.columna))

        }else {
            var tipo=simbolo.tipo
            if (expresion!=null) {
                var tipoexp=expresion!!.obtenerTipo(tablaSimbolos, s.nombre, listaErrores)
                    if (tipoexp!=tipo && tipoexp!=""){
                            listaErrores.add(Error("Le esta asignando un valor $tipoexp al campo ${identificador.lexema} de tipo $tipo",
                                    identificador.fila,identificador.columna))
                }

            }
        }
    }

    override fun getJavaCode(): String {
        return identificador.getJavaCode()+operador.getJavaCode()+expresion.getJavaCode()+";"
    }

}