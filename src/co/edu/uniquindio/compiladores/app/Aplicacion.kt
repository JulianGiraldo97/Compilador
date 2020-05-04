package co.edu.uniquindio.compiladores.app

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintactico.AnalizadorSintactico
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class Aplicacion : Application() {

    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(Aplicacion::class.java.getResource("/inicio.fxml"))
        val parent:Parent = loader.load()

        val scene = Scene(parent)

        primaryStage?.scene = scene
        primaryStage?.title = "Compílador"
        primaryStage?.show()
    }

    companion object{

        @JvmStatic
        fun main(args: Array<String>){
           launch( Aplicacion::class.java)

           // val lex= AnalizadorLexico("fun nombre Entero(Entero a , Entero b){Entero a;}")
       //    lex.analizar()


          // val sin= AnalizadorSintactico(lex.listaTokens)
            //print(sin.esUnidadDeCompilacion())
           //print(sin.listaErrores)


        }

    }


}