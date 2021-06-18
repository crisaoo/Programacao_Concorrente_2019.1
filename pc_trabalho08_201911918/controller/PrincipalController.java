/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 09/06/2021
* Ultima alteracao: 18/06/2021
* Nome: PrincipalController.java
* Funcao: O principal controlador, ele eh responsavel por controlar as threads e a interface
*************************************************************** */

package controller;

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
import threads.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;


public class PrincipalController implements Initializable {
  private int volume;
  public static NezukoThread nezukoCinza, nezukoMarrom, nezukoRosa, nezukoAzul, nezukoPreto, nezukoVermelho, nezukoAmarelo, nezukoVerde;
  public static Semaphore rcMarromAzul, rcCinzaAzul, rcAmareloAzul, rcPretoAzul, rcCinzaAmarelo, rcAmareloVerde, rcPretoAmarelo,
    rcPretoVerde, rcVermelhoVerde, rcCinzaVerde, rcVermelhoPreto, rcRosaVermelho, rcCinzaVermelho, rcRosaPreto, rcMarromPreto, 
    rcRosaMarrom, rcCinzaMarrom, rcCinzaRosa, rcCinzaRosaMarrom, rcVermelhoPretoVerde, rcRosaMarromPreto, rcRosaVermelhoPreto;

  @FXML public ImageView imgNezukoCinza, imgNezukoMarrom, imgNezukoRosa, imgNezukoAzul, imgNezukoPreto, imgNezukoVermelho, imgNezukoAmarelo, imgNezukoVerde;
  @FXML public Slider velocidadeCinza, velocidadeMarrom, velocidadeRosa, velocidadeAzul, velocidadePreto, velocidadeVermelho, velocidadeAmarelo, velocidadeVerde;
  @FXML public MediaPlayer musica;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tocarTrilhaSonora();
    instanciarSemaphores();

    nezukoCinza = new NezukoCinza(this);
    nezukoMarrom = new NezukoMarrom(this);
    nezukoRosa = new NezukoRosa(this);
    nezukoAzul = new NezukoAzul(this);
    nezukoPreto = new NezukoPreto(this);
    nezukoVermelho = new NezukoVermelho(this);
    nezukoAmarelo = new NezukoAmarelo(this);
    nezukoVerde = new NezukoVerde(this);

    // adicionar css nos sliders
    adicionarClassesCSS();
    // adicionar listeners para cada slider, isso irah gerar um .class para cada slider
    habilitarSliders();

    // inicio minhas nezukos :D
    nezukoCinza.start();
    nezukoMarrom.start();
    nezukoRosa.start();
    nezukoAzul.start();
    nezukoPreto.start();
    nezukoVermelho.start();
    nezukoAmarelo.start();
    nezukoVerde.start();
  }

  public void adicionarImgNezuko(String quadra, String path, double x, double y){
    switch(quadra){
      case "Cinza":
        imgNezukoCinza.setImage(new Image(path));
        imgNezukoCinza.setX(x);
        imgNezukoCinza.setY(y);
        break;
      case "Marrom":
        imgNezukoMarrom.setImage(new Image(path));
        imgNezukoMarrom.setX(x);
        imgNezukoMarrom.setY(y);
        break;

      case "Rosa":
        imgNezukoRosa.setImage(new Image(path));
        imgNezukoRosa.setX(x);
        imgNezukoRosa.setY(y);
        break;
      case "Azul":
        imgNezukoAzul.setImage(new Image(path));
        imgNezukoAzul.setX(x);
        imgNezukoAzul.setY(y);
        break;
      case "Preto":
        imgNezukoPreto.setImage(new Image(path));
        imgNezukoPreto.setX(x);
        imgNezukoPreto.setY(y);
        break;
      case "Vermelho":
        imgNezukoVermelho.setImage(new Image(path));
        imgNezukoVermelho.setX(x);
        imgNezukoVermelho.setY(y);
        break;
      case "Amarelo":
        imgNezukoAmarelo.setImage(new Image(path));
        imgNezukoAmarelo.setX(x);
        imgNezukoAmarelo.setY(y);
        break;
      case "Verde":
        imgNezukoVerde.setImage(new Image(path));
        imgNezukoVerde.setX(x);
        imgNezukoVerde.setY(y);
    }
  }

  private void instanciarSemaphores(){
    rcAmareloAzul = new Semaphore(1); 
    rcCinzaAzul = new Semaphore(1);
    rcMarromAzul = new Semaphore(1);
    rcPretoAzul = new Semaphore(1);
    rcCinzaAmarelo = new Semaphore(1); 
    rcPretoAmarelo = new Semaphore(1);
    rcAmareloVerde = new Semaphore(1);
    rcPretoVerde = new Semaphore(1);
    rcVermelhoVerde = new Semaphore(1);
    rcCinzaVerde = new Semaphore(1);
    rcVermelhoPreto = new Semaphore(1);
    rcMarromPreto = new Semaphore(1); 
    rcRosaPreto = new Semaphore(1);
    rcRosaVermelho = new Semaphore(1);
    rcCinzaVermelho = new Semaphore(1);
    rcRosaMarrom = new Semaphore(1);
    rcCinzaMarrom = new Semaphore(1);
    rcCinzaRosa = new Semaphore(1);

    rcCinzaRosaMarrom = new Semaphore(1);
    rcVermelhoPretoVerde = new Semaphore(1);
    rcRosaMarromPreto = new Semaphore(1);
    rcRosaVermelhoPreto = new Semaphore(1);
  }

  private void habilitarSliders(){
    velocidadeCinza.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoCinza.setVelocidade((int)velocidadeCinza.getValue());
      }
    });
    
    velocidadeMarrom.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoMarrom.setVelocidade((int)velocidadeMarrom.getValue());
      }
    });

    velocidadeRosa.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoRosa.setVelocidade((int)velocidadeRosa.getValue());
      }
    });

    velocidadeAzul.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoAzul.setVelocidade((int)velocidadeAzul.getValue());
      }
    });

    velocidadePreto.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoPreto.setVelocidade((int)velocidadePreto.getValue());
      }
    });

    velocidadeVermelho.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoVermelho.setVelocidade((int)velocidadeVermelho.getValue());
      }
    });

    velocidadeAmarelo.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoAmarelo.setVelocidade((int)velocidadeAmarelo.getValue());
      }
    });

    velocidadeVerde.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number valorAntigo, Number valorNovo){
        nezukoVerde.setVelocidade((int)velocidadeVerde.getValue());
      }
    });
  }

  private void adicionarClassesCSS(){
    velocidadeCinza.getStyleClass().add("sliderCinza");
    velocidadeMarrom.getStyleClass().add("sliderMarrom");
    velocidadeRosa.getStyleClass().add("sliderRosa");
    velocidadeAzul.getStyleClass().add("sliderAzul");
    velocidadePreto.getStyleClass().add("sliderPreto");
    velocidadeVermelho.getStyleClass().add("sliderVermelho");
    velocidadeAmarelo.getStyleClass().add("sliderAmarelo");
    velocidadeVerde.getStyleClass().add("sliderVerde");
  }

   // Trilha sonora :D
   private void tocarTrilhaSonora(){
    volume = 4;
    Media midia = new Media(java.nio.file.Paths.get("assets/sounds/trilha-sonora.mp3").toUri().toString());
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
}