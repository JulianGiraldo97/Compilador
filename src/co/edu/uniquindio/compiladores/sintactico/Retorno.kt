package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.math.exp

class Retorno(var expresion:Expresion):Sentencia(){


    override fun getArbolVisual(): TreeItem<String> {
        var raiz=TreeItem("Retorno")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }

    override fun toString(): String {
        return "Retorno(expresion=$expresion)"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
        expresion.analizarSemantica(tablaSimbolos, listaErrores, simbolo)

        var t1=expresion is ExpresionAritmetica;
        var t2=expresion is ExpresionRelacional;
        var t3= expresion is ExpresionLogica;
        var t4=expresion is ExpresionCadena;

        if(t1 && (simbolo.tipo!="Entero" && simbolo.tipo!="Decimal")){
            listaErrores.add(Error("El tipo de retorno no coincide con el tipo de dato ${simbolo.tipo}",simbolo.fila,
                simbolo.columna))
        }else if (t2 && simbolo.tipo!="Logico"){
            listaErrores.add(Error("El tipo de retorno no coincide con el tipo de dato ${simbolo.tipo}",simbolo.fila,
                simbolo.columna))
        }else if (t3 && simbolo.tipo!="Logico"){
            listaErrores.add(Error("El tipo de retorno no coincide con el tipo de dato ${simbolo.tipo}",simbolo.fila,
                simbolo.columna))
        }else if (t4 && simbolo.tipo!="Cadena"){
            listaErrores.add(Error("El tipo de retorno no coincide con el tipo de dato ${simbolo.tipo}",simbolo.fila,
                simbolo.columna))
        }

    }

    override fun getJavaCode(): String {
        return "return"+expresion.getJavaCode()+";"
    }
}