package co.edu.uniquindio.compiladores.semantica
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo

class TablaSimbolos( var listaErrores: ArrayList<Error>) {

    var listaSimbolos: ArrayList<Simbolo> = ArrayList()


    /**
     * Permite guardar un simbolo de tipo constante, parametro, vatiable o arreglo
     */
    fun guardarSimboloValor(nombre: String, tipoDato: String, ambito: String, fila: Int, columna: Int){

        var s= buscarSimboloValor(nombre,ambito)

        if(s==null) {
            listaSimbolos.add(
                Simbolo(
                    nombre,
                    tipoDato,
                    ambito,
                    fila,
                    columna
                )
            )
        }else{

            listaErrores.add(Error("El campo $nombre ya existe en el ámbito $ambito", fila, columna))

        }
    }

    /**
     * Permite guardar un simbolo de tipo funcion
     */
    fun guardarSimboloFuncion(nombre: String, tipoRetorno: String, tiposParametros: ArrayList<String>, ambito: String, fila: Int, columna: Int){

        var s=buscarSimboloFuncion(nombre, tiposParametros)
        if (s==null){
            listaSimbolos.add(
                Simbolo(
                    nombre,
                    tipoRetorno,
                    tiposParametros,
                    ambito
                )
            )
        }else{
            listaErrores.add(Error("La funcion $nombre ya existe en el ámbito $ambito", fila, columna))
        }
    }

    /**
     * permite buscar un valor en la tabla de simbolos
     */
    fun buscarSimboloValor(nombre:String,ambito:String): Simbolo? {
        for (s in listaSimbolos) {
            if (s.tiposParametros == null){
                if (s.nombre == nombre && s.ambito == ambito) {
                    return s
                }
        }
    }
        return null
    }
    /**
     * permite buscar una funcion en la tabla de simbolos
     */
    fun buscarSimboloFuncion(nombre:String,tiposParametros: ArrayList<String>): Simbolo?{
        for(s in listaSimbolos) {
            if (tiposParametros!=null) {
                if (s.nombre == nombre && s.tiposParametros == tiposParametros) {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}