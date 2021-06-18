/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoThread.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class NezukoThread extends Thread{
  protected double x, y;
  protected int velocidade;
  protected String quadra;
  protected PrincipalController controller;
  protected ImageView imgNezuko;

  public NezukoThread(double x, double y, String quadra, PrincipalController controller, ImageView imgNezuko){
    this.x = x;
    this.y = y;
    this.quadra = quadra;
    this.controller = controller;
    this.imgNezuko = imgNezuko;
    velocidade = 25;

    controller.adicionarImgNezuko(quadra, "assets/images/nezuko-correndo.gif", x, y);
  }

  public String getQuadra() {
    return quadra;
  }

  // Movimentos
  protected void direita(int limite) throws InterruptedException {
    while(x != limite){
      Thread.sleep(velocidade);
      x += 5;
      Platform.runLater(() -> {
        imgNezuko.setX(x);
      });
    }
  }
 
  protected void esquerda(int limite) throws InterruptedException {
    while(x != limite){
      Thread.sleep(velocidade);
      x -= 5;
      Platform.runLater(() -> {
        imgNezuko.setX(x);
      });
    }
  }

  protected void descer(int limite) throws InterruptedException{
    while(y != limite){
      Thread.sleep(velocidade);
      y += 5;
      Platform.runLater(() -> {
        imgNezuko.setY(y);
      });
    }
  }

  protected void subir(int limite) throws InterruptedException{
    while(y != limite){
      Thread.sleep(velocidade);
      y -= 5;
      Platform.runLater(() -> {
        imgNezuko.setY(y);
      });
    }
  }


  public void setVelocidade(Integer velocidade){
    this.velocidade = velocidade;
  }
}
