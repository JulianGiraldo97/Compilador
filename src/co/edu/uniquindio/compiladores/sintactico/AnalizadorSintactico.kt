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
     * <Funcion>::= fun idMetodo <TipoRetorno> "("[<ListaParametros>]")""{"<BloqueSentencias>"}"
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
                                if(listaSentencias.size>0){
                                    if (tokenActual.categoria == Categoria.LLAVE_DERECHA){
                                        obtenerSiquienteToken()
                                        return Funcion(nombreFuncion, tipoRetorno, listaParametros, listaSentencias)
                                    }else{
                                        reportarError("Falta llave derecha del bloque de sentencias")
                                    }
                                }else{
                                    reportarError("Sentencias vacias en la funcion")
                                }
                            }else{
                                reportarError("Falta llave izquierda del bloque de sentencias")
                            }
                        }else{
                            reportarError("Falta parentecis derecho")
                        }
                    }else{
                        reportarError("Falta parentecis izquierdo")
                    }

                } else {
                    reportarError("Falta tipo de retorno en la funcion")
                }
            }else{
                reportarError("Falta el nombre de la función")
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= Entero|Real|Cadena|Caracter|Logico|NoRetorno|Verdadero|Falso
     */
    fun esTipoRetorno():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "Entero" || tokenActual.lexema == "Real" || tokenActual.lexema == "Cadena"
                || tokenActual.lexema == "Caracter" || tokenActual.lexema == "Logico" || tokenActual.lexema == "NoRetorno" ||
                tokenActual.lexema == "Falso" || tokenActual.lexema == "Verdadero"){
                return tokenActual
            }
        }
        return null
    }

    fun esListaParametros1():ArrayList<Parametro>{

        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        if(parametro!=null){
            listaParametros.add(parametro)

            if(tokenActual.categoria==Categoria.SEPARADOR){
                obtenerSiquienteToken()
              listaParametros.addAll(esListaParametros1())
            }
            else{
                reportarError("Falta separador")
            }
        }
return listaParametros
    }

    /**
     *<Parametro>::=  <TipoDato> identificador
     */
    fun esParametro():Parametro?{
        if(tokenActual.categoria==Categoria.PARENTECIS_DERECHO) {
            return null
        }
            else {
            val tipoDato = esTipoDato()
            if (tipoDato != null) {
                obtenerSiquienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    val nombre = tokenActual
                    obtenerSiquienteToken()
                    return Parametro(nombre, tipoDato)
                } else {
                    reportarError("falta identificador")
                }
            } else {
                reportarError("falta tipo de dato")
            }
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
     * <BloqueSentencias>::=[<ListaSentencias>]
     */
    fun     esBloqueSentencias():ArrayList<Sentencia>{

        return esListaSentencias()

    }

    /**
     *<ListaSentencias>::=<Sentencia>[<ListaSentencias>]
     */

    fun esListaSentencias():ArrayList<Sentencia>{
       val lista= ArrayList<Sentencia>()
        var sentencia= esSentencia()
        while (sentencia!=null){
            lista.add(sentencia)
            sentencia=esSentencia();
        }
        return lista
    }
    /**
     * <Sentencia>::=<Condicional>|<Ciclo>|[<Retorno>]|<DeclaracionCampo>|<AsignacionVariable>|<Impresion>|<Lectura>|<InvocacionMetodo>
     */
    fun esSentencia():Sentencia?{

var s:Sentencia?=esDeclaracionCampo()
        if(s!=null){
           return s
        }
        s=esDeclaracionArreglo()
        if(s!=null){
            return s
        }

        s=esRetorno()
        if(s!=null){
            return s;
        }

        s=esInvocacionMetodo()
        if(s!=null){
            return s
        }
        s=esLectura()
        if (s!=null){
            return s
        }
        s=esImpresion()
        if (s!=null){
            return s
        }

        s=esCondicional()
        if (s!=null){
            return s
        }

        s=esCiclo()
        if(s!=null){
            return s
        }

        s=esAsignacionVariable()
        if (s!=null){
            return s
        }
        s=esAsignacionArreglo()
        if (s!=null){
            return s
        }

return null
    }

    /**
     * <Condicional>::= SiSeCumple "("<ExpresionLogica ")" "{"<ListaSentencias>"}" [ DeLoContrario "{"<ListaSentencias>"}"]

     */
fun esCondicional():Condicional?{
if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="SiSeCumple"){
   obtenerSiquienteToken()
    if(tokenActual.categoria==Categoria.PARENTECIS_IZQUIERDO){
        obtenerSiquienteToken()
        var expresion=esExpresionCondicion()
        if(expresion!=null){

            if(tokenActual.categoria==Categoria.PARENTECIS_DERECHO){
                obtenerSiquienteToken()
                if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){

                    obtenerSiquienteToken()

                    var lista=esBloqueSentencias()

                    if(lista.size>0){
                        if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                            obtenerSiquienteToken()

                            if (tokenActual.categoria==Categoria.PALABRA_RESERVADA
                                && tokenActual.lexema=="DeLoContrario"){

                                obtenerSiquienteToken()
                                if (tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){

                                    obtenerSiquienteToken()
                                    var listaSiNo=esBloqueSentencias()
                                    if(listaSiNo.size>0){

                                        if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                            obtenerSiquienteToken()

                                            return Condicional(expresion,lista,listaSiNo)
                                        }
                                        else{
                                            reportarError("Falta la llave derecha")
                                        }
                                    }else{
                                        reportarError("Faltan las sentencias")
                                    }
                                }else{
                                    reportarError("Falta la llave izquierda")
                                }
                            }else{
                                return Condicional(expresion,lista,null)
                            }
                        }else{
                            reportarError("Falta la llave derecha")
                        }
                    }else{
                        reportarError("Faltan las sentencias")
                    }

                }else{
                    reportarError("Falta la llave izquierda")
                }
            }else{
                reportarError("Falta parentesis derecho")
            }
        }else{
            reportarError("Error en la expresion logica")
        }
    }else{
        reportarError("Falta parentesis izquierdo")
    }
}
        return null
}

    /**
     * <ExpresionCadena> ::= Cadena ["+" <ExpresionCadena>]
     */
    fun esExpresionCadena(): ExpresionCadena?{
       if(tokenActual.categoria==Categoria.CADENA_CARACTERES){

           var cadena=tokenActual

           obtenerSiquienteToken()

           if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO && tokenActual.lexema=="+"){
               obtenerSiquienteToken()
               var exp=esExpresionCadena()
               if (exp!=null){
                   return ExpresionCadena(cadena,exp)
               }
           }else{
               return ExpresionCadena(cadena)
           }
       }
        return null
    }
    /**
     * <ExpresionRelacional>::=<ExprecionAritmetica> OperadorRelacional <ExpresionAritmetica>
     */
    fun esExpresionRelacional():ExpresionRelacional?{

        val exp=esExpresionAritmetica()
        if(exp!=null){
            if (tokenActual.categoria==Categoria.OPERADOR_RELACIONAL){
                var operador=tokenActual
                obtenerSiquienteToken()

                val exp2=esExpresionAritmetica()
                if (exp2!=null){

                    return ExpresionRelacional(exp,operador,exp2)
                }else{
                    reportarError("Falta expresion aritmetica")
                }
            }
        }
        return null
    }

    /**
     * <ExpresionCondicion>::= <ExpresionRelacional>" [OperadorLogico <ExpresionLogica>] | <ExpresionLogica>
     */
    fun esExpresionCondicion():Expresion?{


        var exp3=esExpresionRelacional()

        if(exp3!=null){
            if (tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                val operador3=tokenActual
                obtenerSiquienteToken()
                val exp4=esExpresionLogica()
                if(exp4!=null){
                    return ExpresionLogica(exp3,operador3,exp4)
                }
            }
            else{

                return ExpresionLogica(exp3)
            }


        }
        else {
            var exp=esExpresionLogica()
            if (exp!=null){
                return exp
            }
        }
return null
    }

    /**
     * <ExpresionLogica> ::= operadorNegacion <ExpresionLogica> [operadorLogico <ExpresionLogica>] | <Termino>  [OperadorLogico <ExpresionLogica>] |
     * */
    fun esExpresionLogica():ExpresionLogica?{

        var termino=esTermino()
        if (termino!=null){
            obtenerSiquienteToken()
            if (tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                var operador4=tokenActual
                obtenerSiquienteToken()
                var exp5=esExpresionLogica()
                if (exp5!=null){
                    return ExpresionLogica(termino,operador4,exp5)
                }

            }else{
                return ExpresionLogica(termino)

            }

        }

      else if(tokenActual.categoria==Categoria.OPERADOR_NEGACION){

            val operador1=tokenActual
            obtenerSiquienteToken()

            val exp1=esExpresionLogica()
            if (exp1!=null){
                if(tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                    val operador2=tokenActual
                    obtenerSiquienteToken()
                    val exp2=esExpresionLogica()
                    if(exp2!=null){
                        return ExpresionLogica(operador1,exp1,operador2,exp2)
                    }
                }else{
                    return ExpresionLogica(operador1,exp1)
                }
            }
        }

        return null
    }



    /**
     * <DeclaracionArreglo> ::= Lista <tipoDato> identificador "{" "}" ";"
     */
    fun esDeclaracionArreglo():DeclaracionArreglo?{

        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Lista") {

            obtenerSiquienteToken()
            var tipoDato = esTipoDato()
            if (tipoDato != null) {
                obtenerSiquienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var id = tokenActual
                    obtenerSiquienteToken()
                    if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                        obtenerSiquienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                            obtenerSiquienteToken()
                            if (tokenActual.categoria == Categoria.TERMINAL) {

                                obtenerSiquienteToken()
                                return DeclaracionArreglo(tipoDato, id)
                            }
                            else{
                                reportarError("Falta el terminal de la sentencia")
                            }
                        }else{
                            reportarError("Falta la llave derecha")
                        }
                    }else
                        reportarError("Falta la llave izquierda")
                }else{
                    reportarError("Falta el identificador del arreglo")
                }
            }
            else{
                reportarError("Falta el tipo de dato en el arreglo")
            }
        }
        return null
    }

    /**
     * <DeclaracionCampo> ::= <tipoDato> identificador ";"
     */
    fun esDeclaracionCampo():DeclaracionCampo?{
        var tipoDato= esTipoDato()
        if (tipoDato!=null){
            obtenerSiquienteToken()
            if(tokenActual.categoria==Categoria.IDENTIFICADOR){
                var id=tokenActual
                obtenerSiquienteToken()
                if(tokenActual.categoria==Categoria.TERMINAL){
                    obtenerSiquienteToken()
                    return DeclaracionCampo(tipoDato,id)
                }
                else{
                    reportarError("Falta el final de la sentencia")
                }

            }
            else{
                reportarError("Falta el identificador de la declaracion")
            }

        }
        return null
    }

    /**
     * <Retorno>::= Retornar idVariable";"
     */

    fun esRetorno():Retorno?{
    if(tokenActual.lexema=="Retornar" && tokenActual.categoria==Categoria.PALABRA_RESERVADA){
        obtenerSiquienteToken()
        var termino=esTermino()
        if(termino!=null){
            obtenerSiquienteToken()
            if(tokenActual.categoria==Categoria.TERMINAL){
                obtenerSiquienteToken()
                return Retorno(termino)
            }
            else{
                reportarError("Falta el final de la sentencia")
            }
        }else{
            reportarError("Falta el termino a retornar")
        }

    }
return null
    }

    /**
     * <Termino>::=Entero|Real|Identificador|CadenaCaracteres|Decimal|Caracter
     */
    fun esTermino():Termino?{

        if(tokenActual.categoria==Categoria.ENTERO || tokenActual.categoria==Categoria.DECIMAL ||
            tokenActual.categoria==Categoria.CARACTER || tokenActual.categoria==Categoria.REAL ||
            tokenActual.categoria==Categoria.CADENA_CARACTERES || tokenActual.categoria==Categoria.IDENTIFICADOR
            || (tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Falso") ||
            (tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Verdadero")){
             return Termino(tokenActual)
        }
return null
    }


    /**
     * <InvocacionMetodo>::= "¬" Identificador "("[<ListaArgumentos>]")"";"
     */
    fun esInvocacionMetodo():InvocacionMetodo?{

        if(tokenActual.categoria==Categoria.CARACTER_INVOCACION) {
            obtenerSiquienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombre = tokenActual
                obtenerSiquienteToken()
                if (tokenActual.categoria == Categoria.PARENTECIS_IZQUIERDO) {
                    obtenerSiquienteToken()
                    var arg = esListaArgumentos1()
                    if (tokenActual.categoria == Categoria.PARENTECIS_DERECHO) {
                        obtenerSiquienteToken()
                        if (tokenActual.categoria == Categoria.TERMINAL) {
                            obtenerSiquienteToken()
                            return InvocacionMetodo(nombre, arg)
                        } else {
                            reportarError("Falta fin de sentencia")
                        }
                    } else {
                        reportarError("Falta parentesis derecho")
                    }

                }else{
                    reportarError("Falta parentesis izquierdo")
                }
            }else{
                reportarError("Falta el identificador del metodo")
            }
        }
return null
    }
    /**
     * <ListaArgumentos>::=<Argumento> [<ListaArgumentos>]
     */
    fun esListaArgumentos():ArrayList<Argumento>{
        var lista=ArrayList<Argumento>()
        var argumento=esArgumento()
        if(argumento!=null){
            lista.add(argumento)
            if(tokenActual.categoria==Categoria.SEPARADOR){
                obtenerSiquienteToken()
                lista.addAll(esListaArgumentos())
            }
        }
return lista
    }

    /**
     * <ListaArgumentos>::=<Argumento> [<ListaArgumentos>]
     */
    fun esListaArgumentos1():ArrayList<Argumento>{
        var lista=ArrayList<Argumento>()
        var argumento=esArgumento()
        while(argumento!=null){
            lista.add(argumento)
            if(tokenActual.categoria == Categoria.SEPARADOR){
                obtenerSiquienteToken()
                argumento = esArgumento()
            }else {

                if(tokenActual.categoria != Categoria.PARENTECIS_DERECHO) {
                    reportarError("Falta separador en la lista de argumentos")

                }
                break
            }
        }
        return lista
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
                    reportarError("Falta separador en la lista de parametros")

                }
                break
            }


        }

        return listaParametros
    }

    /**
     * <Argumento>::= <Termino>
     */
     fun esArgumento():Argumento?{
        var termino=esTermino()
    if(termino!=null){
        obtenerSiquienteToken()
        return Argumento(termino)
    }
    return null
}

    /**
     * <AsignacionArreglo>::= Agregar identificador "=" "{" <ListaArgumentos> "}" ";"
     */
    fun esAsignacionArreglo():AsignacionArreglo?{
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Agregar") {
            obtenerSiquienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombre = tokenActual
                obtenerSiquienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.lexema=="=") {
                    obtenerSiquienteToken()
                    if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA) {
                      obtenerSiquienteToken()
                        var argumentos = esListaArgumentos()
                        if (argumentos != null) {
                            if (tokenActual.categoria==Categoria.LLAVE_DERECHA) {
                                obtenerSiquienteToken()
                                if (tokenActual.categoria == Categoria.TERMINAL) {
                                    obtenerSiquienteToken()
                                    return AsignacionArreglo(nombre, argumentos)
                                }else{
                                    reportarError("Falta el final de la sentencia")
                                }
                            }else{
                                reportarError("Falta el la llave derecha")
                            }
                        }else{
                            reportarError("Faltan los argumento del arreglo")
                        }
                    }else{
                        reportarError("Falta la llave izquierda")
                    }
                }else{
                    reportarError("Falta el operador de asignacion")
                }
            }else{
                reportarError("Falta el identificador del arreglo")
            }
        }
        return null
    }

    /**
     * <AsignacionVariable>::= identificador operadorAsignacion <Expresion> ";"
     */
    fun esAsignacionVariable():AsignacionVariable?{

        if(tokenActual.categoria==Categoria.IDENTIFICADOR){
            var nombre=tokenActual
            obtenerSiquienteToken()
            if (tokenActual.categoria==Categoria.OPERADOR_ASIGNACION){
                obtenerSiquienteToken()

                var expresion=esExpresion()
                if(expresion!=null){

                    if(tokenActual.categoria==Categoria.TERMINAL){
                        obtenerSiquienteToken()
                        return AsignacionVariable(nombre,expresion)
                    }else{
                        reportarError("Falta el final de la sentencia en la asignacion")
                    }
                }else{
                    reportarError("Error en la expresion")
                }
            }
        }
return null
    }

    /**
     * <Expresion>::=<ExpresionAritmetica>|<ExpresionLogica>|<ExpresionRelacional>|<ExpresionCadena>
     */
     fun esExpresion():Expresion?{

        var postoken=posicionActual
        var exp:Expresion?=esExpresionAritmetica()

        if(tokenActual.categoria!=Categoria.OPERADOR_RELACIONAL){
            if (exp!=null){
                return exp
            }
        }else{
            hacerBT(postoken)
        }


         exp=esExpresionRelacional()
        if(tokenActual.categoria!=Categoria.OPERADOR_LOGICO && tokenActual.categoria!=Categoria.OPERADOR_NEGACION) {
            if (exp != null) {
                return exp
            }
        }else{
            hacerBT(postoken)
        }

         exp=esExpresionCadena()
        if (exp!=null){
            return exp
        }
        exp=esExpresionLogica()
        if (exp!=null){
            return exp

        }

        return null
    }

    /**
     * <ExpAritmetica> ::= "("<ExpAritmetica>")" [operadorAritmetico <ExpAritmetica>] |
    <ValorNumerico> [operadorAritmetico <ExpAritmetica>]
     */
    fun esExpresionAritmetica():ExpresionAritmetica?{
        if(tokenActual.categoria==Categoria.PARENTECIS_IZQUIERDO){
            obtenerSiquienteToken()
            val exp1=esExpresionAritmetica()
            if (exp1!=null){
                if (tokenActual.categoria==Categoria.PARENTECIS_DERECHO){
                    obtenerSiquienteToken()
                    if (tokenActual.categoria==Categoria.OPERADOR_ARITMETICO){
                        val operador=tokenActual
                        obtenerSiquienteToken()

                        val exp2=esExpresionAritmetica()
                        if (exp2!=null){
                            return ExpresionAritmetica(exp1, operador, exp2)
                        }

                    }else{
                        return ExpresionAritmetica(exp1)
                    }
                }
            }
        }else{
            val valor=esValorNumerico()
            if (valor!=null){
                obtenerSiquienteToken()
                if (tokenActual.categoria==Categoria.OPERADOR_ARITMETICO){
                    val operador=tokenActual
                    obtenerSiquienteToken()

                    val exp=esExpresionAritmetica()
                    if (exp!=null){
                        return ExpresionAritmetica(valor, operador, exp)
                    }

                }else{
                    return ExpresionAritmetica(valor)
                }
            }
        }


return null
    }

    /**
     * <Lectura>::= Leer "(" Mensaje ")" ";"
     */
    fun esLectura():Lectura?{
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Leer"){
            obtenerSiquienteToken()
            if(tokenActual.categoria==Categoria.PARENTECIS_IZQUIERDO){
                obtenerSiquienteToken()
                if(tokenActual.categoria==Categoria.IDENTIFICADOR){
                    var nombre=tokenActual
                    obtenerSiquienteToken()
                    if(tokenActual.categoria==Categoria.PARENTECIS_DERECHO){
                        obtenerSiquienteToken()
                        if(tokenActual.categoria==Categoria.TERMINAL){
                            obtenerSiquienteToken()
                            return Lectura(nombre)
                        }else{
                            reportarError("Falta el final de la sentencia")
                        }

                    }else{
                        reportarError("Falto parentesis derecho")
                    }
                }else{
                    reportarError("Falto el elemento a leer")
                }
            }else{
                reportarError("Falto parentesis izquierdo")
            }
        }
return null
    }

    /**
     * <Imprimir>::= Imprimir "(" Mensaje ")" ";"
     */
    fun esImpresion():Impresion?{
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Imprimir"){
            obtenerSiquienteToken()
            if(tokenActual.categoria==Categoria.PARENTECIS_IZQUIERDO){
                obtenerSiquienteToken()
                if(tokenActual.categoria==Categoria.IDENTIFICADOR){
                    var nombre=tokenActual
                    obtenerSiquienteToken()
                    if(tokenActual.categoria==Categoria.PARENTECIS_DERECHO){
                        obtenerSiquienteToken()
                        if(tokenActual.categoria==Categoria.TERMINAL){
                            obtenerSiquienteToken()
                            return Impresion(nombre)
                        }else{
                            reportarError("Falta el final de la sentencia")
                        }

                    }else{
                        reportarError("Falto parentesis derecho")
                    }
                }else{
                    reportarError("Falto el elemento a leer")
                }
            }else{
                reportarError("Falto parentesis izquierdo")
            }
        }
        return null
    }
    /**
     * <Ciclo>::= Mientras "("<ExpresionLogica ")" "{"<ListaSentencias"}"
     */
    fun esCiclo():Ciclo?{
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema=="Mientras"){
            obtenerSiquienteToken()
            if(tokenActual.categoria==Categoria.PARENTECIS_IZQUIERDO){
                obtenerSiquienteToken()

                var expresion=esExpresionCondicion()

                if(expresion!=null){

                    if(tokenActual.categoria==Categoria.PARENTECIS_DERECHO){
                        obtenerSiquienteToken()
                        if (tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                            obtenerSiquienteToken()
                            var sentencia=esBloqueSentencias()
                            if (sentencia.size>0){
                                if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                    return Ciclo(expresion,sentencia)

                                }
                                else{
                                    reportarError("Falta llave derecha")
                                }
                            }else{
                                reportarError("Faltan las sentencias en el ciclo")
                            }
                        }else{
                            reportarError("Falta llave izquierda")
                        }
                    }else{
                        reportarError("Falta parentesis derecho")
                    }

                }else{
                    reportarError("Error en la expresion")
                }
            }else{
                reportarError("Falta parentesis izquierdo")
            }
        }

return null

    }

    /**
     * <ValorNumerico>::= [<Signo>] decimal | [<Signo>] entero | [<Signo>] identificador
     */
    fun esValorNumerico():ValorNumerico?{
        var signo:Token?=null
        if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema=="+" ||
                    tokenActual.lexema=="-")){
signo=tokenActual
            obtenerSiquienteToken()
        }
        if (tokenActual.categoria==Categoria.ENTERO || tokenActual.categoria==Categoria.DECIMAL ||
            tokenActual.categoria==Categoria.IDENTIFICADOR) {
            val termino = tokenActual
return  ValorNumerico(signo,termino)
        }
        return null
    }

    fun hacerBT(pos:Int){
     posicionActual=pos
        if (pos<listaTokens.size){
            tokenActual=listaTokens.get(pos)

        }
    }
}