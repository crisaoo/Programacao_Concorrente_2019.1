/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoAmarelo.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoAmarelo extends NezukoThread{
  public NezukoAmarelo(PrincipalController controller){
    super(135, 325, "Amarelo", controller, controller.imgNezukoAmarelo);
  }

  @Override
  public void run(){
    while(true) {
      try {
        regiaoNaoCritica();
        rcCinza();
        rcEncruzilhada1();
        rcVerde();
        rcEncruzilhada2();
        rcPreto();
        rcEncruzilhada3();
        rcAzul();
      } catch (InterruptedException e) {}
    }
  }

  private void regiaoNaoCritica() throws InterruptedException{
    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(270);
    });
    descer(435);
  }

  private void rcCinza() throws InterruptedException{
    controller.rcCinzaAmarelo.acquire();
    descer(455);

    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(0);
    });

    direita(475);
  }
  
  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcAmareloVerde.acquire();
    direita(500);

    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(90);
    });

    subir(435);
    controller.rcCinzaAmarelo.release();
  }

  private void rcVerde() throws InterruptedException{
    subir(325);
  } 

  private void rcEncruzilhada2() throws InterruptedException{
    controller.rcPretoAmarelo.acquire();
    subir(305);

    Platform.runLater(() ->{
      imgNezuko.setRotate(0);
    });

    esquerda(475);
    controller.rcAmareloVerde.release();
  }
  
  private void rcPreto() throws InterruptedException{
    esquerda(370);
  }

  private void rcEncruzilhada3() throws InterruptedException{
    controller.rcAmareloAzul.acquire();
    esquerda(315);
    controller.rcPretoAmarelo.release();
  }

  private void rcAzul() throws InterruptedException{
    esquerda(135);
    controller.rcAmareloAzul.release();
  }
}
