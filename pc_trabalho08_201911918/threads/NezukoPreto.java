/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoPreto.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoPreto extends NezukoThread{
  public NezukoPreto(PrincipalController controller){
    super(365, 150, "Preto", controller, controller.imgNezukoPreto);
  }

  @Override
  public void run(){
    // como o preto ja comeca na quadra marrom, entao vou iniciar com essa regiao ocupada pelo preto
    try {
      controller.rcMarromPreto.acquire();
    } catch (InterruptedException e) {}
    
    while (true) {
      try {

        rcMarrom();
        rcEncruzilhada1();
        rcRosa();
        rcEncruzilhada2();
        rcVermelho();
        rcEncruzilhada3();
        rcVerde();
        rcEncruzilhada4();
        rcAmarelo();
        rcEncruzilhada5();
        rcAzul();
        rcEncruzilhada6();
      } catch (InterruptedException e) {}
    }
  }

  private void rcMarrom() throws InterruptedException {
    direita(455);
  }

  private void rcEncruzilhada1() throws InterruptedException {
    controller.rcRosaPreto.acquire();
    controller.rcRosaMarromPreto.acquire();
    direita(505);
    controller.rcRosaMarromPreto.release();
    controller.rcMarromPreto.release();
  }

  private void rcRosa() throws InterruptedException {
    direita(655);
  }

  private void rcEncruzilhada2() throws InterruptedException {
    controller.rcPretoVerde.acquire();
    controller.rcVermelhoPretoVerde.acquire();
    controller.rcVermelhoPreto.acquire();
    controller.rcRosaVermelhoPreto.acquire();
    direita(680);

    Platform.runLater(() -> {
      imgNezuko.setRotate(90);
    });

    descer(170);
    controller.rcRosaVermelhoPreto.release();
    controller.rcRosaPreto.release();
  }

  private void rcVermelho() throws InterruptedException {
    descer(285);
  }

  private void rcEncruzilhada3() throws InterruptedException {

    descer(305);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(0);
    });

    esquerda(655);
    controller.rcVermelhoPretoVerde.release();
    controller.rcVermelhoPreto.release();
  }

  private void rcVerde() throws InterruptedException {
    esquerda(525);
  }

  private void rcEncruzilhada4() throws InterruptedException {
    controller.rcPretoAmarelo.acquire();
    esquerda(475);
    controller.rcPretoVerde.release();
  }

  private void rcAmarelo() throws InterruptedException {
    esquerda(365);
  }

  private void rcEncruzilhada5() throws InterruptedException {
    controller.rcPretoAzul.acquire();
    esquerda(345);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(270);
    });

    subir(280);
    controller.rcPretoAmarelo.release();
  }

  private void rcAzul() throws InterruptedException {
    subir(170);
  }

  private void rcEncruzilhada6() throws InterruptedException {
    controller.rcMarromPreto.acquire();
    subir(150);

    Platform.runLater(() -> {
      imgNezuko.setRotate(0);
    });

    direita(365);
    controller.rcPretoAzul.release();
  }
}
