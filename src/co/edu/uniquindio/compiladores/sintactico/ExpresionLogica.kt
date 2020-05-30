package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica() :Expresion() {
    var exp1: ExpresionLogica? = null
    var exp2: ExpresionLogica? = null
    var operador1: Token? = null
    var exp3: ExpresionRelacional? = null
    var operador2: Token? = null
    var termino:ExpresionAritmetica?=null
    var exp4: ExpresionLogica? = null
    var operador3:Token?=null
    var operador4:Token?=null
    var exp5: ExpresionLogica? = null
    var exp6:ExpresionRelacional?=null
    var token:Token?=null

    constructor(token:Token):this(){
        this.token=token

    }
    constructor(operador1: Token?,exp1: ExpresionLogica?,operador2: Token?,exp2: ExpresionLogica?): this(){


        this.exp1 = exp1
        this.operador1 = operador1
        this.exp2 = exp2
        this.operador2 = operador2
    }

    constructor(operador1: Token?,exp1: ExpresionLogica?) : this() {


        this.exp1 = exp1
        this.operador1=operador1
    }

    constructor(exp3: ExpresionRelacional?,operador3: Token?,exp4: ExpresionLogica?) : this() {

        this.exp3 = exp3
        this.operador3 = operador3
        this.exp4 = exp4
    }

    constructor(exp3: ExpresionRelacional?) : this() {

        this.exp3 = exp3
    }
    constructor(termino: ExpresionAritmetica?,operador4:Token?,exp5: ExpresionLogica?) : this() {


        this.termino = termino
        this.operador4 = operador4
        this.exp5 = exp5
    }
    constructor(termino: ExpresionAritmetica?) : this() {
        this.termino = termino
    }
    constructor(exp3: ExpresionRelacional?,operador3: Token?,exp6:ExpresionRelacional?) : this() {
        this.exp3 = exp3
        this.operador3 = operador3
        this.exp6 = exp6
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresion logica")

        if (token!=null){
            raiz.children.add(TreeItem("Valor: ${token!!.lexema}"))
        }


        else if(operador1!=null && exp1!=null && operador2!=null && exp2!=null){
            raiz.children.add(TreeItem("Operador: ${operador1!!.lexema}"))
            raiz.children.add(exp1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador: ${operador2!!.lexema}"))
            raiz.children.add(exp2!!.getArbolVisual())
        }
        else if(operador1!=null &&  exp1!=null){
            raiz.children.add(TreeItem("Operador: ${operador1!!.lexema}"))
            raiz.children.add(exp1!!.getArbolVisual())
        }
        else if(exp3!=null && operador3!=null && exp4!=null){
            raiz.children.add(exp3!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador: ${operador3!!.lexema}"))
            raiz.children.add(exp4!!.getArbolVisual())
        }
        else if(exp3!=null && operador3!=null && exp6!=null){
            raiz.children.add(exp3!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador: ${operador3!!.lexema}"))
            raiz.children.add(exp6!!.getArbolVisual())
        }
        else if (exp3!=null){

            raiz.children.add(exp3!!.getArbolVisual())
        }
        else if (termino!=null && operador4!=null && exp5!=null ){

            raiz.children.add(termino!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador: ${operador4!!.lexema}"))
            raiz.children.add(exp5!!.getArbolVisual())
        }
        else if (termino!=null){
            raiz.children.add(termino!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionLogica(exp1=$exp1, exp2=$exp2, operador1=$operador1, exp3=$exp3, operador2=$operador2, termino=$termino, exp4=$exp4, operador3=$operador3, operador4=$operador4, exp5=$exp5)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,listaErrores: ArrayList<Error>): String {
        return "Logico"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, simbolo: Simbolo) {
       if (exp1!=null && exp2!=null){
           exp1!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
           exp2!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else if(exp1!=null){
           exp1!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else if (exp3!=null && exp4!=null){
           exp3!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
           exp4!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else if (exp3!=null && exp6!=null){
           exp3!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
           exp6!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else  if (exp3!=null){
           exp3!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else if (termino!=null && exp5!=null){
           exp5!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
       }
       else if(termino!=null){
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, simbolo)
        }
    }

    override fun getJavaCode(): String {
        if(termino!==null&&operador4!=null&&exp5!=null){
            return termino!!.getJavaCode()+operador4!!.getJavaCode()+exp5!!.getJavaCode()
        } else if(termino!=null){
            return termino!!.getJavaCode()
        } else if(operador1!=null&&exp1!=null&&operador2!=null&&exp2!=null){
            return operador1!!.getJavaCode()+exp1!!.getJavaCode()+operador2!!.getJavaCode()+exp2!!.getJavaCode()
        } else if(operador1!=null&&exp1!=null){
            return operador1!!.getJavaCode()+exp1!!.getJavaCode()
        } else if(token!=null){
            return token!!.getJavaCode()
        } else if(exp3!=null&&operador3!=null&&exp4!=null){
            return exp3!!.getJavaCode()+operador3!!.getJavaCode()+exp4!!.getJavaCode()
        } else{
            return exp3!!.getJavaCode()
        }
    }
}