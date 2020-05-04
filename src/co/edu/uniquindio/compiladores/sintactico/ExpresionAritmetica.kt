package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica():Expresion() {
    var exp1:ExpresionAritmetica?=null
    var exp2:ExpresionAritmetica?=null
    var operador:Token?=null
    var valor:ValorNumerico?=null

    constructor(exp1: ExpresionAritmetica?, operador:Token?, exp2: ExpresionAritmetica?):this(){
        this.exp1=exp1
        this.operador=operador
        this.exp2=exp2
    }
     constructor(exp1: ExpresionAritmetica?):this(){
         this.exp1=exp1
     }
    constructor(valor:ValorNumerico?, operador: Token?,exp2: ExpresionAritmetica?):this(){
        this.valor=valor
        this.operador=operador
        this.exp2=exp2
    }
    constructor(valor: ValorNumerico?):this(){
        this.valor=valor
    }
    override fun toString(): String {
        return "ExpresionAritmetica(exp1=$exp1, exp2=$exp2, operador=$operador, valor=$valor)"
    }
    override open fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Expresion Aritmetica")
        if(exp1!=null && operador!=null && exp2!=null){

            raiz.children.add(exp1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador: ${operador!!.lexema}"))
            raiz.children.add(exp2!!.getArbolVisual())

        }
        else if(exp1!=null){
            raiz.children.add(exp1!!.getArbolVisual())
        }
        else if(valor!=null && operador!=null && exp2!=null){

            raiz.children.add(valor!!.getArbolVisual())
            raiz.children.add(TreeItem(" Operador: ${operador!!.lexema}"))
            raiz.children.add(exp2!!.getArbolVisual())

        }

        else if(valor!=null){
            raiz.children.add(valor!!.getArbolVisual())
        }


return raiz
    }

}