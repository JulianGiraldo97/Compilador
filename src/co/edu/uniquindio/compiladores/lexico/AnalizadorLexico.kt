package co.edu.uniquindio.compiladores.lexico

import org.omg.PortableInterceptor.ObjectReferenceTemplate

class AnalizadorLexico(var codigoFuente:String) {

    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var posicionActual = 0
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    var palabrasReservadas = ArrayList<String>()


    /*
    metodo que almacena los tokens para mostrar en la UI
     */
    fun  almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(
        Token(lexema, categoria, fila, columna)
    )

    /*
    metodo analizar que llama al resto de metodos del analizar lexico
     */
    fun analizar(){
        llenarPalabrasReservadas()
        while (caracterActual != finCodigo){

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCatacter()
                continue
            }
            if (esCadenaCaracteres()) continue
            if (esEntero()) continue
            if (esIdentificador()) continue
            if (esDecimal()) continue
            if (esReal()) continue
            if (esParentesisIzquierdo()) continue
            if (esParentesisDerecho()) continue
            if (esLLaveIzquierda()) continue
            if (esLLaveDerecha()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorLogico()) continue
            if (esOperadorRelacional()) continue
            if (esOperadorAsignacion()) continue
            if (esSeparador()) continue
            if (esTerminal()) continue
            if (esCaracter()) continue
            if (esComentario()) continue
            if (esIncremento()) continue
            if (esDecremento()) continue
            if (esComentarioBloque()) continue
            if (esPunto()) continue
            if (esDosPuntos()) continue
            if (esCorcheteDerecho()) continue
            if (esCorcheteIzquierdo()) continue


            almacenarToken(""+caracterActual,
                Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCatacter()
    }
    }

    /*
    llena la lista con las palabras reservadas
     */
    fun llenarPalabrasReservadas(){
        palabrasReservadas.add("Fn");
        palabrasReservadas.add("Mientras");
        palabrasReservadas.add("Entero");
        palabrasReservadas.add("Real");
        palabrasReservadas.add("Cadena");
        palabrasReservadas.add("Caracter");
        palabrasReservadas.add("Logico");
        palabrasReservadas.add("Verdadero");
        palabrasReservadas.add("NoRetorno");

        palabrasReservadas.add("SiSeCumple");
        palabrasReservadas.add("DeLoContrario");
        palabrasReservadas.add("Verdadero");
        palabrasReservadas.add("Falso");

        palabrasReservadas.add("Retornar");
        palabrasReservadas.add("Imprimir");
        palabrasReservadas.add("ImprimirError");
        palabrasReservadas.add("Leer");

        palabrasReservadas.add("Intentar"); // try
        palabrasReservadas.add("NoSeLogro"); // si no se se ejecuta el try

        palabrasReservadas.add("Detener"); // break
        palabrasReservadas.add("Omitir"); // continue
    }

    /*
    verifica un corchete izquierdo
     */
    fun esCorcheteIzquierdo():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        if(caracterActual=='['){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.CORCHETE_IZQUIERDO,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false
    }

    /*
    verifica un corchete derecho
     */
    fun esCorcheteDerecho():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        if(caracterActual==']'){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.CORCHETE_DERECHO,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false
    }

    /*
    verifica si es un punto
     */
    fun esPunto():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        var posInicial=posicionActual
        if(caracterActual=='.'){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            almacenarToken(lexema,Categoria.PUNTO,filaInicio,columnaInicio)
            return true
        }
        return false
    }

    /*
   verifica si un lexema son dos puntos
    */
    fun esDosPuntos():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        var posInicial=posicionActual
        if(caracterActual==':'){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            almacenarToken(lexema,Categoria.DOS_PUNTOS,filaInicio,columnaInicio)
            return true
        }
        return false
    }

    /*
    verifica si esun comentario de bloque
     */
    fun esComentarioBloque():Boolean{
         if(caracterActual=='/'){
             var lexema=""
             var filaInicio=filaActual
             var columnaInicio=columnaActual
             var posInicial=posicionActual
             lexema+=caracterActual
             obtenerSiguienteCatacter()
              if(caracterActual=='*'){
                  lexema+=caracterActual
                  obtenerSiguienteCatacter()

                  while (caracterActual!='*' && caracterActual!=finCodigo){
                      lexema+=caracterActual
                      obtenerSiguienteCatacter()
                  }
                  lexema+=caracterActual
                  obtenerSiguienteCatacter()
                  if(caracterActual=='/'){
                      lexema+=caracterActual
                      obtenerSiguienteCatacter()
                      almacenarToken(lexema,Categoria.COMENTARIO_BLOQUE,filaInicio,columnaInicio)
                      return true
                  }
                  else{
                      hacerBT(posInicial,filaInicio,columnaInicio)
                      return false
                  }
              }
              else{
                  hacerBT(posInicial,filaInicio,columnaInicio)
                  return false
              }
}
        return false
    }

    /*
    verifica que el lexema sea decremento
     */
    fun esDecremento():Boolean{

        if(caracterActual=='-'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial=posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if (caracterActual=='-'){
                lexema+=caracterActual
                almacenarToken(lexema,Categoria.DECREMENTO,filaInicio,columnaInicio)
                obtenerSiguienteCatacter()
                return true
            }
            else{
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
        }
        return false
    }

    /*
    verifica que el lexema sea incremento
     */
    fun esIncremento():Boolean{

        if(caracterActual=='+'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial=posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if (caracterActual=='+'){
                lexema+=caracterActual
                almacenarToken(lexema,Categoria.INCREMENTO,filaInicio,columnaInicio)
                obtenerSiguienteCatacter()
                return true
            }
            else{
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
        }
        return false
    }

    /*
    verifica una comentario
     */
    fun esComentario():Boolean{

        if(caracterActual=='['){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial=posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()

            while (caracterActual!=']' && caracterActual!=finCodigo){

                lexema+=caracterActual
                obtenerSiguienteCatacter()

            }
            if(caracterActual==']'){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
                almacenarToken(lexema,Categoria.COMENTARIO,filaInicio,columnaInicio)
                return true
            }
            else{
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
        }
        return false
    }

/*
se encarca de verificar si es un caracter
 */
    fun esCaracter():Boolean{
        if(caracterActual=='^'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial=posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            var contador=false

            while (contador==true){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
                contador=true
            }
                if(caracterActual=='^'){
                    lexema+=caracterActual
                    obtenerSiguienteCatacter()
                    almacenarToken(lexema,Categoria.CARACTER,filaInicio,columnaInicio)
                    return true
                }
            else{
                    hacerBT(posInicial,filaInicio,columnaInicio)
                    return false
                }

        }
return false
    }

    /*
    se encarga de verificar una cadena de caracteres
     */
fun esCadenaCaracteres():Boolean{

    if(caracterActual=='#'){
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        var posInicial=posicionActual
        lexema+=caracterActual
        obtenerSiguienteCatacter()

        while(caracterActual!='#'  && caracterActual != finCodigo){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
        }
        if(caracterActual=='#'){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            almacenarToken(lexema,Categoria.CADENA_CARACTERES,filaInicio,columnaInicio)

            return true
        }
        else{
            hacerBT(posInicial,filaInicio,columnaInicio)
            return false
        }

    }
    return false
}

    /*
    verifica si el lexema es un terminal
     */
    fun esTerminal():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual


        if(caracterActual==';'){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.TERMINAL,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false

    }

    /*
    metodo que se encarga de verificar si es un separador
     */
    fun esSeparador():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual

        if(caracterActual==','){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.SEPARADOR,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
       return false

    }

/*
metodo que verificar si es un operador de asignacion
 */
    fun esOperadorAsignacion():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        var posInicial= posicionActual
        if(caracterActual=='='){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicio,columnaInicio)
            return true
        }
       else if(caracterActual=='+' || caracterActual=='-' || caracterActual=='*' || caracterActual=='/' || caracterActual=='%'){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if(caracterActual=='='){
                lexema+=caracterActual
almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicio,columnaInicio)
                obtenerSiguienteCatacter()
                return true
            }else{
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
        }
return false
    }

    /*
    metodo que se encarga de verificar operadores logicos
     */
    fun esOperadorLogico():Boolean{
        if(caracterActual=='&'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial= posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if(caracterActual=='&'){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
                almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicio,columnaInicio)
                return true
            }else {
            hacerBT(posInicial,filaInicio,columnaInicio)
return false
            }
           return false
        }else if(caracterActual=='|'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial= posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()

            if(caracterActual=='|') {
                lexema += caracterActual
                obtenerSiguienteCatacter()
                almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicio,columnaInicio)
                return true
            }
            hacerBT(posInicial,filaInicio,columnaInicio)
            return false
        }
        return false
    }

    /*
    metodo que se encarga si verificar operadores relacionales
     */
    fun esOperadorRelacional():Boolean{

        if(caracterActual=='<' || caracterActual== '>'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial= posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if(caracterActual=='='){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
            }

            almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicio,columnaInicio)
            return true
        }else if(caracterActual=='<' || caracterActual=='>'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial= posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()

            if(caracterActual=='='){

               hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicio,columnaInicio)
            return true

        }else if(caracterActual=='='){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            lexema+=caracterActual
            var posInicial= posicionActual
            obtenerSiguienteCatacter()
             if(caracterActual=='='){
                 lexema+=caracterActual
                 obtenerSiguienteCatacter()
                 almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicio,columnaInicio)
                 return true
             }
            else{
                 hacerBT(posInicial,filaInicio,columnaInicio)
                 return false
             }

        }
        else if(caracterActual=='!'){
            var lexema=""
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            var posInicial= posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()
            if (caracterActual=='='){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicio,columnaInicio)
                return true
            }
            else{
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
        }
return false
    }

    /*
    metodo que verificar si es un operador aritmetico
     */
    fun esOperadorAritmetico():Boolean{

        if(caracterActual=='+'){
            var lexema="";
            var posInicial =posicionActual
            lexema+=caracterActual
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            obtenerSiguienteCatacter()

            if(caracterActual=='+' || caracterActual == '='){
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicio,columnaInicio)
            return true
        }else if(caracterActual=='-'){
            var lexema=""
            lexema+=caracterActual
            var posInicial=posicionActual
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            obtenerSiguienteCatacter()

            if(caracterActual=='-' || caracterActual=='='){
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicio,columnaInicio)
            return true
        }else if(caracterActual=='/'){
            var lexema=""
            lexema+=caracterActual
            var posInicial=posicionActual
            var filaInicio= filaActual
            var columnaInicio= columnaActual
            obtenerSiguienteCatacter()
            if(caracterActual=='/' || caracterActual == '=' || caracterActual == '*'){
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicio,columnaInicio)
            return true
        }
        else if(caracterActual=='*'){
            var lexema=""
            lexema+=caracterActual
            var posInicial=posicionActual
            var filaInicio=filaActual
            var columnaInicio= columnaActual
            obtenerSiguienteCatacter()

            if(caracterActual=='='){
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicio,columnaInicio)
            return true
        }else if(caracterActual=='%'){
            var lexema=""
            lexema+=caracterActual
            var posInicial=posicionActual
            var filaInicio=filaActual
            var columnaInicio=columnaActual
            obtenerSiguienteCatacter()

            if(caracterActual=='='){
                hacerBT(posInicial,filaInicio,columnaInicio)
                return false
            }
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicio,columnaInicio)
            return true
        }
return false
    }
    /*
    metodo que analiza llave izquierda
     */
    fun esLLaveIzquierda():Boolean{
        var lexema=""
    var filaInicio=filaActual
    var columnaInicio=columnaActual
    if(caracterActual=='{'){
        lexema+=caracterActual
        almacenarToken(lexema,Categoria.LLAVE_IZQUIERDA,filaInicio,columnaInicio)
        obtenerSiguienteCatacter()
        return true
    }
    return false
}

    /*
   metodo que analiza llave derecha
    */
    fun esLLaveDerecha():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        if(caracterActual=='}'){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.LLAVE_DERECHA,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false
    }

    /*
    metodo para identificar si es entero
     */
    fun esEntero():Boolean{

        if (caracterActual.isDigit()){
            var lexema = ""
            var filainicial = filaActual
            var columnainicial = columnaActual
            var posicionInicial = posicionActual
            lexema+=caracterActual
            obtenerSiguienteCatacter()

            while (caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCatacter()
            }
            if(caracterActual == '.'){
                hacerBT(posicionInicial, filainicial, columnainicial)
                return false
            }
            almacenarToken(lexema,
                Categoria.ENTERO,filainicial, columnainicial)
            return true
        }
        return false
    }

    /*
    metodo para verificar si es identificador
     */
    fun esIdentificador():Boolean {

        if (caracterActual=='$' || caracterActual=='_' || caracterActual.isLetter()){
            var palabra=""
            palabra+=caracterActual
            var filaInicio=filaActual
            var columnaInicio=columnaActual

            obtenerSiguienteCatacter()

            while (caracterActual.isDigit() || caracterActual=='$' || caracterActual== '_' || caracterActual.isLetter()){

                palabra+=caracterActual
                obtenerSiguienteCatacter()
            }

            if(esPalabraReservada(palabra)){
                almacenarToken(palabra, Categoria.PALABRA_RESERVADA, filaInicio, columnaInicio)
                return true
            }
            else {
                almacenarToken(palabra, Categoria.IDENTIFICADOR, filaInicio, columnaInicio)
                return true
            }
        }

return false

    }
fun esReal():Boolean{

    if(!caracterActual.isDigit() && caracterActual == '.'){
        return false
    }

    var lexema:String=""
    var posInicial=posicionActual

    var filaInicio=filaActual
    var columnaInicio=columnaActual

    if(caracterActual=='.'){
        lexema+=caracterActual
        obtenerSiguienteCatacter()

        if(!caracterActual.isDigit()){
            hacerBT(posInicial,filaInicio,columnaInicio)
            return false
        }else{
            lexema+=caracterActual;
            obtenerSiguienteCatacter()
        }
    }else{
        lexema+=caracterActual
        obtenerSiguienteCatacter()
        while (caracterActual.isDigit()){
            lexema+=caracterActual
            obtenerSiguienteCatacter()
        }
        if(caracterActual!='.'){
            hacerBT(posInicial,filaInicio,columnaInicio)
            return false
        }else{
            lexema+=caracterActual
            obtenerSiguienteCatacter()
        }
    }
    while (caracterActual.isDigit()){
        lexema+=caracterActual
        obtenerSiguienteCatacter()
    }
    almacenarToken(lexema,Categoria.REAL,filaInicio,columnaInicio)
    return true
}
    /*
    metodo para identificar si es decimal
     */
    fun esDecimal():Boolean{
        if (caracterActual == '.' || caracterActual.isDigit()){
            var lexema = ""
            var filainicial = filaActual
            var columnainicial = columnaActual
            var posicionInicial=posicionActual

            if (caracterActual == '.'){
                lexema += caracterActual
                obtenerSiguienteCatacter()

                if (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCatacter()
                }
                else
                {
                    hacerBT(posicionInicial,filainicial,columnainicial)
                    return false
                }
            }else{
                lexema += caracterActual
                obtenerSiguienteCatacter()
                while (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCatacter()
                }
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCatacter()
                }
            }
            while (caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCatacter()
            }
            almacenarToken(lexema,
                Categoria.DECIMAL,filainicial,columnainicial)
            return true
        }
        return false
    }

    fun esParentesisIzquierdo():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        if(caracterActual=='('){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.PARENTECIS_IZQUIERDO,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false
    }

    fun esParentesisDerecho():Boolean{
        var lexema=""
        var filaInicio=filaActual
        var columnaInicio=columnaActual
        if(caracterActual==')'){
            lexema+=caracterActual
            almacenarToken(lexema,Categoria.PARENTECIS_DERECHO,filaInicio,columnaInicio)
            obtenerSiguienteCatacter()
            return true
        }
        return false
    }


    /*
    metodo para moverse en el codigo fuente
     */
    fun obtenerSiguienteCatacter(){

    if(posicionActual == codigoFuente.length-1){
        caracterActual = finCodigo
      }else{

        if (caracterActual == '\n'){
            filaActual++
            columnaActual = 0
        }else{
            columnaActual++
         }
          posicionActual++
          caracterActual = codigoFuente[posicionActual]
       }

    }

    /*
    metodo para hacer back tracking
     */
    fun hacerBT( posicionInicial:Int,  filaInicial:Int,  columnainicial:Int){

        posicionActual=posicionInicial
        filaActual=filaInicial
        columnaActual=columnainicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun esPalabraReservada(lexema:String):Boolean{

        return palabrasReservadas.contains(lexema)
    }

}