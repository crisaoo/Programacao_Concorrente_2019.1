/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 09/04/2021
* Ultima alteracao: 15/04/2021
* Nome: Vaca2Thread.java
* Funcao: Controlar a posicao e velocidade da minha vaquinha
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Vaca2Thread extends Thread{
  // Preciso do controller principal para acessar as imagens das vacas e poder move-las. Tambem eh necessario 
  // para verificar as regioes criticas
  private PrincipalController controller;
  // x e y representam a posicao da minha vaca e a velocidade eh baseada no sleep
  private int x, y, velocidade;
  // Salvo minhas regioes criticas na minha thread pois cada vaca possui uma regiao critica diferente 
  // Sao salvas em um vetor de tamanho 2, onde a posicao 0 representa o inicio da minha RC e a posicao 1
  // Representa o fim dela
  private int regiaoCritica1[] = new int[2];
  private int regiaoCritica2[] = new int[2];

  @FXML ImageView vaca;
  

  public Vaca2Thread (PrincipalController controller){
    /**
     * No construtor da thread eu pego a referencia do meu controller principal, a imageView da minha vaca
     * defino a valocidade inicial dela, as posicoes inicias e as regioes criticas ()
     */
    this.controller = controller;
    vaca = controller.imgViewVaca2;
    velocidade = 600;
    regiaoCritica1[0] = -80;
    regiaoCritica1[1] = -190;
    regiaoCritica2[0] = -310;
    regiaoCritica2[1] = -420;
    x = 0;
    y = 0;
  }
  
  @Override 
  public void run(){
    controller.adicionarVaca2("/assets/images/vaca.gif");
    percursoVaca();
  }

  // Metodo responsavel por fazer todo o percurso da vaca 1 mudando sua posicao na tela atraves do setX e setY
  private void percursoVaca(){
    percorrerPrimeiraRegiaoNaoCritica();
    // Antes de entrar do curral (minha RC), eu entro dentro de um while e pergunto se ha alguma outra vaca la dentro
    while(!controller.possoAcessarRC(2, regiaoCritica1))
      System.out.print("");
    
    percorrerPrimeiraRegiaoCritica();
    percorrerSegundaRegiaoNaoCritica();
    while(!controller.possoAcessarRC(2, regiaoCritica2))
      System.out.print("");
    
      percorrerSegundaRegiaoCritica();
    percorrerTerceiraRegiaoNaoCriticaVaca();

    // Redefinindo as posicoes iniciais e utilizando da recursao para criar um loop
    x = 0;
    y = 0;
    percursoVaca();
  }


  // Percurso da vaca 1
  public void percorrerPrimeiraRegiaoNaoCritica(){
    // Percorrer a linha vertical
    while(vaca.getY() != -80){
      try{
        y--;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha horizontal 
    while(vaca.getX() != -90){
      try{
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }


  public void percorrerPrimeiraRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != -200){
      try{
        y--;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  public void percorrerSegundaRegiaoNaoCritica(){
    // Percorrer a primeira linha horizontal 
    while(vaca.getX() != -190){
      try{
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != -300){
      try{
        y--;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a segunda linha horizontal 
    while(vaca.getX() != -80){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  public void percorrerSegundaRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != -430){
      try{
        y--;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  public void percorrerTerceiraRegiaoNaoCriticaVaca(){
    // Percorrer a linha horizontal 
    while(vaca.getX() != 0){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != -560){
      try{
        y--;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  // Para aumentar a velocidade da minha vaca, eu diminuo o tempo do sleep
  // O limite maximo da minha velocidade eh 100 milissegundos
  public void aumentarVelocidade(){
    if(velocidade > 100){
      velocidade -= 100;
      Platform.runLater(() -> {
        // Logica para definir como minha velocidade sera apresentada na tela 
        String velocidadeStr = String.valueOf(11 - velocidade / 100);
        controller.txtVelocidadeVaca2.setText(velocidadeStr);
      });
    }
  }

  // Para diminuir a velocidade da minha vaca, eu aumento o tempo do sleep
  // O limite minimo da minha velocidade eh 1000 milissegundos
  public void diminuirVelocidade(){
    if(velocidade < 1000){
      velocidade += 100;
      Platform.runLater(() -> {
        // Logica para definir como minha velocidade sera apresentada na tela 
        String velocidadeStr = String.valueOf(11 - velocidade / 100);
        controller.txtVelocidadeVaca2.setText(velocidadeStr);
      });
    }
  }
}