package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.sintactico.Argumento

class Simbolo() {

    var nombre: String = ""
    var tipo: String = ""
    var modificable: Boolean = false
    var ambito: String = ""
    var fila: Int = 0
    var columna: Int = 0
    var tiposParametros: ArrayList<String>? = null

    /**
     * constructor para crear un simbolo de tipo valor
     */
    constructor(nombre: String, tipoDato: String, ambito: String, fila: Int, columna: Int) : this() {
        this.nombre = nombre
        this.tipo = tipoDato
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * constructor para crear un simbolo de tipo metodo
     */
    constructor(nombre: String, tipoRetorno: String, tiposParametros: ArrayList<String>, ambito: String) : this() {
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.ambito = ambito
        this.tiposParametros = tiposParametros

    }

    override fun toString(): String {

      return if (tiposParametros == null) {
          "Simbolo(nombre='$nombre', tipo='$tipo', ambito='$ambito', fila=$fila, columna=$columna,"
        }else {
         "Simbolo(nombre='$nombre', tipo='$tipo', ambito='$ambito', tipoParametros=$tiposParametros)"
    }
}

}