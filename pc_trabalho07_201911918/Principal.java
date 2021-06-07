/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 31/05/2021
* Ultima alteracao: 06/06/2021
* Nome: Principal.java
* Funcao: Iniciar minha interface grafica para resolver o problema dos filosofos (ou o problema do benders, espero que tenha assistido futurama :D)
*************************************************************** */
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.PrincipalController;
import models.threads.Bender;
import models.util.Paths;

public class Principal extends Application {
  public static PrincipalController controller;
  public static void main(String[] args) {
    controller = new PrincipalController();
    launch(args);
  } 
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal_view.fxml")); 
    Parent root = loader.load(); 
    
    root.getStylesheets().add("/assets/styles/principal_style.css");

    Scene scene = new Scene(root);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("O 'jantar' dos benders");
    primaryStage.setResizable(false);
    primaryStage.show(); 


    primaryStage.setOnCloseRequest((event) -> {
      System.exit(0);
    });
  } 
}