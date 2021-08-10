/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 25/03/2021
* Ultima alteracao: 28/03/2021
* Nome: Bisneto.java 
* Funcao: Representa o bisneto
*************************************************************** */
package models;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static models.Pai.panel;

public class Bisneto extends Thread{
  public void run(){
    try {
      ImageIcon bisnetoIcon = new ImageIcon(getClass().getResource("/assets/images/bisneto.png"));
      JLabel bisneto = new JLabel(bisnetoIcon);
      panel.add(bisneto);
      bisneto.setBounds(80, 495, 144, 117);

      Thread.sleep(12000); // Ano 2101 para a morte do bisneto (que era eu)
      bisnetoIcon = new ImageIcon(getClass().getResource("/assets/images/lapide.png"));
      bisneto.setIcon(bisnetoIcon);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
  }
}
