/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 08/04/2021
* Ultima alteracao: 15/04/2021
* Nome: Principal.java
* Funcao: Gerar os arquivos .classes, iniciar e fechar minha interface grafica 
*************************************************************** */


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.PrincipalController;
// Para atualizar os .classes das minhas vaquinhas
import threads.VacaThread;
import threads.Vaca1;
import threads.Vaca2;

public class Principal extends Application { 
  public static void main(String[] args) {
    launch(args); 
  }
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    // Carrego minha tela principal
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal_view.fxml"));  
    Parent root = loader.load(); 
    
    root.getStylesheets().add( "/assets/styles/main_style.css" ); // Adiciono um css, nesse caso, so aos meus botoes
    
    Scene scene = new Scene(root); // Crio minha tela principal
  
    // Adiciono minha scene ao meu stage e exibo
    primaryStage.setTitle("Fazenda top"); 
    primaryStage.setResizable(false); 

    // Adiciono minha scene ao meu stage e exibo
    primaryStage.setScene(scene);
    primaryStage.show(); 

    // Como minhas vacas estao em loop, eu preciso encerrar as threads delas quando fechar a tela
    primaryStage.setOnCloseRequest((event) -> { 
        PrincipalController.vaca1.interrupt();
        PrincipalController.vaca2.interrupt();
        System.exit(0);
    });
  }
}