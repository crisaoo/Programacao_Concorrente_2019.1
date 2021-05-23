/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 08/04/2021
* Ultima alteracao: 15/04/2021
* Nome: Vaca1Thread.java
* Funcao: Controlar a posicao e velocidade da minha vaquinha
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Vaca1Thread extends Thread{
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


  public Vaca1Thread (PrincipalController controller){
    /**
     * No construtor da thread eu pego a referencia do meu controller principal, a imageView da minha vaca
     * defino a valocidade inicial dela, as posicoes inicias e as regioes criticas ()
     */

    this.controller = controller;
    vaca = controller.imgViewVaca1;
    velocidade = 600;
    x = 0;
    y = 0;
    regiaoCritica1[0] = 100;
    regiaoCritica1[1] = 210;
    regiaoCritica2[0] = 330;
    regiaoCritica2[1] = 430;
  }

  @Override 
  public void run(){
    controller.adicionarVaca1("/assets/images/vaca.gif");
    percursoVaca();
  }

  // Metodo responsavel por fazer todo o percurso da vaca 1 mudando sua posicao na tela atraves do setX e setY
  private void percursoVaca(){
    percorrerPrimeiraRegiaoNaoCritica();
    // Antes de entrar do curral (minha RC), eu entro dentro de um while e pergunto se ha alguma outra vaca la dentro
    while(!controller.possoAcessarRC(1, regiaoCritica1))
      System.out.print("");

    percorrerPrimeiraRegiaoCritica();
    percorrerSegundaRegiaoNaoCritica();
    while(!controller.possoAcessarRC(1, regiaoCritica2))
      System.out.print("");
    
    percorrerSegundaRegiaoCritica();
    percorrerTerceiraRegiaoNaoCriticaVaca();

    // Redefinindo as posicoes iniciais e utilizando da recursao para criar um loop
    x = 0;
    y = 0;
    percursoVaca();
  }

  public void percorrerPrimeiraRegiaoNaoCritica(){
    // Percorrer a linha vertical
    while(vaca.getY() != 90){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha horizontal 
    while(vaca.getX() != 80){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }


  public void percorrerPrimeiraRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != 220){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  public void percorrerSegundaRegiaoNaoCritica(){
    // Percorrer a primeira linha horizontal 
    while(vaca.getX() != 190){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != 320){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a segunda linha horizontal 
    while(vaca.getX() != 80){
      try{
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  public void percorrerSegundaRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != 450){
      try{
        y++;
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
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != 580){
      try{
        y++;
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
        controller.txtVelocidadeVaca1.setText(velocidadeStr);
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
        controller.txtVelocidadeVaca1.setText(velocidadeStr);
      });
    }
  }
}