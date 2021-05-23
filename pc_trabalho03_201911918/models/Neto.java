/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 25/03/2021
* Ultima alteracao: 28/03/2021
* Nome: Neto.java
* Funcao: A thread que representa o neto e talvez gere a threads "bisneto"
*************************************************************** */

package models;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static models.Pai.panel;

public class Neto extends Thread{
  private int posicaoX, posicaoY, idadeMorte, nascimentoBisneto;
  private boolean bisneto;
  private String urlImagem;

  public Neto (FilhoTipo filhoTipo){ // Mesma logica do construtor dos filhos
    if(filhoTipo.equals(FilhoTipo.PRIMEIRO)){
      posicaoX = 80;
      posicaoY = 332;
      nascimentoBisneto = 30 * 1000;
      idadeMorte = (35 - 30) * 1000;
      urlImagem = "/neto1.png";
      bisneto = true;
    }
    else if(filhoTipo.equals(FilhoTipo.SEGUNDO)){
      posicaoX = 310;
      posicaoY = 340;
      idadeMorte = 33 * 1000;
      urlImagem = "/neto2.png";
      bisneto = false;
    }
    else{
      throw new IllegalStateException("Erro ao gerar Neto.");
    }
  }

  @Override
  public void run(){
    try {
      // Criei a imagem do filho e adicionei na tela
      ImageIcon netoIcon = new ImageIcon(getClass().getResource("/assets/images" + urlImagem));
      JLabel neto = new JLabel(netoIcon);
      panel.add(neto);
      neto.setBounds(posicaoX, posicaoY, 144, 117);

      if(bisneto){ // Se o neto gerar o bisneto, entao criarei uma nova thread para o bisneto
        Thread.sleep(nascimentoBisneto); // Ano 2089 para o nascimento do bisneto
        Bisneto bisneto = new Bisneto(); 
        bisneto.start();
      }

      Thread.sleep(idadeMorte); // Ano 2094 e 2099 para a morte do primeiro e segundo neto respectivamente
      netoIcon = new ImageIcon(getClass().getResource("/assets/images/lapide.png"));
      neto.setIcon(netoIcon);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
