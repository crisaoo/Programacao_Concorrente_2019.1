/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 12/06/2021
* Nome: NezukoRosa.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoRosa extends NezukoThread{
  public NezukoRosa(PrincipalController controller){
    super(500, 0, "Rosa", controller, controller.imgNezukoRosa);
  }

  @Override
  public void run(){
    // como o marrom ja comeca na quadra cinza, entao vou iniciar com essa regiao ocupada pelo marrom
    try {
      controller.rcCinzaRosa.acquire();
    } catch (InterruptedException e) {}

    while(true){
      try{
        rcCinza();
        regiaoNaoCritica();
        rcVermelho();
        rcEncruzilhada1();
        rcPreto();
        rcEncruzilhada2();
        rcMarrom();
        rcEncruzilhada3();
      } catch (InterruptedException e){}
    }
  }

  private void rcCinza() throws InterruptedException{
    direita(815);
    controller.rcCinzaRosa.release();
  }

  private void regiaoNaoCritica() throws InterruptedException{
    // controller.rcCinzaRosaMarrom.release();

    Platform.runLater(() -> {
      imgNezuko.setRotate(90);
    });
    descer(130);
  }

  private void rcVermelho() throws InterruptedException{
    controller.rcRosaVermelho.acquire();
    descer(150);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(0);
    });

    esquerda(705);
  }

  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcRosaPreto.acquire();
    controller.rcRosaVermelhoPreto.acquire();
    esquerda(655);
    controller.rcRosaVermelhoPreto.release();
    controller.rcRosaVermelho.release();
  }

  private void rcPreto() throws InterruptedException{
    esquerda(505);
  }
  
  private void rcEncruzilhada2() throws InterruptedException{
    controller.rcRosaMarrom.acquire();
    controller.rcRosaMarromPreto.acquire();
    esquerda(480);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(-90);
    });

    subir(130);
    controller.rcRosaMarromPreto.release();
    controller.rcRosaPreto.release();
  }

  private void rcMarrom() throws InterruptedException{
    subir(20);
  }

  private void rcEncruzilhada3() throws InterruptedException{
    controller.rcCinzaRosa.acquire();  
    controller.rcCinzaRosaMarrom.acquire();
    subir(0);

    Platform.runLater(() -> {
      imgNezuko.setRotate(0);
    });

    direita(500);
    controller.rcCinzaRosaMarrom.release();
    controller.rcRosaMarrom.release();
  }
}
