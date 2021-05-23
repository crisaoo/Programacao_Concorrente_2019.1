/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 18/03/2021
* Ultima alteracao: 21/03/2021
* Nome: Principal.cpp
* Funcao: Simulacao de uma arvore genealogica atraves dos comandos fork(para criar novos processos) e sleep(para controlar o tempo)
*************************************************************** */
#include <iostream> // Biblioteca para entrada e saida de dados, apesar de usar apenas para saida
#include <unistd.h> // Biblioteca para o uso do fork

using namespace std;  // Padronizar para n utilizar o "::std" em cada saida ou entrada de dados

int main () { // metodo principal

  pid_t processo; // criamos nosso processo principal

  processo = fork(); // primeiro fork

  /* a cada processo criado, verificamos se ele eh igual a -1, 0 ou diferente.
  * processo == -1: erro, e já encerramos o processo
  * processo == 0: deu certo, entao trabalhamos com ele
  * processo != dos anteriores: o processo filho (processo == 0) foi encerrado, entao voltamos para o processo pai 
  */ 

  if(processo == -1){  
    cout << "Erro\n";
    exit(0);
  }
  else if (processo == 0){ // nasce o pai
    cout << "Nasce o pai  ID: " << getpid() << "\n"; // quando nasce alguem, usei o metodo getpid() para mostrar que é outro processo

    processo = fork(); // nasce o primeiro filho
    if(processo == -1){ 
      cout << "Erro\n";
      exit(0);
    }
    else if(processo == 0){
      sleep(22); // esse processo gerado espera 22 segundos antes de executar a proxima acao
      cout<< "O pai tem o primeiro filho aos 22 anos  ID: " << getpid() << "\n"; 
      
      processo = fork(); // criamos outro processo para o neto (primeiro filho) do pai
      if(processo == -1){
        cout << "Erro\n";
        exit(0);
      }
      else if (processo == 0){
        sleep(16); // esperamos mais 16 segundos para executar a proxima acao
        cout << "O pai eh avo (primeiro filho) aos 38 anos  ID: " << getpid() << "\n";

        processo = fork(); // criamos outro processo para o bisneto (primeiro filho) do pai
        if(processo == -1){
          cout << "Erro\n";
          exit(0);
        }
        else if(processo == 0){
          sleep(30); // esperamos mais 30 segundos para executar a proxima acao
          cout << "O pai eh bisavo (primeiro filho) aos 68 anos  ID: " << getpid() << "\n";
        }
        else { // voltamos ao processo do primeiro neto (primeiro filho) do pai
          sleep(35); // esperamos mais 35 segundos para matar o primeiro neto   
          cout << "O primeiro neto morre aos 35 anos (o pai tem 73 anos)\n";
          sleep(7); // esperamos mais 7 segundos para matar o bisneto (morreu cedo demais, tadinho)
          cout << "O bisneto morre aos 12 anos (o pai tem 80 anos)\n";
          exit(0); // encerramos o processo do neto, como o processo do bisneto foi gerado a partir desse, ele tbm sera encerrado
        }
      }
      else{ // voltamos para o processo do primeiro filho
        sleep(61); // esperamos 61 segundos para matar o primeiro filho
        cout<<"O primeiro filho morre aos 61 anos (o pai tem 83 anos)\n";
        exit(0); // encerramos toda a linhagem de processos do primeiro filho
      }
    }
    else {
      processo = fork(); // criamos outro processo para o filho 2
      if(processo == -1){
        cout << "Erro\n";
        exit(0);
      }
      else if (processo == 0){
        sleep(25); // esperamos 25 segundos para o seu nascimento
        cout<< "O pai tem o segundo filho aos 25 anos  ID: " << getpid() << "\n";

        processo = fork(); // criamos outro processo para o segundo neto (filho 2) do pai 
        if(processo == -1){
          cout << "Erro\n";
          exit(0);
        }
        else if (processo == 0){
          sleep(20); // esperamos 20 segundos para o seu nascimento
          cout << "O pai eh avo (segundo filho) aos 45 anos  ID: " << getpid() << "\n"; 
        }
        else{ // voltamos para o processo do segundo neto
          sleep(53); // esperamos 53 segundos  para a sua morte   
          cout << "O segundo neto morre aos 33 anos (o pai tem 78 anos)\n";
          exit(0); // encerramos o processo do segundo neto do pai
        }
      }
      else{ // voltamos para o processo do filho 2
        processo = fork(); // criamos outro processo para o nascimento do 3 filho (foi a unica maneira q encontrei 
                           // para executar os processos dos 3 filhos ao mesmo tempo)
        if(processo == -1){
          cout << "Erro\n";
          exit(0);
        }
        else if(processo == 0){
          sleep(32); // esperamos 32 segundos para o nascimento do filho 3
          cout << "O pai tem o terceiro filho aos 32 anos  ID: " << getpid() << "\n";
        }
        else{ // ainda no processo do terceiro filho
          sleep(87); // esperamos 87 segundos para matar o terceiro filho
          cout << "O terceiro filho morre aos 55 anos (o pai tem 87)\n";
          exit(0); // encerramos o processo do terceiro filho
        }

        sleep(48); // esperamos 48 segundos para matar o 2 filho
        cout << "O segundo filho morre aos 55 anos (o pai tem 80 anos)\n";
        exit(0); // encerramos toda a linhagem do 2 filho
      }
    }
  }    
  else{ // voltamos ao processo do pai
    sleep(90); // esperamos 90 segundos para matar o pai
    cout << "O pai morre aos 90 anos\n";
    exit(0); // encerramos toda a linhagem do pai
  }
}
