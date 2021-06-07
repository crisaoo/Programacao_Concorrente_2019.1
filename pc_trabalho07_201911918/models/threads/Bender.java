/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 03/06/2021
* Ultima alteracao: 06/06/2021
* Nome: Bender.java
* Funcao: Bender irah substituir os filosofos: enquanto bender nao bebe e fuma ele DANCA
*************************************************************** */

package models.threads;

import controller.PrincipalController;
import models.util.Paths;

public class Bender extends Thread{
  private PrincipalController controller;
  private int id, posicaoXInicial; // O posicaoX eh para resolver um bug que estava acontecendo quando tento
                                  // modificar a imagem para o bender fumando e bebendo
  
  public Bender(PrincipalController controller, int id){
    this.controller = controller;
    this.id = id;
    posicaoXInicial = controller.getPosicaoXInicial(this.id);
  }

  @Override
  public void run(){
    try{
      while(true){
        dancar();
        pegarGarfos();
        fumarBeber();
        devolverGarfos();
      }
    } catch(InterruptedException e){
    }
  }

  // Gerenciar a mesa onde estao meus benders, para que nao haja conflito
  private void pegarGarfos() throws InterruptedException{
    PrincipalController.mutex.acquire();  // verifico se posso entrar na regiao critica (RC)
    controller.setEstado(id, 1);          // Mudo meu estado para "estou com vontade de beber e fumar"
    possoFurmarBeber(id);                 
    PrincipalController.mutex.release();  // Depois de verificar se posso pegar os "garfos", eu libero minha RC
    PrincipalController.controleGarfos[id].acquire(); // Agora verifico minha outra rc, os garfos
  }

  private void devolverGarfos() throws InterruptedException{
    PrincipalController.mutex.acquire();
    controller.setEstado(id, 0);
    controller.mudarImgGarfos(id, false);
    possoFurmarBeber(benderEsquerdo(id));
    possoFurmarBeber(benderDireito(id));
    PrincipalController.mutex.release();
  }

  private void possoFurmarBeber(int idBender){
    // Verifico tres coisas: o bender estah com vontade de beber e fumar
    // e se os benders da esquerda e direita estao dancando, se sim...
    if(controller.getEstado(idBender) == 1 && controller.getEstado(benderEsquerdo(idBender)) != 2 && controller.getEstado(benderDireito(idBender)) != 2){
      controller.mudarImgGarfos(idBender, true);  // retiro os "garfos" da mesa
      controller.setEstado(idBender, 2);          // mudo o estado para "bebendo e fumando"
      PrincipalController.controleGarfos[idBender].release(); // e libero a minha regiao critica dos garfos
    }
  }

  // Calculo para obter o id do bender da esquerda e da direita
  private int benderEsquerdo(int id) {
    return (id - 1 + controller.getNBenders()) % controller.getNBenders();
  } 

  private int benderDireito(int id) {
    return (id + 1 + controller.getNBenders()) % controller.getNBenders();
  } 
  // --------------------------------------------

  private void dancar() throws InterruptedException{
    controller.adicionarImgBender(Paths.BENDER_DANCANDO, id, posicaoXInicial);
    Thread.sleep(controller.getVelocidade(id));
  }

  private void fumarBeber() throws InterruptedException{
    controller.adicionarImgBender(Paths.BENDER_FUMANDO_BEBENDO, id, posicaoXInicial);
    Thread.sleep(controller.getVelocidade(id));
  }
}
