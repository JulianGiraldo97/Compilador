package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var posicionActual = 0
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0


    /*
    metodo que almacena los tokens para mostrar en la UI
     */
    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(
        Token(lexema, categoria, fila, columna)
    )

    /*
    metodo analizar que llama al resto de metodos del analizar lexico
     */
    fun analizar(){
        while (caracterActual != finCodigo){

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCatacter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue

            almacenarToken(""+caracterActual,
                Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCatacter()
    }
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
    metodo para identificar si es decimal
     */
    fun esDecimal():Boolean{
        if (caracterActual == '.' || caracterActual.isDigit()){
            var lexema = ""
            var filainicial = filaActual
            var columnainicial = columnaActual

            if (caracterActual == '.'){
                lexema += caracterActual
                obtenerSiguienteCatacter()
                if (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCatacter()
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
        posicionActual = filaInicial
        filaActual = filaInicial
        columnaActual = columnaActual
        caracterActual = codigoFuente[posicionActual]
    }

}