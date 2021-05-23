/****************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 13/05/2021
* Ultima alteracao: 19/05/2021
* Nome: Principal.java
* Funcao: Iniciar minhas threads, controlar a velocidade de execucao do programa, gerenciar o numero de 
  consumidores e produtores e controlar minha GUI
****************************************************************/

package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import threads.ProdutorThread;
import threads.ConsumidorThread;

public class PrincipalController implements Initializable {
  // Threads
  private ProdutorThread produtor1, produtor2, produtor3;
  private ConsumidorThread consumidor1, consumidor2, consumidor3, consumidor4, consumidor5, consumidor6;
  public static Semaphore balcaoVazio; // Referente aa minha regiao critica (balcao) estah vazia
  public static Semaphore balcaoCheio; // Referente aa minha regiao critica (balcao) estah cheia
  public static Semaphore mutex; // Se posso acessar minha regiao critica (balcao)

  private int nProdutores;  // numero minimo: 1, numero maximo: 3
  private int nConsumidores;  // numero minimo: 1, numero maximo: 6
  private int velocidadeConsumidor, velocidadeProdutor;

  // Componentes para serem exibidos na tela: labels, sliders, images e buttons
  @FXML private Label lblProdutores, lblConsumidores, lblVelocidadeProdutor, lblVelocidadeConsumidor;
  @FXML private Button bttnAdicionarProdutor, bttnRemoverProdutor, bttnAdicionarConsumidor, 
    bttnRemoverConsumidor, bttnIniciar;
  @FXML private Slider sliderVelocidadeProdutor, sliderVelocidadeConsumidor;

  @FXML private ImageView imgProdutor1, imgProdutor2, imgProdutor3;
  @FXML private ImageView imgProdutorProduzindo1, imgProdutorProduzindo2, imgProdutorProduzindo3;
  @FXML private ImageView imgConsumidor1, imgConsumidor2, imgConsumidor3, imgConsumidor4, imgConsumidor5, 
    imgConsumidor6;
  @FXML private ImageView imgConsumidorRetirando1, imgConsumidorRetirando2, imgConsumidorRetirando3, 
    imgConsumidorRetirando4, imgConsumidorRetirando5, imgConsumidorRetirando6;

  @FXML private ImageView imgCervejaBalcao1, imgCervejaBalcao2, imgCervejaBalcao3, imgCervejaBalcao4, 
    imgCervejaBalcao5, imgCervejaBalcao6;
  @FXML private ImageView imgCervejaMesa1, imgCervejaMesa2, imgCervejaMesa3, imgCervejaMesa4, 
    imgCervejaMesa5, imgCervejaMesa6;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Antes de iniciar, eu quero desabilitar alguns controles e instanciar minhas threads obrigatorias
    habilitarOuDesabilitarControles(false);
    balcaoVazio = new Semaphore(6); // Comeca com 6 posicoes vazias
    balcaoCheio = new Semaphore(0); // Comeca com 0 posicoes cheias
    mutex = new Semaphore(1); // apenas 1 processo pode acessar meu balcao por vez
    produtor1 = new ProdutorThread(this, 1);
    consumidor1 = new ConsumidorThread(this, 1);
  }

  @FXML public void iniciar(ActionEvent event){
    // desabilito meu botao de iniciar
    bttnIniciar.setDisable(true);
    // reabilito meus controles
    habilitarOuDesabilitarControles(true);

    // No minimo, sempre terah 1 produtor e 1 consumidor
    nProdutores = 1;
    nConsumidores = 1;
    lblProdutores.setText("Produtores: " + nProdutores);
    lblConsumidores.setText("Consumidores: " + nConsumidores);

    // Habilito o listener para meus sliders
    alterarVelocidadeProdutor();
    alterarVelocidadeConsumidor();

    // Valores inicias de velocidade
    velocidadeProdutor = 11 - (int)sliderVelocidadeProdutor.getValue(); 
    velocidadeConsumidor = 11 - (int)sliderVelocidadeConsumidor.getValue();

    produtor1.start();
    consumidor1.start();
  }

  // Gerenciar quantidade de produtores
  @FXML public void adicionarProdutor(ActionEvent event){
    if(nProdutores < 3)
      nProdutores++;
    lblProdutores.setText("Produtores: " + nProdutores);

    if (nProdutores == 2){
      produtor2 = new ProdutorThread(this, 2);
      produtor2.start();
    }
    else if (nProdutores == 3){
      // Programacao segura: para evitar que eu instancie novamente minha thread (as anteriores nao hah necessidade)
      if(Objects.isNull(produtor3)){
        produtor3 = new ProdutorThread(this, 3);
        produtor3.start();
      }
    }


  }

  @FXML public void removerProdutor(ActionEvent event){
    encerrarProdutor(nProdutores);

    if(nProdutores > 1)
      nProdutores--;
    lblProdutores.setText("Produtores: " + nProdutores);

  }

  // Gerenciar quantidade de consumidores
  @FXML public void adicionarConsumidor(ActionEvent event){
    if(nConsumidores < 6)
      nConsumidores++;
    lblConsumidores.setText("Consumidores: " + nConsumidores);

    switch (nConsumidores) {
      case 2:
        consumidor2 = new ConsumidorThread(this, 2);
        consumidor2.start();
        break;
      case 3:
        consumidor3 = new ConsumidorThread(this, 3);
        consumidor3.start();
        break;
      case 4:
        consumidor4 = new ConsumidorThread(this, 4);
        consumidor4.start();
        break;
      case 5:
        consumidor5 = new ConsumidorThread(this, 5);
        consumidor5.start();
        break;
      case 6:
        // Programacao segura: para evitar que eu instancie novamente minha thread (as anteriores nao hah necessidade)
        if(Objects.isNull(consumidor6)){
          consumidor6 = new ConsumidorThread(this, 6);
          consumidor6.start();
        }
        break;
    
    }
  }

  @FXML public void removerConsumidor(ActionEvent event){
    encerrarConsumidor(nConsumidores);

    if(nConsumidores > 1)
      nConsumidores--;
    lblConsumidores.setText("Consumidores: " + nConsumidores);
  }

  // Adicionar imagem nas imageViews
  public ImageView adicionarImagemProdutor(int produtor){
    Image image = new Image("/assets/images/produtor.png");

    switch(produtor){
      case 1:
        imgProdutor1.setImage(image);
        return imgProdutor1;
      case 2:
        imgProdutor2.setImage(image);
        return imgProdutor2;
      case 3:
        imgProdutor3.setImage(image);
        return imgProdutor3;
    }
    return null;
  }
  
  public ImageView adicionarImagemConsumidor(int consumidor){
    Image image = new Image("/assets/images/consumidor.png");

    switch(consumidor){
      case 1:
        imgConsumidor1.setImage(image);
        return imgConsumidor1;
      case 2:
        imgConsumidor2.setImage(image);
        return imgConsumidor2;
      case 3:
        imgConsumidor3.setImage(image);
        return imgConsumidor3;
      case 4:
        imgConsumidor4.setImage(image);
        return imgConsumidor4;
      case 5:
        imgConsumidor5.setImage(image);
        return imgConsumidor5;
      case 6:
        imgConsumidor6.setImage(image);
        return imgConsumidor6;
    }

    return null;
  }
  
  public void adicionarCervejaNoBalcao(){
    Image image = new Image("/assets/images/cerveja.png");

    if (Objects.isNull(imgCervejaBalcao1.getImage()))
      imgCervejaBalcao1.setImage(image);
    else if (Objects.isNull(imgCervejaBalcao2.getImage()))
      imgCervejaBalcao2.setImage(image);
    else if (Objects.isNull(imgCervejaBalcao3.getImage()))
      imgCervejaBalcao3.setImage(image);
    else if (Objects.isNull(imgCervejaBalcao4.getImage()))
      imgCervejaBalcao4.setImage(image);
    else if (Objects.isNull(imgCervejaBalcao5.getImage()))
      imgCervejaBalcao5.setImage(image);
    else if (Objects.isNull(imgCervejaBalcao6.getImage()))
      imgCervejaBalcao6.setImage(image);
  }
  
  public void adicionarCervejaNaMesa(int nConsumidor){
    Image image = new Image("/assets/images/cerveja.png");

    switch (nConsumidor) {
      case 1:
        imgCervejaMesa1.setImage(image);
        imgCervejaBalcao1.setImage(null);
        break;
      case 2:
        imgCervejaMesa2.setImage(image);
        imgCervejaBalcao2.setImage(null);
        break;
      case 3:
        imgCervejaMesa3.setImage(image);
        imgCervejaBalcao3.setImage(null);
        break;
      case 4:
        imgCervejaMesa4.setImage(image);
        imgCervejaBalcao4.setImage(null);
        break;
      case 5:
        imgCervejaMesa5.setImage(image);
        imgCervejaBalcao5.setImage(null);
        break;
      case 6:
        imgCervejaMesa6.setImage(image);
        imgCervejaBalcao6.setImage(null);
        break;
    }
  }

  public void removerCervejaDaMesa(int nConsumidor){
    switch (nConsumidor) {
      case 1:
        if(!Objects.isNull(imgCervejaMesa1))
          imgCervejaMesa1.setImage(null);
        break;
      case 2:
        if(!Objects.isNull(imgCervejaMesa2))
          imgCervejaMesa2.setImage(null);
        break;
      case 3:
        if(!Objects.isNull(imgCervejaMesa3))
          imgCervejaMesa3.setImage(null);
        break;
      case 4:
        if(!Objects.isNull(imgCervejaMesa4))
          imgCervejaMesa4.setImage(null);
        break;
      case 5:
        if(!Objects.isNull(imgCervejaMesa5))
          imgCervejaMesa5.setImage(null);
        break;
      case 6:
        if(!Objects.isNull(imgCervejaMesa6))
          imgCervejaMesa6.setImage(null);
        break;
    }
  }
  // -------------------------------------------------------


  // Gerenciar a velocidade de execucao atravehs de um listener
  private void alterarVelocidadeProdutor(){
    sliderVelocidadeProdutor.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidadeProdutor = 11 - ((int)sliderVelocidadeProdutor.getValue());
        lblVelocidadeProdutor.setText("Velocidade de Produtor: " + (11 - velocidadeProdutor));
      }
    });
  }

  private void alterarVelocidadeConsumidor(){
    sliderVelocidadeConsumidor.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidadeConsumidor = 11 - ((int)sliderVelocidadeConsumidor.getValue());
        lblVelocidadeConsumidor.setText("Velocidade do Consumidor: " + (11 - velocidadeConsumidor));
      }
    });
  }
  // -------------------------------------------------------
  
  // Verifico se thread nao estah nula, defino sua imageView correspondente como null, interrompo minha thread
  // tambem defino a imagem do produtorProduzindo/consumidorRetirando como nula
  public void encerrarProdutor(int nProdutor){
    switch(nProdutor){
      case 2:
        if (!Objects.isNull(produtor2)){
          produtor2.setVivo(false);
          imgProdutor2.setImage(null);
          imgProdutorProduzindo2.setImage(null);
          produtor2.interrupt();
        } 
        break;

      case 3:
        if (!Objects.isNull(produtor3)){
          produtor3.setVivo(false);
          imgProdutor3.setImage(null);
          imgProdutorProduzindo3.setImage(null);
          produtor3.interrupt();
          produtor3 = null;
        } 
        break;
    }

  }

  public void encerrarConsumidor(int nConsumidor){
    switch(nConsumidor){
      case 2:
        if(!Objects.isNull(consumidor2)){
          imgConsumidor2.setImage(null);
          imgConsumidorRetirando2.setImage(null);
          consumidor2.setVivo(false);
          consumidor2.interrupt();
        }
        break;
      case 3:
        if(!Objects.isNull(consumidor3)){
          imgConsumidor3.setImage(null);
          imgConsumidorRetirando3.setImage(null);
          consumidor3.setVivo(false);
          consumidor3.interrupt();
        }
        break;
      case 4:
        if(!Objects.isNull(consumidor4)){
          imgConsumidor4.setImage(null);
          imgConsumidorRetirando4.setImage(null);
          consumidor4.setVivo(false);
          consumidor4.interrupt();
        }
        break;
      case 5:
        if(!Objects.isNull(consumidor5)){
          imgConsumidor5.setImage(null);
          imgConsumidorRetirando5.setImage(null);
          consumidor5.setVivo(false);
          consumidor5.interrupt();
        }
        break;
      case 6:
        if(!Objects.isNull(consumidor6)){
          imgConsumidor6.setImage(null);
          imgConsumidorRetirando6.setImage(null);
          consumidor6.setVivo(false);
          consumidor6.interrupt();
          consumidor6 = null;
        }
        break;
    }

    if(nConsumidor != 1)
      removerCervejaDaMesa(nConsumidor);
  }
  // -------------------------------------------------------

  // Simplesmente desabilito ou habilito a visibildiade delas
  private void habilitarOuDesabilitarControles(boolean habilitar){
    sliderVelocidadeProdutor.setVisible(habilitar);
    sliderVelocidadeConsumidor.setVisible(habilitar);
    bttnAdicionarProdutor.setVisible(habilitar);
    bttnRemoverProdutor.setVisible(habilitar);
    bttnAdicionarConsumidor.setVisible(habilitar);
    bttnRemoverConsumidor.setVisible(habilitar);
    lblProdutores.setVisible(habilitar);
    lblConsumidores.setVisible(habilitar);
    lblVelocidadeProdutor.setVisible(habilitar);
    lblVelocidadeConsumidor.setVisible(habilitar);
  }

  // Getters e Setters
  public int getVelocidadeProdutor(){
    return velocidadeProdutor;
  }

  public int getVelocidadeConsumidor(){
    return velocidadeConsumidor;
  }

  public void setImgProdutorProduzindo(Integer nProdutor, boolean apagarImg){
    // Logica ternaria para decidir se eu apago ou insiro a imagem
    Image imgProdutor = (apagarImg) ? null : new Image("/assets/images/produtor.png");

    switch (nProdutor) {
      case 1:
        imgProdutorProduzindo1.setImage(imgProdutor);
        break;
      case 2:
        imgProdutorProduzindo2.setImage(imgProdutor);
        break;
      case 3:
        imgProdutorProduzindo3.setImage(imgProdutor);
        break;
    }
  }

  public void setImgConsumidorRetirando(Integer nConsumidor, boolean apagarImg){
    // Logica ternaria para decidir se eu apago ou insiro a imagem
    Image imgConsumidor = (apagarImg) ? null : new Image("/assets/images/consumidor.png");

    switch (nConsumidor) {
      case 1:
        imgConsumidorRetirando1.setImage(imgConsumidor);    
        break;
      case 2:
        imgConsumidorRetirando2.setImage(imgConsumidor);    
        break;
      case 3:
        imgConsumidorRetirando3.setImage(imgConsumidor);    
        break;
      case 4:
        imgConsumidorRetirando4.setImage(imgConsumidor);    
        break;
      case 5:
        imgConsumidorRetirando5.setImage(imgConsumidor);    
        break;
      case 6:
        imgConsumidorRetirando6.setImage(imgConsumidor);    
        break;
    }


  }
  // -------------------------------------------------------
}