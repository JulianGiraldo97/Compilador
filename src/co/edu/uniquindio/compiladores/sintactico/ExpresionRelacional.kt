package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional(var exp: ExpresionAritmetica,var operador:Token, var exp2: ExpresionAritmetica):Expresion(){
    override fun toString(): String {
        return "ExpresionRelacional(exp=$exp, operador=$operador, exp2=$exp2)"
    }
    override open fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Expresion relacional")
        raiz.children.add(exp.getArbolVisual())
        raiz.children.add(TreeItem("Operador: ${operador!!.lexema}"))
        raiz.children.add(exp2.getArbolVisual())

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,listaErrores: ArrayList<Error>): String {
        return "Logico"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
        if (exp!=null && exp2!=null){
            exp.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
            var tipo1=exp!!.obtenerTipo(tablaSimbolos,simbolo.nombre,listaErrores)
            exp2.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
            var tipo2=exp2!!.obtenerTipo(tablaSimbolos,simbolo.nombre,listaErrores)

            if (!((tipo1=="Entero" || tipo1=="Decimal") && (tipo2=="Entero" || tipo2=="Decimal"))){
listaErrores.add(Error("Los valores de la expresion relacional deben ser numericos",operador.fila,operador.columna))
            }
        }
    }

    override fun getJavaCode(): String {
            return exp.getJavaCode()+operador.getJavaCode()+exp2.getJavaCode()
    }

}