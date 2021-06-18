/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 11/06/2021
* Ultima alteracao: 12/06/2021
* Nome: NezukoVerde.java
* Funcao: 
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;

public class NezukoVerde extends NezukoThread{
  public NezukoVerde(PrincipalController controller){
    super(525, 305, "Verde", controller, controller.imgNezukoVerde);
  }

  @Override
  public void run(){
    // como o verde ja comeca na quadra preto, entao vou iniciar com essa regiao ocupada pelo verde
    try {
      controller.rcPretoVerde.acquire();
      // controller.rcVermelhoPretoVerde.acquire();
    } catch (InterruptedException e) {}

    while(true) {
      try {
        rcPreto();
        rcEncruzilhada1();
        rcVermelho();
        regiaoNaoCritica();
        rcCinza();
        rcEncruzilhada2();
        rcAmarelo();
        rcEncruzilhada3();
      } catch (InterruptedException e) {}
    }
  }

  private void rcPreto() throws InterruptedException{
    direita(655);
  }

  private void rcEncruzilhada1() throws InterruptedException{
    controller.rcVermelhoVerde.acquire();
    controller.rcVermelhoPretoVerde.acquire();
    direita(705);
    controller.rcVermelhoPretoVerde.release();
    controller.rcPretoVerde.release();
  }

  private void rcVermelho() throws InterruptedException{
    direita(850);

    Platform.runLater(() ->{
      imgNezuko.setRotate(90);
    });
  }

  private void regiaoNaoCritica() throws InterruptedException{
    descer(325);
    controller.rcVermelhoVerde.release();

    descer(435);
    
    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      imgNezuko.setRotate(0);
    });
  }

  private void rcCinza() throws InterruptedException{
    controller.rcCinzaVerde.acquire();
    descer(455);
    esquerda(525);
  }

  private void rcEncruzilhada2() throws InterruptedException{
    controller.rcAmareloVerde.acquire();
    esquerda(500);

    Platform.runLater(() ->{
      imgNezuko.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      imgNezuko.setRotate(270);
    });

    subir(435);
    controller.rcCinzaVerde.release();
  }

  private void rcAmarelo() throws InterruptedException{
    subir(325);
  }

  private void rcEncruzilhada3() throws InterruptedException{
    controller.rcPretoVerde.acquire();
    // controller.rcVermelhoPretoVerde.acquire();
    subir(305);

    Platform.runLater(() ->{
      imgNezuko.setRotate(0);
    });

    direita(525);
    controller.rcAmareloVerde.release();
  }
}
