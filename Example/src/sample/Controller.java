package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    //public Dictionary dictionary;
    @FXML
    private TextField textField;
    @FXML
    private TextField textField2;
    @FXML
    private TextArea textArea;
    @FXML
    private ImageView image;
    @FXML
    private ImageView pronoun;


//     private MediaPlayer mediaPlayer;
//    @FXML
//    private MediaView media;
//    @FXML
//    private MediaView mediaView;
    String sound="image/hoa.mp4";

    public Controller() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        System.out.println(url.toString());
//        System.out.println(this.getClass().getResource(sound).toExternalForm());
////            media=new Media(new File(sound).toURI().toString());
//            mediaPlayer=new MediaPlayer(new Media(this.getClass().getResource(sound).toExternalForm()));
//            mediaPlayer.setAutoPlay(true);
//            media.setMediaPlayer(mediaPlayer);
//            mediaView.setMediaPlayer(mediaPlayer);
//            mediaPlayer.play();


    }
//    MediaPlayer mediaPlayer=new MediaPlayer(media);
    //@FXML
     //Image buttonPress=new Image("image\\speaker.jpg");
    //@FXML
     //Image releasePress=new Image("..\\image\\speaker2.jpg");
    private Button button;
     Dictionary dictionary=new Dictionary();
    @FXML
    public void summit(ActionEvent actionEvent) throws FileNotFoundException {

        String word=textField.getText();
        String performWord=word.toLowerCase();

        if(dictionary.searchTranWord(performWord)==true){
            InternetConnect ic=new InternetConnect();
            String data=ic.getOnlineData(performWord);
                textArea.setText(data);
        }
        else{
            textArea.setText("\nDon't have word \nYou can add in dictionary!!!");
        }
        dictionary.sout();



    }

    public void mousePress(MouseEvent mouseEvent){
        //pronoun.setImage(buttonPress);
        //System.out.println("press");
        Image releasePress=new Image("image\\speaker2.jpg");
        //pronoun.setImage(releasePress);
        //mediaPlayer.setAutoPlay(true);
        //mediaPlayer.play();
//        Image buttonPress=new Image("image\\speaker.jpg");
        pronoun.setImage(releasePress);

    }
    Stage primaryStage2=new Stage();
//    MenuItem m1=new MenuItem("add word to dictionary");
//    MenuItem m2=new MenuItem("remove word from dictionary");
//    MenuItem m3=new MenuItem("fix word in dictionary");
//    MenuButton menuButton=new MenuButton("Options",null,m1,m2,m3);
    @FXML
    private Label require;

    public void clickMouse(MouseEvent mouseEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Scene scene=new Scene(root);
        primaryStage2.setScene(scene);
        primaryStage2.show();

    }
    public boolean add(ActionEvent mouseEvent){
        require.setText("Add to dictionary");
        return true;
    }
    public boolean remove(ActionEvent mouseEvent){
        require.setText("Remove from dictionary");
        return true;
    }
    public boolean fix(ActionEvent mouseEvent){
        require.setText("Fix in dictionary");
        return true;
    }
    public void save(ActionEvent mouseEvent){
        String x=textField2.getText().toLowerCase();
        Word w=new Word(x);
        System.out.println(x);
        if(add(mouseEvent)==true) dictionary.addWord(w);
        else if(remove(mouseEvent)==true) dictionary.removeWord(w);
        //else if(fix(mouseEvent)==true) dictionary.fixWord();
        primaryStage2.close();
    }


}
