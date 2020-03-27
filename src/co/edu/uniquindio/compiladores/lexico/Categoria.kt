package co.edu.uniquindio.compiladores.lexico

enum class Categoria {
    ENTERO,
    DECIMAL,
    IDENTIFICADOR,
    OPERADOR_ARITMETICO,
    OPERADOR_LOGICO,
    PARENTECIS_IZQUIERDO,
    PARENTECIS_DERECHO,
    DESCONOCIDO
}