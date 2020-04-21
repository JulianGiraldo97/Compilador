package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error

class AnalizadorSintactico (var listaTokens:ArrayList<Token>){

    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiquienteToken(){
        posicionActual++;
        if (posicionActual < listaTokens.size){
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje:String){
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna ))
    }

    /**
     * <UnidadDeCompilacion> ::= <listaFunciones>
     */
    fun esUnidadDeCompilacion (): UnidadDeCompilacion?{
        val listaFunciones:ArrayList<Funcion> = esListaFunciones()
        return if(listaFunciones.size > 0){
            UnidadDeCompilacion(listaFunciones)
        }else null
    }

    /**
     * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion>{
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion != null){
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion>::= fun idMetodo <TipoRetorno> "("[<ListaParametros>]")""{"<listaSentencias>"}"
     *
     */
    fun esFuncion():Funcion?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "fun"){
          obtenerSiquienteToken()

            if(tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombreFuncion = tokenActual
                obtenerSiquienteToken()
                var tipoRetorno = esTipoRetorno()
                if (tipoRetorno != null) {
                    obtenerSiquienteToken()
                    if(tokenActual.categoria == Categoria.PARENTECIS_IZQUIERDO){
                        obtenerSiquienteToken()
                        var listaParametros = esListaParametros()
                        if(tokenActual.categoria == Categoria.PARENTECIS_DERECHO){
                            obtenerSiquienteToken()
                           if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA){
                               obtenerSiquienteToken()
                               var listaSentencias = esBloqueSentencias()
                               if(listaSentencias != null){
                                  obtenerSiquienteToken()
                                   if (tokenActual.categoria == Categoria.LLAVE_DERECHA){
                                       obtenerSiquienteToken()
                                       return Funcion(nombreFuncion, tipoRetorno, listaParametros, listaSentencias)
                                   }else{
                                       reportarError("falta llave derecha")
                                   }
                               }else{
                                   reportarError("sentencias vacias")
                               }
                            }else{
                               reportarError("falta llave izquierda")
                           }
                        }else{
                            reportarError("falta parentecis derecho")
                        }
                    }else{
                        reportarError("falta parentecis izquierdo")
                    }

                } else {
                    reportarError("falta tipo de retorno en la funcion")
                }
            }else{
                reportarError("falta el nombre de la función")
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= Entero|Real|Cadena|Caracter|Logico|NoRetorno
     */
    fun esTipoRetorno():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "Entero" || tokenActual.lexema == "Real" || tokenActual.lexema == "Cadena"
                || tokenActual.lexema == "Caracter" || tokenActual.lexema == "Logico" || tokenActual.lexema == "NoRetorno"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros>::=<Parametro> [","<ListaParametros>]
     */
    fun esListaParametros():ArrayList<Parametro>{
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        while (parametro != null){
            listaParametros.add(parametro)
            if(tokenActual.categoria == Categoria.SEPARADOR){
                obtenerSiquienteToken()
                parametro = esParametro()
            }else {
                if(tokenActual.categoria != Categoria.PARENTECIS_DERECHO) {
                    reportarError("falta un separador en la lista de parametros")
                }
                break
            }


        }
        return listaParametros
    }

    /**
     *<Parametro>::=  <TipoDato> identificador
     */
    fun esParametro():Parametro?{
        val tipoDato = esTipoDato()
        if(tipoDato != null){
            obtenerSiquienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR){
                val nombre = tokenActual
                obtenerSiquienteToken()
                return Parametro(nombre,tipoDato)
            }else{
                reportarError("falta identificador")
            }
        }else{
            reportarError("falta tipo de dato")
        }
        return null
    }

    /**
     * <TipoDato> ::= Entero|Real|Cadena|Caracter|Logico|NoRetorno
     */
    fun esTipoDato():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "Entero" || tokenActual.lexema == "Real" || tokenActual.lexema == "Cadena"
                || tokenActual.lexema == "Caracter" || tokenActual.lexema == "Logico"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaSentencias>::="{"[<ListaSentencias>]"}"
     */
    fun esBloqueSentencias():ArrayList<Sentencia>?{
        if(tokenActual.categoria == Categoria.LLAVE_IZQUIERDA){
            obtenerSiquienteToken()
            var listaSentencias = esListaSentencias()

            if(tokenActual.categoria == Categoria.LLAVE_DERECHA){
                obtenerSiquienteToken()
                return listaSentencias
            }else{
                reportarError("falta llave derecha")
            }

        }else{
            reportarError("falta llave izquierda")
        }

        return null
    }

    fun esListaSentencias():ArrayList<Sentencia>{
        return ArrayList()
    }
}