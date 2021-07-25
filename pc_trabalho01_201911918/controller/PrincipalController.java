/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 24/07/2021
* Ultima alteracao: 25/07/2021
* Nome: PrincipalController.java
* Funcao: Controlar a minha aplicacao
*************************************************************** */
package controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import models.Path;

public class PrincipalController implements Initializable {
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private Button btnDeixaAlagar, btnFolhas, btnFreeBird, btnParadiseCity, btnRoxanne, btnRubricas, btnTelegrama, 
    btnThatsMyWay, btnVaiMeDandoCorda, btnPlayPause, btnReload;
  @FXML
  private ImageView imgMusica;
  @FXML
  private MediaPlayer mediaPlayer;
  @FXML
  private Slider sliderVolume;

  private boolean isPausado;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scrollPane.setBackground(null);
    isPausado = false;
    sliderVolume.setDisable(true);
    inicializarThumbnails();
  }

  @FXML
  public void tocarMusica(ActionEvent event){
    btnPlayPause.setGraphic(new ImageView(new Image(Path.PAUSE_BTN)));
    btnPlayPause.setDisable(false);
    btnReload.setDisable(false);

    habilitarSlider();

    Image image = null;
    Media media = null;

    // ps: Paths (lib do java)  != Path (model do projeto)
    switch (((Button)event.getSource()).getId()) {
      case "btnDeixaAlagar":
        image = new Image(Path.DEIXA_ALAGAR_IMG);
        media = new Media(Paths.get(Path.DEIXA_ALAGAR_MUSICA).toUri().toString());
        break;
        
      case "btnFolhas":
        image = new Image(Path.FOLHAS_IMG);
        media = new Media(Paths.get(Path.FOLHAS_MUSICA).toUri().toString());
        break;

      case "btnFreeBird":
        image = new Image(Path.FREE_BIRD_IMG);
        media = new Media(Paths.get(Path.FREE_BIRD_MUSICA).toUri().toString());
        break;

      case "btnParadiseCity":
        image = new Image(Path.PARADISE_CITY_IMG);
        media = new Media(Paths.get(Path.PARADISE_CITY_MUSICA).toUri().toString());
        break;

      case "btnRoxanne":
        image = new Image(Path.ROXANNE_IMG);
        media = new Media(Paths.get(Path.ROXANNE_MUSICA).toUri().toString());
        break;

      case "btnRubricas":
        image = new Image(Path.RUBRICAS_IMG);
        media = new Media(Paths.get(Path.RUBRICAS_MUSICA).toUri().toString());
        break;

      case "btnTelegrama":
        image = new Image(Path.TELEGRAMA_IMG);
        media = new Media(Paths.get(Path.TELEGRAMA_MUSICA).toUri().toString());
        break;

      case "btnThatsMyWay":
        image = new Image(Path.THATS_MY_WAY_IMG);
        media = new Media(Paths.get(Path.THATS_MY_WAY_MUSICA).toUri().toString());
        break;

      case "btnVaiMeDandoCorda":
        image = new Image(Path.VAI_ME_DANDO_CORDA_IMG);
        media = new Media(Paths.get(Path.VAI_ME_DANDO_CORDA_MUSICA).toUri().toString());
    }

    // Programacao defensiva
    if(!Objects.isNull(image) && !Objects.isNull(media)){
      imgMusica.setImage(image);
      
      if(!Objects.isNull(mediaPlayer))
        mediaPlayer.stop();

      mediaPlayer = new MediaPlayer(media);
      sliderVolume.setValue(50);
      mediaPlayer.play();
    }
  }

  @FXML
  public void restartarMusica(ActionEvent event){
    mediaPlayer.seek(Duration.seconds(0.0));
  }

  @FXML
  public void pausarOuTocarMusica(ActionEvent event){
    // Se estiver pausado, entao eu irei tocar a musica e mudar sua imagem, e vice e versa caso a musica esteja tocando
    if(isPausado){
      btnPlayPause.setGraphic(new ImageView(new Image(Path.PAUSE_BTN)));
      mediaPlayer.play();  
    } else {
      btnPlayPause.setGraphic(new ImageView(new Image(Path.PLAY_BTN)));
      mediaPlayer.pause(); 
    }
    
    // A cada clique eu mudo o estado da musica (pausado/tocando)
    isPausado = isPausado ? false : true;
  }

  private void inicializarThumbnails(){
    // Defino uma imagem para cada botao (nao utilizo imagesView pois nao ha como eu definir um evento para elas)
    btnDeixaAlagar.setGraphic(new ImageView(new Image(Path.DEIXA_ALAGAR_THUMBNAIL)));
    btnFolhas.setGraphic(new ImageView(new Image(Path.FOLHAS_THUMBNAIL)));
    btnFreeBird.setGraphic(new ImageView(new Image(Path.FREE_BIRD_THUMBNAIL)));
    btnParadiseCity.setGraphic(new ImageView(new Image(Path.PARADISE_CITY_THUMBNAIL)));
    btnRoxanne.setGraphic(new ImageView(new Image(Path.ROXANNE_THUMBNAIL)));
    btnRubricas.setGraphic(new ImageView(new Image(Path.RUBRICAS_THUMBNAIL)));
    btnTelegrama.setGraphic(new ImageView(new Image(Path.TELEGRAMA_THUMBNAIL)));
    btnThatsMyWay.setGraphic(new ImageView(new Image(Path.THATS_MY_WAY_THUMBNAIL)));
    btnVaiMeDandoCorda.setGraphic(new ImageView(new Image(Path.VAI_ME_DANDO_CORDA_THUMBNAIL)));
    
    // Como eu quero apenas aplicar css em certos botoes e nao em todos, tenho q fazer isso manualmente
    estilizarThumbnails();

    // Configuro o layout dos meus botoes e, inicialmente, deixo-os desabilitados
    btnPlayPause.setBackground(null);
    btnPlayPause.setGraphic(new ImageView(new Image(Path.PLAY_BTN)));
    btnPlayPause.setDisable(true);

    btnReload.setBackground(null);
    btnReload.setGraphic(new ImageView(new Image(Path.RELOAD_BTN)));
    btnReload.setDisable(true);
  }

  private void estilizarThumbnails(){
    btnDeixaAlagar.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnFolhas.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnFreeBird.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnParadiseCity.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnRoxanne.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnRubricas.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnTelegrama.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnThatsMyWay.getStylesheets().add(Path.THUMBNAILS_CSS);
    btnVaiMeDandoCorda.getStylesheets().add(Path.THUMBNAILS_CSS);
  } 

  private void habilitarSlider(){
    sliderVolume.setDisable(false);
    sliderVolume.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        mediaPlayer.setVolume(sliderVolume.getValue()/100);
      }
    });
    
  }
}