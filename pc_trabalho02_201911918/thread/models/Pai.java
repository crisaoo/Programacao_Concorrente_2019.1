/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 25/03/2021
* Ultima alteracao: 28/03/2021
* Nome: Pai.java
* Funcao: A thread que representa o pai e ira gerar a thread "filho"
*************************************************************** */

package models;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import models.FilhoTipo;

public class Pai extends Thread{
	public static JFrame frame = new JFrame("Arvore Genealogica"); // Criei um frame e um panel para poder exibir graficamente
	public static JPanel panel = new JPanel();

	@Override		
	public void run(){ 	// Metodo obrigatorio para classes que extendem a classe Thread
		try { // Try catch pois ha a possibilidade de gerar uma InterruptedExecption ocasionada pelo Thread.sleep()
			//Configuracoes da interface grafica
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				panel.setPreferredSize(new Dimension(780, 700));
				frame.add(panel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				panel.setBackground(Color.WHITE);

				// Criei uma imagem para representar o pai e configurei para aparecer na tela em tal posicao
				ImageIcon paiIcon = new ImageIcon(getClass().getResource("/assets/images/pai.png"));
		    JLabel pai = new JLabel(paiIcon); 
		    panel.add(pai);
		    pai.setBounds(300, 340, 144, 117);

				frame.setVisible(true);		
		    frame.setResizable(false);	

				Thread.sleep(22000);	// Ano 2043
				Filho filho1 = new Filho(FilhoTipo.PRIMEIRO); // Nasce o primeiro filho
				filho1.start();

				Thread.sleep(3000);	// Ano 2046
				Filho filho2 = new Filho(FilhoTipo.SEGUNDO); // Nasce o segundo filho
				filho2.start();

				Thread.sleep(7000);	// Ano 2053
				Filho filho3 = new Filho(FilhoTipo.TERCEIRO); // Nasce o terceiro filho
				filho3.start();

				Thread.sleep(58000); // Ano 2111
				// Criei uma nova image para representar a morte do pai
				paiIcon = new ImageIcon(getClass().getResource("/assets/images/lapide.png")); // Morte do pai
				pai.setIcon(paiIcon);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
