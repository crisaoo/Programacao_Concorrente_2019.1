/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoCinza.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoCinza extends NezukoThread{
  public NezukoCinza(PrincipalController controller){
    super(0, 0, "Cinza", controller, controller.imgNezukoCinza);
  }

  @Override
  public void run(){
    while(true){
      try{
        primeiraRNC();
        rcAzul();
        segundaRNC();
        rcAmarelo();
        rcEncruzilhada1();
        rcVerde();
        terceiraRNC();
        rcVermelho();
        quartaRNC();
        rcRosa();
        rcEncruzilhada2();
        rcMarrom();
        quintaRNC();
      } catch(InterruptedException e){}
    }
  }


  // RNC: Regiao Nao Critica
  private void primeiraRNC() throws InterruptedException{
    Platform.runLater(() -> {
      controller.imgNezukoCinza.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      controller.imgNezukoCinza.setRotate(90);
    });

    descer(130);
  }

  
  private void rcAzul() throws InterruptedException{
    controller.rcCinzaAzul.acquire();
    descer(305);
    controller.rcCinzaAzul.release();
  }


  private void segundaRNC() throws InterruptedException{
    descer(455);

    Platform.runLater(() -> {
      controller.imgNezukoCinza.setRotate(0);
    });

    direita(110);
  }


  private void rcAmarelo() throws InterruptedException{
    controller.rcCinzaAmarelo.acquire();
    direita(470);
  }


  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcCinzaVerde.acquire();
    direita(520);
    controller.rcCinzaAmarelo.release();
  }

  
  private void rcVerde() throws InterruptedException{
    direita(855);
    controller.rcCinzaVerde.release();
  }


  private void terceiraRNC() throws InterruptedException{
   direita(1010);

    Platform.runLater(() -> {
      controller.imgNezukoCinza.setRotate(270);
    });

    subir(320);
  }
  

  private void rcVermelho() throws InterruptedException{
    controller.rcCinzaVermelho.acquire();
    subir(155);
    controller.rcCinzaVermelho.release();
  }


  private void quartaRNC() throws InterruptedException{
    subir(0);

    Platform.runLater(() -> {
      controller.imgNezukoCinza.setRotate(0);
      controller.imgNezukoCinza.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    });

    esquerda(840);
  }
  
  
  private void rcRosa() throws InterruptedException{
    controller.rcCinzaMarrom.acquire();
    controller.rcCinzaRosaMarrom.acquire();
    controller.rcCinzaRosa.acquire();
    esquerda(505);
  }


  private void rcEncruzilhada2() throws InterruptedException{
    esquerda(455);
    controller.rcCinzaRosa.release();
    controller.rcCinzaRosaMarrom.release();
  }


  private void rcMarrom() throws InterruptedException{
    esquerda(140);
    controller.rcCinzaMarrom.release();
  }


  private void quintaRNC() throws InterruptedException{
    esquerda(0);
  }
}
