/* ***************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 08/04/2021
* Ultima alteracao: 06/05/2021
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
import threads.VacaThread;
import threads.Vaca1;
import threads.Vaca2;

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
  public static VacaThread vaca1;
  public static VacaThread vaca2;

  // Irah decidir qual algoritmo serah utilizado para tratar minha regiao critica
  public int algoritmo;

  // - Algoritmo: variavel de travamento
  // variaveis de travamento podem assumar dois valores: 0 = caminho livre, 1 = caminho ocupado 
  public static int variavelDeTravamentoRC1;
  public static int variavelDeTravamentoRC2;

  // - Algoritmo de estrita alternancia e solucao de Peterson
  // variaveis que decidem qual processo irah acessar a rc: 1 = vaca1,  2 = vaca2
  public static int vezRC1;
  public static int vezRC2;
  // Se a vaca tem interesse de entrar na região critica: interesse[0] = vaca 1,   interesse[1] = vaca 2
  public static boolean interesseRC1 [];  
  public static boolean interesseRC2 [];  

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Irei deixar o Protocolo das bandeiras como o algoritmo padrao
    algoritmo = 1;

    // Instancio as minhas vacas
    vaca1 = new Vaca1(this);
    vaca2 = new Vaca2(this);

    // Inicio as minhas threads (vacas) 
    vaca1.start();
    vaca2.start();

    // Inicio a musica
    volume = 4;
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
  
  
  // Mudanca do algoritmo utilizado para tratar as RCs
  @FXML public void escolherProtocoloDasBandeiras(ActionEvent event){
    algoritmo = 1;
    resetar();
  }
  
  @FXML public void escolherVariavelDeTravamento(ActionEvent event){
    algoritmo = 2;
    variavelDeTravamentoRC1 = 0;
    variavelDeTravamentoRC2 = 0;
    resetar();
  }
  
  @FXML public void escolherEstritaAlternancia(ActionEvent event){
    algoritmo = 3;
    vezRC1 = 1;
    vezRC2 = 2;
    resetar();
  }
  
  @FXML public void escolherSolucaoDePeterson(ActionEvent event){
    algoritmo = 4;
    vezRC1 = 0;
    vezRC2 = 0;
    // inicialmente, nenhuma vaca tem interesse de entrar na rc
    interesseRC1 = new boolean [2];
    interesseRC2 = new boolean [2];
    interesseRC1[0] = false;
    interesseRC1[1] = false;
    interesseRC2[0] = false;
    interesseRC2[1] = false;
    resetar();
  }

  private void resetar(){
    vaca1.setPararPercurso(true);
    vaca2.setPararPercurso(true);
  }

  // ----------------------------------------------------------------
  
  
  public boolean protocoloDasBandeiras(int vaca, int regiaoCritica){
    // {inicio da rc, fim da rc}
    int regiaoCritica1[] = {100, 210};  
    int regiaoCritica2[] = {330, 430};  
    // A vaca informa qual regiao critica eu vou passar
    int rc[] = (regiaoCritica == 1) ? regiaoCritica1 : regiaoCritica2 ;
    double posicaoVacaOposta = (vaca == 1) ? imgViewVaca2.getY() : imgViewVaca1.getY();

    // Verifico se a vaca oposta estah dentro da regiao critica. Se o curral (minha RC) estiver ocupado, retorno false
    // Se a vaca oposta ja saiu do curral, retorno true
    return !( posicaoVacaOposta >= rc[0] && posicaoVacaOposta <= rc[1] );
  }

  // ----------------------------------------------------------------
}