/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 25/03/2021
* Ultima alteracao: 28/03/2021
* Nome: Filho.java
* Funcao: A thread que representa os filhos e talvez gere a thread "neto"
*************************************************************** */

package models;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static models.Pai.panel;

public class Filho extends Thread{
  // Dados que cada filho (filho1, filho2, filho3) tera
  private int posicaoX, posicaoY, idadeMorte, nascimentoNeto;
  private boolean neto;
  private String urlImagem;
  private FilhoTipo filhoTipo; 

  public Filho(FilhoTipo filhoTipo){    // No construtor eh passado o tipo de filho (enum) para determinar sua posicao 
                                        // na tela, sua imagem, a idade em que ira morrer, se tera um neto e quando ele ira nascer
   if(filhoTipo.equals(FilhoTipo.PRIMEIRO)){
     posicaoX = 80;
     posicaoY = 155;
     nascimentoNeto = 16 * 1000;
     idadeMorte = (61 - 16) * 1000;
     urlImagem = "/filho1.png";
     neto = true;
   }
   else if(filhoTipo.equals(FilhoTipo.SEGUNDO)){
    posicaoX = 310;
    posicaoY = 155;
    nascimentoNeto = 20 * 1000;
    idadeMorte = (55 - 20) * 1000;
    urlImagem = "/filho2.png";
    neto = true;
   }
   else if(filhoTipo.equals(FilhoTipo.TERCEIRO)){
    posicaoX = 530;
    posicaoY = 155;
    idadeMorte = 55 * 1000;
    urlImagem = "/filho3.png";
    neto = false;
   }
   else{  // Caso seja passado um tipo inexistente, lancarei uma IllegalStateException 
     throw new IllegalStateException("Erro ao gerar Filho.");
   }

   this.filhoTipo = filhoTipo; // Guardarei o qual filho estamos criando e inicializarei o neto com a mesma logica
                               // visto que ha dois netos diferentes
  }

  @Override
  public void run(){
    try {
      // Criamos uma imagem para cada filho (primeiro, segundo e terceiro) e adicionamos a interface
      ImageIcon filhoIcon = new ImageIcon(getClass().getResource("/assets/images" + urlImagem)); 
			JLabel filho = new JLabel(filhoIcon); 
			panel.add(filho);
			filho.setBounds(posicaoX, posicaoY, 144, 117);
			
      if(neto){ // Se esse filho gerar um neto, entao iremos criar uma nova thread
        Thread.sleep(nascimentoNeto); // Ano 2059 e 2066 para o nascimento do neto do primeiro e segundo filho respectivamente
        Neto neto = new Neto(filhoTipo);
        neto.start();
      }

      Thread.sleep(idadeMorte); // Ano 2104, 2101 e 2108 para a morte do primeiro, segundo e terceiro filho respectivamente
      filhoIcon = new ImageIcon(getClass().getResource("/assets/images/lapide.png")); // Nova imagem para a morte dos filhos
			filho.setIcon(filhoIcon);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
