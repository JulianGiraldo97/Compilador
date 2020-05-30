package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo(var identificador: Token,var tipoDato:Token, var terminos:ArrayList<Argumento>): Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Arreglo")
        raiz.children.add(TreeItem("Identificador: ${identificador.lexema}  Tipo: ${tipoDato.lexema}"))
        if (terminos.isNotEmpty()) {
            val argumentos = TreeItem("Argumentos")
            for (arg in terminos) {
                argumentos.children.add(arg.getArbolVisual())
            }
            raiz.children.add(argumentos)
        }
        return raiz
    }

    override fun toString(): String {
        return "Arreglo(identificador=$identificador, tipoDato=$tipoDato, terminos=$terminos)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(identificador.lexema,tipoDato.lexema,ambito,identificador.fila,identificador.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>,simbolo: Simbolo) {
        for (a in terminos){
            var tipo=a.obtenerTipo(tablaSimbolos,simbolo.nombre)
            if (tipo.isEmpty()){
                listaErrores.add(Error("El campo ${a.termino.termino.lexema} no existe en el ambito ${simbolo.nombre}",identificador.fila,identificador.columna))
            }
            else if(tipo!=tipoDato.lexema){
                listaErrores.add(Error("Le esta asignando un valor de tipo ${tipo} a un arreglo de tipo ${tipoDato.lexema}"
                    ,identificador.fila, identificador.columna))
            }

        }
    }

    override fun getJavaCode(): String {
        var codigo=tipoDato.getJavaCode()+"[] "+identificador.lexema
        if(terminos!=null){
            codigo+="={"
            for(t in terminos){
                codigo+=t.getJavaCode()+","
            }
            codigo=codigo.substring(0,codigo.length-1)
            codigo+="}"
        }
        codigo+=";"
        return codigo
    }
}