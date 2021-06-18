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

public class NezukoMarrom extends NezukoThread{
  public NezukoMarrom(PrincipalController controller){
    super(165, 0, "Marrom", controller, controller.imgNezukoMarrom);
  }

  @Override
  public void run(){
    // como o marrom ja comeca na quadra cinza, entao vou iniciar com essa regiao ocupada pelo marrom
    try {
      controller.rcCinzaMarrom.acquire();  
    } catch (InterruptedException e) {}

    while(true){
      try {
        rcCinza();
        rcEncruzilhada1();
        rcRosa();
        rcEncruzilhada2();
        rcPreto();
        rcEncruzilhada3();
        rcAzul();
        regiaoNaoCritica();
      } catch (InterruptedException e) {}
    }
  }

  private void rcCinza() throws InterruptedException {
    Platform.runLater(() -> {
      imgNezuko.setRotate(0);
    });

    direita(460);
  }

  private void rcEncruzilhada1() throws InterruptedException {
    controller.rcMarromPreto.acquire();
    controller.rcRosaMarromPreto.acquire();
    controller.rcRosaMarrom.acquire();
    controller.rcCinzaRosaMarrom.acquire();
    direita(480);

    Platform.runLater(() -> {
      imgNezuko.setRotate(90);
    });

    descer(20);
    controller.rcCinzaRosaMarrom.release();
    controller.rcCinzaMarrom.release();
  }

  private void rcRosa() throws InterruptedException {
    descer(130);
  }

  private void rcEncruzilhada2() throws InterruptedException {
    // controller.rcMarromPreto.acquire();
    descer(150);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(0);
    });

    esquerda(455);
    controller.rcRosaMarromPreto.release();
    controller.rcRosaMarrom.release();
  }
  
  private void rcPreto() throws InterruptedException {
    esquerda(365);
  }

  private void rcEncruzilhada3() throws InterruptedException {
    controller.rcMarromAzul.acquire();
    esquerda(315);
    controller.rcMarromPreto.release();
  }

  private void rcAzul() throws InterruptedException {
    esquerda(140);
    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(-90);
    });
    controller.rcMarromAzul.release();
  }

  private void regiaoNaoCritica() throws InterruptedException {
    subir(20);
    controller.rcCinzaMarrom.acquire();
    subir(0);
  }

  
}
