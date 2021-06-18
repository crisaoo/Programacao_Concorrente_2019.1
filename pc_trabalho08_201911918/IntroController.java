/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 18/06/2021
* Ultima alteracao: 18/06/2021 
* Nome: IntroController.java
* Funcao: Controlar meu video introdutorio
* Obs: tive que colocar essa classe na raiz para poder utilizar os metodos da classe Principal.java
*************************************************************** */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class IntroController implements Initializable{

	@FXML private MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)  {
		file = new File("assets/videos/intro.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();
		
		// Quando terminar de exibir a intro ou pular ela, eu comeco a exibir de fato o programa princiapl
		mediaPlayer.setOnEndOfMedia(() -> {
			try{
				Principal.mudarParaTelaPrincipal();
			} catch (IOException e){}
		});
	}

	@FXML public void pularIntro(ActionEvent event){
		mediaPlayer.seek(Duration.seconds(24));
	}
}