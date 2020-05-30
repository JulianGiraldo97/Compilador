package co.edu.uniquindio.compiladores.sintactico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena():Expresion() {

    var exp: ExpresionCadena?=null
    var cadena:Token?=null

    constructor(cadena:Token?) : this() {
        this.cadena = cadena
    }

    constructor(cadena:Token?,exp: ExpresionCadena?) : this() {
        this.exp = exp
        this.cadena=cadena
    }

    override fun toString(): String {
        return "ExpresionCadena(exp=$exp, cadena=$cadena)"
    }
    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Expresion Cadena")

         if (cadena!=null && exp!=null){
            raiz.children.add(exp!!.getArbolVisual())
            raiz.children.add(TreeItem("Cadena: ${cadena!!.lexema}"))

        }
       else if(cadena!=null){
            raiz.children.add(TreeItem("Cadena: ${cadena!!.lexema}"))
        }



        return raiz
    }
    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,listaErrores: ArrayList<Error>):String{
        return "Cadena"
    }

    override fun getJavaCode(): String {
        var codigo=cadena!!.getJavaCode()
        if(exp!=null){
            codigo+="+"+exp!!.getJavaCode()
        }
        return codigo
    }

}