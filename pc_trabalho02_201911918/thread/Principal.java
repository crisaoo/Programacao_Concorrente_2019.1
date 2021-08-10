/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 25/03/2021
* Ultima alteracao: 28/03/2021
* Nome: Principal.java
* Funcao: Simular uma arvore genealogica utilizando o conceito de threads e a interface grafica da biblioteca swing
*************************************************************** */

import models.Pai;
import models.Filho;
import models.Neto;
import models.Bisneto;

public class Principal { 
  public static void  main(String args[]){
    Pai pai = new Pai();  // Instanciamos e iniciamos a thread pai
    pai.start(); // Ano 2021
    ano();
  }

  public static void ano(){ // Metodo para imprimir a passagem dos anos para acompanhamento
    int ano = 2021;
    for(int i =0; i<90; i++){
      System.out.println("ANO: " + ano);
      try {
        Thread.sleep(1000);
        ano++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}