package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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
    override fun getArbolVisual(): TreeItem<String> {
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, s: Simbolo) {
        if (valor!=null){
            if (valor!!.termino.categoria==Categoria.IDENTIFICADOR){
                var simbolo=tablaSimbolos.buscarSimboloValor(valor!!.termino.lexema,s.nombre)
print("${s!!.nombre} , ")
                if (simbolo==null){
                    listaErrores.add(
                        Error(
                            "El campo ${valor!!.termino.lexema} no existe en el ambito ${s.nombre}",
                            valor!!.termino.fila, valor!!.termino.columna
                        )
                    )

                }else{

                        if (!(simbolo.tipo=="Entero" || simbolo.tipo=="Decimal")){
                        listaErrores.add(Error("La variable ${valor!!.termino.lexema} no es numerica",valor!!.termino.
                        fila,valor!!.termino.columna))
                    }
                }
            }
        }
        if (exp1!=null){
            exp1!!.analizarSemantica(tablaSimbolos, listaErrores,s)

        }
        if (exp2!=null){
            exp2!!.analizarSemantica(tablaSimbolos, listaErrores, s)

        }
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,listaErrores: ArrayList<Error>): String {
    if (exp1!=null && exp2!=null){


        var tipo1= exp1!!.obtenerTipo(tablaSimbolos, ambito,listaErrores)
        var tipo2= exp2!!.obtenerTipo(tablaSimbolos, ambito,listaErrores)

        if (tipo1 == "Decimal" || tipo2=="Decimal"){
            return "Decimal"
        }else{
            return "Entero"
        }
    }


    else if (exp1!=null){
        return exp1!!.obtenerTipo(tablaSimbolos, ambito,listaErrores)
    }


    else if (valor!=null && exp1!=null){

 var tipo1= obtenerTipoCampo(valor, ambito, tablaSimbolos, listaErrores)

        var tipo2= exp1!!.obtenerTipo(tablaSimbolos, ambito,listaErrores)

        if (tipo1 == "Decimal" || tipo2=="Decimal"){
            return "Decimal"
        }else{
            return "Entero"
        }
    }


    else if (valor!=null){

return obtenerTipoCampo(valor, ambito, tablaSimbolos, listaErrores)
    }

return ""
     }

fun obtenerTipoCampo(valor: ValorNumerico?,ambito: String,tablaSimbolos: TablaSimbolos,listaErrores: ArrayList<Error>):String{
    if (valor!!.termino.categoria== Categoria.ENTERO) {
        return "Entero"
    }else if (valor!!.termino.categoria== Categoria.DECIMAL){
        return  "Decimal"
    }else{
        var simbolo=tablaSimbolos.buscarSimboloValor(valor!!.termino.lexema,ambito)
        if (simbolo!=null){
            return simbolo.tipo
        }else{

            listaErrores.add(Error("El campo ${valor!!.termino.lexema} no existe en el ambito $ambito",
                valor!!.termino.fila, valor!!.termino.columna))
        }
    }
return ""
}

    override fun getJavaCode(): String {
        if(exp1!=null&&operador!=null&&exp2!=null){
            return "("+exp1!!.getJavaCode()+")"+operador!!.getJavaCode()+"("+exp2!!.getJavaCode()+")"
        } else if(exp1!=null){
            return "("+exp1!!.getJavaCode()+")"
        } else if(valor!=null&&operador!=null&&exp2!=null){
            return valor!!.getJavaCode()+operador!!.getJavaCode()+"("+exp2!!.getJavaCode()+")"
        } else {
            return valor!!.getJavaCode()
        }
    }


    }