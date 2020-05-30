package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token(var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {




    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode():String{
        if(categoria== Categoria.PALABRA_RESERVADA){
            if(lexema=="Entero"){
                return "int"
            } else if(lexema =="Real"){
                return "double"
            } else if(lexema=="Decimal"){
                return "float"
            } else if(lexema=="Cadena"){
                return "String"
            } else if(lexema=="Caracter"){
                return "char"
            } else if(lexema=="NoRetorno"){
                return "void"
            } else if(lexema=="Leer"){
                return "JOptionPane.showInputDialog"
            } else if(lexema=="Retornar"){
                return "return"
            } else if(lexema=="Imprimir"){
                return "System.out.println"
            } else if(lexema=="SiSeCumple"){
                return "if"
            } else if(lexema=="DeLoContrario"){
                return "else"
            } else if(lexema=="Mientras"){
                return "while"
            } else if(lexema=="Logico"){
                return "boolean"
            } else if(lexema=="Verdadero"){
                return "true"
            } else if(lexema=="Falso"){
                return "false"
            } else if(lexema=="Lista"){
                return "[]"
            } else if(lexema=="Agregar"){
                return ".add"
            } else if(lexema=="Fn"){
                return ""
            } else if(lexema=="Intentar"){
                return "try"
            } else if(lexema=="NoSeLogro"){
                return "catch"
            } else if(lexema=="Detener"){
                return "break"
            }
        } else if(categoria==Categoria.COMENTARIO){
            return "//"+lexema.substring(1,lexema.length-1)
        } else if(categoria==Categoria.CARACTER){
            return lexema.replace("^","'")
        } else if(categoria==Categoria.CADENA_CARACTERES){
            return  lexema.replace("#","\"")
        }
        return lexema
    }
}