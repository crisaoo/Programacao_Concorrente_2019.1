/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 03/05/2021
* Ultima alteracao: 06/05/2021
* Nome: VacaThread.java
* Funcao: Super classe das minhas vacas
*************************************************************** */

package threads;

import controller.PrincipalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class VacaThread extends Thread{
  // Preciso do controller principal para acessar as imagens das vacas e poder move-las. Tambem eh necessario 
  // para verificar as regioes criticas
  protected PrincipalController controller; 
  // x e y representam a posicao da minha vaca e a velocidade eh baseada no sleep
  protected int x, y, velocidade;               
  @FXML protected ImageView vaca;   
  protected boolean pararPercurso = false;


  public VacaThread (PrincipalController controller){
    /**
     * No construtor da thread eu pego a referencia do meu controller principal e defino a velocidade inicial dela
    */

    this.controller = controller;
    velocidade = 600;
  }

  // Escolho qual algoritmo serah utilizado para controlar a rc (regiao critica)
  protected void possoAcessarRC(int vaca, int rc){
    switch(controller.algoritmo){
      case 1:
        while (!controller.protocoloDasBandeiras(vaca, rc))
          System.out.print("");
        break;

      case 2:
        if (rc == 1){
          while (PrincipalController.variavelDeTravamentoRC1 != 0)
            System.out.print("");

          PrincipalController.variavelDeTravamentoRC1 = 1;
        } 
        else if (rc == 2){
          while (PrincipalController.variavelDeTravamentoRC2 != 0)
            System.out.print("");

          PrincipalController.variavelDeTravamentoRC2 = 1;
        } 
        break;
      case 3:
        if(rc == 1)
          while(PrincipalController.vezRC1 != vaca)
            System.out.print("");
        else if (rc == 2)
          while(PrincipalController.vezRC2 != vaca)
            System.out.print("");
        break;
      case 4:
        int vacaOposta = 3 - vaca;
        if(rc == 1){
          PrincipalController.interesseRC1[vaca-1] = true;
          PrincipalController.vezRC1 = vaca; 
          while(PrincipalController.vezRC1 == vaca && PrincipalController.interesseRC1[vacaOposta-1])
            System.out.print("");
        }
        else if (rc == 2){
          PrincipalController.interesseRC2[vaca-1] = true;
          PrincipalController.vezRC2 = vaca;
          while(PrincipalController.vezRC2 == vaca && PrincipalController.interesseRC2[vacaOposta-1])
            System.out.print("");
        }
    }
  }

  // Em alguns protocolos, preciso realizar alguma acao apos sair da rc
  protected void jaSaiDaRC(int vaca, int rc){
    switch(controller.algoritmo){
      case 2:
        if(rc == 1)
          PrincipalController.variavelDeTravamentoRC1 = 0;
        else if (rc == 2)
          PrincipalController.variavelDeTravamentoRC2 = 0;
        break;
      case 3:
        if(rc == 1)
          PrincipalController.vezRC1 = (PrincipalController.vezRC1 == 1) ? 2 : 1;
        else if (rc == 2)
          PrincipalController.vezRC2 = (PrincipalController.vezRC2 == 1) ? 2 : 1;
        break;
      case 4: 
        if(rc == 1)
          PrincipalController.interesseRC1[vaca-1] = false;
        else if (rc == 2)
          PrincipalController.interesseRC2[vaca-1] = false;
    }
  }
  // ----------------------------------------------------------------

  // Para aumentar a velocidade da minha vaca, eu diminuo o tempo do sleep
  // O limite maximo da minha velocidade eh 100 milissegundos
  public void aumentarVelocidade(){
    if(velocidade > 100){
      velocidade -= 100;
      Platform.runLater(() -> {
        // Logica para definir como minha velocidade sera apresentada na tela               
        String velocidadeStr = String.valueOf(11 - velocidade / 100); 
        alterarTextoVelocidade(velocidadeStr);
      });
    }
  }

  // Para diminuir a velocidade da minha vaca, eu aumento o tempo do sleep
  // O limite minimo da minha velocidade eh 1000 milissegundos
  public void diminuirVelocidade(){
    if(velocidade < 1000){
      velocidade += 100;
      Platform.runLater(() -> {
        // Logica para definir como minha velocidade sera apresentada na tela       
        String velocidadeStr = String.valueOf(11 - velocidade / 100); 
        alterarTextoVelocidade(velocidadeStr);
      });
    }
  }

  // Cada vaca irah alterar o seu TextVelocidade
  public void alterarTextoVelocidade(String velocidadeStr){
  }

  public void setPararPercurso(boolean pararPercurso){
    this.pararPercurso = pararPercurso;
  }
}