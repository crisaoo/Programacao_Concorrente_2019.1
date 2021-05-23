/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 08/04/2021
* Ultima alteracao: 06/05/2021
* Nome: Vaca1Thread.java
* Funcao: Controlar a posicao e velocidade da minha vaquinha
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;

public class Vaca1 extends VacaThread{
  
  public Vaca1 (PrincipalController controller){
    super(controller);
    vaca = controller.imgViewVaca1;
    x = 0;
    y = -2;
  }

  @Override 
  public void run(){
    controller.adicionarVaca1("/assets/images/vaca.gif");
    percursoVaca();
  }


  // Metodo responsavel por fazer todo o percurso da vaca 1 mudando sua posicao na tela atraves do setX e setY
  private void percursoVaca(){
    vaca.setX(x * 10);
    vaca.setY(y * 10);

    percorrerPrimeiraRegiaoNaoCritica();
    // Antes de entrar do curral (minha RC), eu utilizo algum dos 4 algoritmos para verificar se posso entrar na rc
    possoAcessarRC(1, 1);
    percorrerPrimeiraRegiaoCritica();
    jaSaiDaRC(1, 1);
    percorrerSegundaRegiaoNaoCritica();
    possoAcessarRC(1, 2);
    percorrerSegundaRegiaoCritica();
    jaSaiDaRC(1, 2);
    percorrerTerceiraRegiaoNaoCritica();
    
    // Redefino as posicoes iniciais e utilizo da recursao para criar um loop
    x = 0;
    y = -6;
    pararPercurso = false;
    percursoVaca();
  }

  private void percorrerPrimeiraRegiaoNaoCritica(){
    // Percorrer a linha vertical
    while(vaca.getY() != 90 && !pararPercurso){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha horizontal 
    while(vaca.getX() != 80 && !pararPercurso ){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  private void percorrerPrimeiraRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != 220 && !pararPercurso ){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

  }

  private void percorrerSegundaRegiaoNaoCritica(){
    // Percorrer a primeira linha horizontal 
    while(vaca.getX() != 190  && !pararPercurso ){
      try{
        x++;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != 320 && !pararPercurso ){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a segunda linha horizontal 
    while(vaca.getX() != 80 && !pararPercurso ){
      try{
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  private void percorrerSegundaRegiaoCritica(){
    // Percorrer a linha vertical do curral
    while(vaca.getY() != 450 && !pararPercurso ){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }

  private void percorrerTerceiraRegiaoNaoCritica(){
    // Percorrer a linha horizontal 
    while(vaca.getX() != 0 && !pararPercurso ){
      try{
        x--;
        Platform.runLater(() -> {
          vaca.setX(x * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }

    // Percorrer a linha vertical
    while(vaca.getY() != 580  && !pararPercurso ){
      try{
        y++;
        Platform.runLater(() -> {
          vaca.setY(y * 10);
        });
        Thread.sleep(velocidade);
      } catch(InterruptedException e){}
    }
  }
  // ----------------------------------------------------------------


  @Override
  public void alterarTextoVelocidade(String velocidadeStr){
    controller.txtVelocidadeVaca1.setText(velocidadeStr);
  }
}