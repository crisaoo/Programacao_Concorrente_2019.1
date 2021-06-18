/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 13/06/2021
* Nome: NezukoVermelho.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoVermelho extends NezukoThread{ 
  public NezukoVermelho(PrincipalController controller){
    super(700, 150, "Vermelho", controller, controller.imgNezukoVermelho);
  }

  @Override
  public void run(){
    // como o vermelho ja comeca na quadra rosa, entao vou iniciar com essa regiao ocupada pelo vermelho
    try {
      controller.rcRosaVermelho.acquire();
    } catch (InterruptedException e) {}

    while (true) {
      try {
        rcRosa();
        regiaoNaoCritica1();
        rcCinza();
        regiaoNaoCritica2();
        rcVerde();
        rcEncruzilhada1();
        rcPreto();
        rcEncruzilhada2();
      } catch (InterruptedException e) {}
    }
  }

  
  private void rcRosa() throws InterruptedException{
    direita(815);
    controller.rcRosaVermelho.release();
  }

  private void regiaoNaoCritica1() throws InterruptedException{
    direita(985);
  }

  private void rcCinza() throws InterruptedException{
    controller.rcCinzaVermelho.acquire();
    direita(1010);

    Platform.runLater(() -> {
      imgNezuko.setRotate(90);
    });

    descer(305);
  }

  private void regiaoNaoCritica2() throws InterruptedException{
    esquerda(990);
    controller.rcCinzaVermelho.release();

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(0);
    });

    esquerda(875);
  }

  private void rcVerde() throws InterruptedException{
    controller.rcVermelhoVerde.acquire();
    esquerda(705);
  }

  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcRosaVermelho.acquire();
    controller.rcRosaVermelhoPreto.acquire();
    controller.rcVermelhoPreto.acquire();
    controller.rcVermelhoPretoVerde.acquire();
    esquerda(680);

    Platform.runLater(() -> {
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(270);
    });

    subir(285);
    controller.rcVermelhoPretoVerde.release();
    controller.rcVermelhoVerde.release();
  }

  private void rcPreto() throws InterruptedException{
    subir(170);
  }

  private void rcEncruzilhada2() throws InterruptedException{
    subir(150);

    Platform.runLater(() -> {
      imgNezuko.setRotate(0);
    });

    direita(700);
    controller.rcRosaVermelhoPreto.release();
    controller.rcVermelhoPreto.release();
  }
}
