/****************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 13/05/2021
* Ultima alteracao: 19/05/2021
* Nome: Principal.java
* Funcao: Gerar os arquivos .classes, iniciar e fechar minha interface grafica 
****************************************************************/

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Apenas para gerar os .classes ddos meus threads e controllers
import controller.PrincipalController;
import threads.ProdutorThread;
import threads.ConsumidorThread;


public class Principal extends Application { 
  private static PrincipalController controller;
  public static void main(String[] args) {
    controller = new PrincipalController();
    launch(args); 
  } 
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    // Carrego minha tela principal
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal_view.fxml")); 
    Parent root = loader.load(); 
    
    // Crio minha tela princiapl
    Scene scene = new Scene(root);
    
    // Adiciono minha scene ao meu stage e exibo
    primaryStage.setTitle("Gorobar");
    primaryStage.setResizable(false);

    // Adiciono minha scene ao meu stage e exibo
    primaryStage.setScene(scene);
    primaryStage.show(); 

    // Encerrar as threads apos fechar a aplicacao
    primaryStage.setOnCloseRequest((event) -> {
      encerrarProdutores();
      encerrarConsumidores();
      System.exit(0);
    });
  } 

  private void encerrarProdutores(){
    controller.encerrarProdutor(1);  
    controller.encerrarProdutor(2);  
    controller.encerrarProdutor(3);  
  }

  private void encerrarConsumidores(){
    controller.encerrarConsumidor(1);
    controller.encerrarConsumidor(2);
    controller.encerrarConsumidor(3);
    controller.encerrarConsumidor(4);
    controller.encerrarConsumidor(5);
    controller.encerrarConsumidor(6);
  }
}