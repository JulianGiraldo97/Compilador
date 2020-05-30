package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class ValorNumerico(var signo: Token?, var termino:Token) {
    override fun toString(): String {
        return "ValorNumerico(signo=$signo, termino=$termino)"
    }

     fun getArbolVisual(): TreeItem<String> {
         var raiz= TreeItem("Valor numerico")
         if(signo!=null){
             raiz.children.add(TreeItem("Termino: ${signo?.lexema} ${termino.lexema}"))
         }
        else{
             raiz.children.add(TreeItem("Termino:  ${termino.lexema}"))
         }

         return raiz
     }

    fun getJavaCode():String{
        if(signo!=null){
            return signo!!.getJavaCode()+termino.getJavaCode()
        } else {
            return termino.getJavaCode()
        }
    }
}