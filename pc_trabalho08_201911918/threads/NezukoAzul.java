/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoAzul.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoAzul extends NezukoThread{
  public NezukoAzul(PrincipalController controller){
    super(0, 150, "Azul", controller, controller.imgNezukoAzul);
  }

  @Override
  public void run(){
    while(true){
      try {
        rcCinza();
        regiaoNaoCritica1();
        rcAmarelo();
        rcEncruzilhada1();
        rcPreto();
        rcEncruzilhada2();
        rcMarrom();
        regiaoNaoCritica2();
      } catch (InterruptedException e) {}
    }
  }

  private void rcCinza() throws InterruptedException{
    controller.rcCinzaAzul.acquire();
    esquerda(0);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(270);
    });

    descer(305);
    
  }

  private void regiaoNaoCritica1() throws InterruptedException{
    direita(20);
    controller.rcCinzaAzul.release();

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(0);
    });

    direita(110);
  }

  private void rcAmarelo() throws InterruptedException{
    controller.rcAmareloAzul.acquire();
    direita(315);
  }

  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcPretoAzul.acquire();
    direita(340);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(90);
    });

    subir(285);
    controller.rcAmareloAzul.release();
  } 

  private void rcPreto() throws InterruptedException{
    subir(170);
  }

  private void rcEncruzilhada2() throws InterruptedException{
    controller.rcMarromAzul.acquire();
    subir(150);

    Platform.runLater(() -> {
      imgNezuko.setRotate(0);
    });

    esquerda(320);
    controller.rcPretoAzul.release();
  }

  private void rcMarrom() throws InterruptedException{
    esquerda(140);
    controller.rcMarromAzul.release();
  }

  private void regiaoNaoCritica2() throws InterruptedException{
    esquerda(20);
  }
}
