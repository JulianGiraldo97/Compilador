package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea


class inicioControler {

    @FXML lateinit var codigoFuente:TextArea

    @FXML
    fun analizarCodigo( e : ActionEvent){

        if (codigoFuente.length > 0){

        val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            print(lexico.listaTokens)
        }
    }
}