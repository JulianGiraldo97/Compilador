package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintactico.AnalizadorSintactico
import javafx.scene.control.*
import kotlin.system.exitProcess


class inicioControler : Initializable {

    @FXML lateinit var codigoFuente:TextArea

    @FXML lateinit var tablaTokens:TableView<Token>
    @FXML lateinit var tablaErrores:TableView<Error>

    @FXML lateinit var columnaLexema:TableColumn<Token, String>
    @FXML lateinit var columnaCategoria:TableColumn<Token, String>
    @FXML lateinit var columnaFila:TableColumn<Token, Int>
    @FXML lateinit var columnaColumna:TableColumn<Token, Int>

    @FXML lateinit var mensajeError:TableColumn<Error, String>
    @FXML lateinit var filaError:TableColumn<Error, Int>
    @FXML lateinit var columnaError:TableColumn<Error, Int>

    @FXML lateinit var arbolVisual:TreeView<String>


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCategoria.cellValueFactory = PropertyValueFactory("categoria")
        columnaFila.cellValueFactory = PropertyValueFactory("fila")
        columnaColumna.cellValueFactory = PropertyValueFactory("columna")
        mensajeError.cellValueFactory = PropertyValueFactory("error")
        filaError.cellValueFactory = PropertyValueFactory("Fila")
        columnaError.cellValueFactory = PropertyValueFactory("Columna")
    }


    @FXML
    fun analizarCodigo( e : ActionEvent) {

        if (codigoFuente.length > 0) {

            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            val sintaxis = AnalizadorSintactico(lexico.listaTokens)
            val uc = sintaxis.esUnidadDeCompilacion()
            tablaErrores.items = FXCollections.observableArrayList(sintaxis.listaErrores)
            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)


            if (uc != null) {
                arbolVisual.root = uc.getArbolVisual()
            }


        }

    }
}