/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 24/07/2021
* Ultima alteracao: 25/07/2021
* Nome: Principal.java
* Funcao: Iniciar a minha aplicacao grafica
*************************************************************** */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import controller.PrincipalController;
import models.Path;

public class Principal extends Application { 
  public static void main(String[] args) {
    launch(args); 
  } 
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.PRINCIPAL_VIEW)); 
    Parent root = loader.load(); 
    root.getStylesheets().add(Path.PRINCIPAL_CSS);
    Scene scene = new Scene(root);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Streaming de musica");
    primaryStage.getIcons().add(new Image(Path.ICON));
    primaryStage.setResizable(false);
    primaryStage.show(); 
  } 
}