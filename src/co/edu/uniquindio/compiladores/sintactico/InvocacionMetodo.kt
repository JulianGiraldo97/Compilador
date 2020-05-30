package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class InvocacionMetodo(var identificador:Token, var terminos:ArrayList<Argumento>):Sentencia() {
    override fun toString(): String {
        return "InvocacionMetodo(identificador=$identificador, terminos=$terminos)"
    }
   override fun getArbolVisual(): TreeItem<String> {
       var raiz = TreeItem("Invocacion metodo")
       raiz.children.add(TreeItem("Identificador: ${identificador.lexema}"))
       var raizArgumentos = TreeItem("Argumentos")
       for (p in terminos){
           raizArgumentos.children.add(p.getArbolVisual())
       }
       raiz.children.add(raizArgumentos)
       return raiz
   }
    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos,ambito:String):ArrayList<String>{
        var listaArg=ArrayList<String>()
        for (a in terminos){
            listaArg.add(a.obtenerTipo(tablaSimbolos,ambito))
        }
        return listaArg
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
        var listaTiposArg= obtenerTiposArgumentos(tablaSimbolos, simbolo.nombre)
        var s=tablaSimbolos.buscarSimboloFuncion(identificador.lexema,listaTiposArg)
        if (s==null){
listaErrores.add(Error("La funcion ${identificador.lexema} con argumentos tipo $listaTiposArg no existe",identificador.fila,
    identificador.columna))
        }
    }

    override fun getJavaCode(): String {
        var codigo=identificador.getJavaCode()+"("
        for(t in terminos){
            codigo+=t.getJavaCode()+","
        }
        codigo=codigo.substring(0,codigo.length-1)
        codigo+=");"
        return codigo
    }
}