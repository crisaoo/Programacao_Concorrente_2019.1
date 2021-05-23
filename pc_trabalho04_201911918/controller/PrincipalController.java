/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 08/04/2021
* Ultima alteracao: 15/04/2021
* Nome: PrincipalController.java
* Funcao: Controlar toda a interface, iniciar as minhas threads e verificar a area de interseccao entre as threads
*************************************************************** */

package controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import threads.Vaca1Thread;
import threads.Vaca2Thread;

public class PrincipalController implements Initializable {
  // Atributos responsaveis pela trilha sonora da aplicacao
  @FXML private Text txtVolume;
  @FXML private MediaPlayer musica;
  private int volume;

  // Imagens das vacas
  @FXML public ImageView imgViewVaca1;
  @FXML public ImageView imgViewVaca2;

  // Exibicao da velocidade das vacas
  @FXML public Text txtVelocidadeVaca1;
  @FXML public Text txtVelocidadeVaca2;

  // Threads das vacas
  public static Vaca1Thread vaca1;
  public static Vaca2Thread vaca2;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Instancio as minhas vacas
    vaca1 = new Vaca1Thread(this);
    vaca2 = new Vaca2Thread(this);

    // Inicio as minhas threads (vacas) 
    vaca1.start();
    vaca2.start();

    // Inicio a musica
    volume = 2;
    tocarTrilhaSonora();
  }


  // Meodos responsaveis por tocar e controlar a trilha sonora da aplicacao
  private void tocarTrilhaSonora(){
    Media midia = new Media(Paths.get("assets/sounds/trilha_sonora.mp3").toUri().toString());
    musica = new MediaPlayer(midia);
    musica.setVolume((double) volume/10);
    musica.setCycleCount(MediaPlayer.INDEFINITE);
    musica.play();
    txtVolume.setText("Volume: " + volume);
  }

  @FXML public void aumentarVolume(ActionEvent event){
    if(volume < 10){
      volume++;
      musica.setVolume((double) volume/10);
      txtVolume.setText("Volume: " + volume);
    }
  }

  @FXML public void diminuirVolume(ActionEvent event){
    if(volume > 0){
      volume--;
      if(volume == 0)
        musica.setVolume(0.0);
      else
        musica.setVolume((double) volume/10);
      
      txtVolume.setText("Volume: " + volume);
    }
  }
  // ----------------------------------------------------------------


  // Adicionar as imagens das vacas
  public void adicionarVaca1(String vaca){
    imgViewVaca1.setImage(new Image(vaca));
    imgViewVaca1.setX(0);
    imgViewVaca1.setY(0);
  }

  public void adicionarVaca2(String vaca){
    imgViewVaca2.setImage(new Image(vaca));
    imgViewVaca2.setX(0);
    imgViewVaca2.setY(0);
  }
  // ----------------------------------------------------------------
  

  // Controlar a velocidade das vacas
  @FXML public void aumentarVelocidadeVaca1(ActionEvent event){
    vaca1.aumentarVelocidade();
  }
  
  @FXML public void diminuirVelocidadeVaca1(ActionEvent event){
    vaca1.diminuirVelocidade();
  }

  @FXML public void aumentarVelocidadeVaca2(ActionEvent event){
    vaca2.aumentarVelocidade();
  }
  
  @FXML public void diminuirVelocidadeVaca2(ActionEvent event){
    vaca2.diminuirVelocidade();
  }

  // ----------------------------------------------------------------
  

  // Controle das regioes criticas
  
  // Verifico se posso acessar a regiao critica de cada vaca (lembrando q cada vaca tem suas RCs diferente)
  // esse metodo serve para ambas regioes criticas de ambas as vacas, pois cada uma armazena os dados da sua propria RC
  public boolean possoAcessarRC(int vaca, int rc[]){
    if(vaca == 1){
      // Ambas as vacas estao com posicoes diferentes no mapa, entao utilizo um metodo auxiliar para corrigir isso
      int posVaca2 = posicaoVaca2((int)imgViewVaca2.getY());
      if( posVaca2 >= rc[0] && posVaca2 <= rc[1]) // Verifico se a vaca oposta estah dentro da regiao critica
        return false; // Se o curral (meu RC) estiver ocupado, retorno false
      else  
        return true; // Se a vaca oposta ja saiu do curral, retorno true
    }
    else if(vaca == 2){
      int posVaca1 = posicaoVaca1((int)imgViewVaca1.getY());
      if( posVaca1 <= rc[0] && posVaca1 >= rc[1]) 
        return false;
      else
        return true;
    }
    return false;
  }
 
  // Logica para converter a posicao da vaca 2 para ficar equivalente a da vaca 1
  private int posicaoVaca2(int y){
    return 520 + y;
  }

  // Logica para converter a posicao da vaca 1 para ficar equivalente a da vaca 2
  private int posicaoVaca1(int y){
    return y - 520;
  }
  // ----------------------------------------------------------------
}