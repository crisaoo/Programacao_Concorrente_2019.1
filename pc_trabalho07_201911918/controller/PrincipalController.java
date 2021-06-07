/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 31/05/2021
* Ultima alteracao: 06/06/2021
* Nome: PrincipalController.java
* Funcao: Controlar minha interface grafica
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.threads.Bender;
import models.util.Paths;

public class PrincipalController implements Initializable{
  private int velocidade[];       // Velocidade de cada bender serah diferente
  private int nBenders;           // Numero de benders
  private Bender bender[];        // Guardo minhas threads em um vetor, jah que serah um numero fixo de threads (nBenders)
  private int estado[];           // 0: Dancando, 1: Vontade de beber e fumar,  2: Bebendo e fumando
  public static Semaphore mutex;  //controla minha regiao critica  
  public static Semaphore controleGarfos []; // Cada bender irah tentar pegar o seu cigarro ou sua bebida
  private int volume;

  // Componentes que serao exibidos na tela: imageViews e labels
  @FXML private ImageView imgBender0, imgBender1, imgBender2, imgBender3, imgBender4, imgBender5;
  // Os garfos foram abstraidos para cigarros e bebidas, mas decidi manter a nomenclatura para facilitar 
  @FXML private ImageView imgGarfo0, imgGarfo1, imgGarfo2, imgGarfo3, imgGarfo4, imgGarfo5;
  // Terei um slider para controlar a velocidade de cada bender
  @FXML private Slider sliderVel0, sliderVel1, sliderVel2, sliderVel3, sliderVel4, sliderVel5;  
  @FXML private MediaPlayer musica;
 
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Inicializo os meu vetores e semaforos
    nBenders = 6;
    bender = new Bender[nBenders];
    velocidade = new int[nBenders];
    estado = new int[nBenders];
    mutex = new Semaphore(1);
    controleGarfos = new Semaphore[nBenders];

    habilitarSlidersDeVelocidade();

    // Inicio os valores inicias da minha velocidade, instancio e rodo meus benders
    for(int id = 0; id < nBenders; id++ ){  
      controleGarfos[id] = new Semaphore(0);
      velocidade[id] = 5;
      bender[id] = new Bender(this, id);
      bender[id].start();
    }

    volume = 3;
    tocarTrilhaSonora();
  }

  // Trilha sonora :D
  private void tocarTrilhaSonora(){
    Media midia = new Media(java.nio.file.Paths.get("assets/sounds/intro.mp3").toUri().toString());
    musica = new MediaPlayer(midia);
    musica.setVolume((double) volume/10);
    musica.setCycleCount(MediaPlayer.INDEFINITE);
    musica.play();
  }
  
  @FXML public void aumentarVolume(ActionEvent event){
    if(volume < 10){
      volume++;
      musica.setVolume((double) volume/10);
    }
  }

  @FXML public void diminuirVolume(ActionEvent event){
    if(volume > 0){
      volume--;
      if(volume == 0)
        musica.setVolume(0.0);
      else
        musica.setVolume((double) volume/10);
    }
  }
  // --------------------------------------------

  // Gerenciar as imagens na minha interface
  public void adicionarImgBender(String path, int idBender, int posicaoXInicial){
    switch(idBender){
      case 0:
        imgBender0.setImage(new Image(path));

        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender0.setFitHeight(100);
          imgBender0.setX(posicaoXInicial - 20);
        } else{
          imgBender0.setX(posicaoXInicial);
        }
        break;

      case 1:
        imgBender1.setImage(new Image(path)); 
        
        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender1.setFitHeight(100);
          imgBender1.setX(posicaoXInicial - 20);
        }
        else{
          imgBender1.setX(posicaoXInicial);
        }
        break;
        
      case 2:
        imgBender2.setImage(new Image(path));
        
        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender2.setFitHeight(100);
          imgBender2.setX(posicaoXInicial - 20);
        } else{
          imgBender2.setX(posicaoXInicial);
        }
        break;

      case 3:
        imgBender3.setImage(new Image(path));
        
        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender3.setFitHeight(100);
          imgBender3.setX(posicaoXInicial - 20);
        } else{
          imgBender3.setX(posicaoXInicial);
        }
        break;

      case 4:
        imgBender4.setImage(new Image(path));
        
        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender4.setFitHeight(100);
          imgBender4.setX(posicaoXInicial - 20);
        } else{
          imgBender4.setX(posicaoXInicial);
        }
        break;

      case 5:
        imgBender5.setImage(new Image(path));

        if(path.equals(Paths.BENDER_FUMANDO_BEBENDO)){
          imgBender5.setFitHeight(100);
          imgBender5.setX(posicaoXInicial - 20);
        } else{
          imgBender5.setX(posicaoXInicial);
        }
        break;
    }
  }
  
  // Pego os "garfos" da esquerda e direita e defino a visibilidade deles de acordo com valor passado no parametro
  // Tambem defino quais garfos serao "pegos" de acordo com o idBender
  public void mudarImgGarfos(int idBender, boolean pegarGarfos){
    switch (idBender) {
      case 0:
        imgGarfo0.setVisible(!pegarGarfos);
        imgGarfo1.setVisible(!pegarGarfos);
        break;
      
      case 1:
        imgGarfo1.setVisible(!pegarGarfos);
        imgGarfo2.setVisible(!pegarGarfos);
        break;
      
      case 2:
        imgGarfo2.setVisible(!pegarGarfos);
        imgGarfo3.setVisible(!pegarGarfos);
        break;
      
      case 3:
        imgGarfo3.setVisible(!pegarGarfos);
        imgGarfo4.setVisible(!pegarGarfos);
        break;
      
      case 4:
        imgGarfo4.setVisible(!pegarGarfos);
        imgGarfo5.setVisible(!pegarGarfos);
        break;
      
      case 5:
        imgGarfo5.setVisible(!pegarGarfos);
        imgGarfo0.setVisible(!pegarGarfos);
        break;
    }
  }
  // --------------------------------------------
  
  // Gerenciar a velocidade de cada bender (tanto para dancar, como para beber e fumar)
  private void habilitarSlidersDeVelocidade(){
    sliderVel0.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[0] = (int) sliderVel0.getValue();
      }
    });

    sliderVel1.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[1] = (int) sliderVel1.getValue();
      }
    });
    
    sliderVel2.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[2] = (int) sliderVel2.getValue();
      }
    });
    sliderVel3.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[3] = (int) sliderVel3.getValue();
      }
    });
    sliderVel4.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[4] = (int) sliderVel4.getValue();
      }
    });
    sliderVel5.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        velocidade[5] = (int) sliderVel5.getValue();
      }
    });
  }
  // --------------------------------------------
  
  // Getters e setters
  public int getVelocidade(int idBender){
    // System.out.println(velocidade[0]);
    return velocidade[idBender] * 1000; // O id dos meus benders comecam com 1, mas o vetor comeca com 0 
  }

  public Integer getPosicaoXInicial(int idBender){
    switch (idBender) {
      case 0:
        return (int) imgBender0.getX();

      case 1:
        return (int) imgBender1.getX();

      case 2:
        return (int) imgBender2.getX();
      
      case 3:
        return (int) imgBender3.getX();

      case 4:
        return (int) imgBender4.getX();
      
      case 5:
        return (int) imgBender5.getX();

      default:
        return null;
    }
  }
  
  public int getEstado(int idBender){
    return estado[idBender];
  }

  public void setEstado(int idBender, int estado){
    this.estado[idBender] = estado;
  }

  public int getNBenders(){
    return nBenders;
  }
  // --------------------------------------------
}