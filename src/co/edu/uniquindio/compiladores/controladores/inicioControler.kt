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
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintactico.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintactico.UnidadDeCompilacion
import javafx.scene.control.*
import java.io.File
import kotlin.system.exitProcess


class inicioControler : Initializable {

    @FXML lateinit var codigoFuente:TextArea

    @FXML lateinit var tablaTokens:TableView<Token>
    @FXML lateinit var tablaErrores:TableView<Error>
    @FXML lateinit var tablaSemantico:TableView<Error>

    @FXML lateinit var columnaLexema:TableColumn<Token, String>
    @FXML lateinit var columnaCategoria:TableColumn<Token, String>
    @FXML lateinit var columnaFila:TableColumn<Token, Int>
    @FXML lateinit var columnaColumna:TableColumn<Token, Int>

    @FXML lateinit var mensajeError:TableColumn<Error, String>
    @FXML lateinit var filaError:TableColumn<Error, Int>
    @FXML lateinit var columnaError:TableColumn<Error, Int>

    @FXML lateinit var mensajeErrorSemantico:TableColumn<Error, String>
    @FXML lateinit var filaErrorSemantico:TableColumn<Error, Int>
    @FXML lateinit var columnaErrorSemantico:TableColumn<Error, Int>

    @FXML lateinit var arbolVisual:TreeView<String>

    private var uc:UnidadDeCompilacion?=null
    private var lexico:AnalizadorLexico?=null
    private var sintaxis:AnalizadorSintactico?=null
    private var semantica:AnalizadorSemantico?=null


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCategoria.cellValueFactory = PropertyValueFactory("categoria")
        columnaFila.cellValueFactory = PropertyValueFactory("fila")
        columnaColumna.cellValueFactory = PropertyValueFactory("columna")
        mensajeError.cellValueFactory = PropertyValueFactory("error")
        filaError.cellValueFactory = PropertyValueFactory("Fila")
        columnaError.cellValueFactory = PropertyValueFactory("Columna")
        mensajeErrorSemantico.cellValueFactory = PropertyValueFactory("error")
        filaErrorSemantico.cellValueFactory = PropertyValueFactory("Fila")
        columnaErrorSemantico.cellValueFactory = PropertyValueFactory("Columna")

    }


    @FXML
    fun analizarCodigo( e : ActionEvent) {

        if (codigoFuente.length > 0) {

            lexico = AnalizadorLexico(codigoFuente.text)
            lexico!!.analizar()
            sintaxis = AnalizadorSintactico(lexico!!.listaTokens)
            uc = sintaxis!!.esUnidadDeCompilacion()
            tablaErrores.items = FXCollections.observableArrayList(sintaxis!!.listaErrores)
            tablaTokens.items = FXCollections.observableArrayList(lexico!!.listaTokens)


            if (uc != null) {
                arbolVisual.root = uc!!.getArbolVisual()
                semantica=AnalizadorSemantico(uc!!)
                semantica!!.llenarTablaSimbolos()
              print(semantica!!.tablaSimbolos)
               // print(semantica.listaErrores)

                semantica!!.analizarSemantica()
              //  print(semantica.listaErrores)
                tablaSemantico.items = FXCollections.observableArrayList(semantica!!.listaErrores)
            }


        }

    }

    @FXML
    fun traducirCodigo(e:ActionEvent) {
        if (lexico!!.listaErrores.isEmpty()&&sintaxis!!.listaErrores.isEmpty()&&semantica!!.listaErrores.isEmpty()) {
            val codigo=uc!!.getJavaCode()
            File("src/Principal.java").writeText(codigo)

            val runtime=Runtime.getRuntime().exec("javac src/Principal.java")
            runtime.waitFor()
            Runtime.getRuntime().exec("java Principal", null, File("src"))

        } else{
            val alerta=Alert(Alert.AlertType.ERROR)
            alerta.headerText=null
            alerta.contentText="El codigo no se puede traducir porque hay errores"
            alerta.show()
        }
    }
}