/****************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 15/05/2021
* Ultima alteracao: 19/05/2021
* Nome: ProdutorThread.java
* Funcao: Controlar todos os processos do meu produtor
****************************************************************/
package threads;

import controller.PrincipalController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ProdutorThread extends Thread {
  private int nProdutor; // para saber com qual produtor estou trabalhando
  private boolean vivo; // variavel q irah definir a condicao do loop do meu produtor
  private PrincipalController controller;
  @FXML private ImageView produtor;

  public ProdutorThread(PrincipalController controller, int nProdutor){
    this.controller = controller;
    this.nProdutor = nProdutor;
    produtor = this.controller.adicionarImagemProdutor(this.nProdutor); 
    vivo = true;
  }  

  @Override
  public void run(){
    while(vivo){
      try {
        Thread.sleep(2000);
        produzirCerveja();
        entregarCerveja();
      } catch (InterruptedException e) {  
      }
    }   
  }

  private void produzirCerveja() throws InterruptedException{
    // Mudo a posicao do meu produtor para perto do barril onde ele ira produzir a cerveja
    produtor.setImage(null); 
    controller.setImgProdutorProduzindo(nProdutor, false);
    // Decrementando as posicoes vazias no meu balcao 
    PrincipalController.balcaoVazio.acquire();
    PrincipalController.mutex.acquire();

    Thread.sleep(controller.getVelocidadeProdutor() * 1000);
  }


  private void entregarCerveja(){
    // Mudo a posicao do meu produtor para o balcao, onde ele irah entregar a cerveja para o consumidor retirar
    controller.setImgProdutorProduzindo(nProdutor, true);
    produtor = controller.adicionarImagemProdutor(nProdutor);
    controller.adicionarCervejaNoBalcao();
    PrincipalController.mutex.release();
     // Incrementando as posicoes cheias no meu balcao
     PrincipalController.balcaoCheio.release();   
  }

  public void setVivo(boolean vivo){
    this.vivo = vivo;
  }
}
