/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 09/06/2021
* Ultima alteracao: 18/06/2021 
* Nome: Principal.java
* Funcao: Iniciar minha interface grafica
*************************************************************** */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// Imports para atualizar sempre os .class (por algum motivo o vscode nao estava atualizando)
import controller.PrincipalController;
import threads.NezukoThread;
import threads.NezukoAmarelo;
import threads.NezukoAzul;
import threads.NezukoCinza;
import threads.NezukoMarrom;
import threads.NezukoPreto;
import threads.NezukoRosa;
import threads.NezukoVerde;
import threads.NezukoVermelho;

public class Principal extends Application { 
  public static Stage stage;
  private static Scene introScene, principalScene;
  private static FXMLLoader loader;
  private static Parent root;

  public static void main(String[] args) {
    launch(args); 
  } 
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    // Soh para atualizar o .class
    IntroController intro = new IntroController();

    stage = primaryStage;
    
    loader = new FXMLLoader(getClass().getResource("/view/intro_view.fxml")); 
    root = loader.load(); 
    root.getStylesheets().add("/assets/styles/main_style.css" );
    introScene = new Scene(root);
    
    // Carrego o loader aqui pois nao posso fazer isso em uma classe estatica
    loader = new FXMLLoader(getClass().getResource("/view/principal_view.fxml")); 
    
    // Configurar titulo, dimensao, icones, cena e exibir
    stage.setTitle("Run Nezuko, run!!!");
    stage.setResizable(false);
    stage.getIcons().add(new Image("assets/images/nezuko-icon.png"));
    stage.setScene(introScene);
    stage.show(); 

    // Antes de fechar minha interface, eu interrompo todas as minhas threads
    stage.setOnCloseRequest((event) -> {
      // Mas antes eu verifico se estou na cena princial e nao na intro
      if(stage.getScene().equals(principalScene)){ 
        PrincipalController.nezukoCinza.interrupt();
        PrincipalController.nezukoMarrom.interrupt();
        PrincipalController.nezukoRosa.interrupt();
        PrincipalController.nezukoVermelho.interrupt();
        PrincipalController.nezukoVerde.interrupt();
        PrincipalController.nezukoAmarelo.interrupt();
        PrincipalController.nezukoAzul.interrupt();
        PrincipalController.nezukoPreto.interrupt();
      }
      System.exit(0);
    });
  } 

  public static void mudarParaTelaPrincipal() throws IOException{
    root = loader.load(); 
    root.getStylesheets().add("/assets/styles/main_style.css" );
    principalScene = new Scene(root);
    stage.setScene(principalScene);
  }
}