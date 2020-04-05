package co.edu.uniquindio.compiladores.lexico

enum class Categoria {
    ENTERO,
    DECIMAL,
    IDENTIFICADOR,
    OPERADOR_ARITMETICO,
    OPERADOR_LOGICO,
    PARENTECIS_IZQUIERDO,
    PARENTECIS_DERECHO,
    CORCHETE_DERECHO,
    CORCHETE_IZQUIERDO,
    DESCONOCIDO,
    OPERADOR_RELACIONAL,
    INCREMENTO,
    DECREMENTO,
    REAL,
    LLAVE_IZQUIERDA,
    LLAVE_DERECHA,
    OPERADOR_ASIGNACION,
    SEPARADOR,
    TERMINAL,
    CADENA_CARACTERES,
    CARACTER,
    COMENTARIO,
    COMENTARIO_BLOQUE,
    PALABRA_RESERVADA,
    PUNTO,
    DOS_PUNTOS

}