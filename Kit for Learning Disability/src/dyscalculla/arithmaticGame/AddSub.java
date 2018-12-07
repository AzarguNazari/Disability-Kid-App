
package dyscalculla.arithmaticGame;

import Utilities.Utilities;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class AddSub implements Initializable {
    
    @FXML
    private AnchorPane game, result;
    
    @FXML
    private Text firstNumber;
    @FXML
    private Text comparison;
    @FXML
    private Text secondNumber;
    @FXML
    private Text correct, incorrect;    // show the score of correct and wrong answers
    @FXML
    private Text resultPercentage;      // show the percentage of result
    @FXML
    private Button speaker;
    @FXML
    private Button trueButton;
    @FXML
    private Button falseButton;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane resultPane;
    
    private ComparisonQuestion currentQuestion;
    
    public static int level = 1;
    
    
    @FXML
    private void trueButton(ActionEvent event) {
        if(currentQuestion.getTrueAnswer()){
            currentQuestion.setAnswer(true);
            correct.setText(Integer.toString(Integer.parseInt(correct.getText()) + 1));
        }
        else{
            currentQuestion.setAnswer(false);
            incorrect.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        generateQuestion();
    }
    
    @FXML
    private void falseButton(ActionEvent event) {
        if(currentQuestion.getTrueAnswer()){
            currentQuestion.setAnswer(false);
            incorrect.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        else{
            currentQuestion.setAnswer(true);
            correct.setText(Integer.toString(Integer.parseInt(correct.getText()) + 1));
        }
        generateQuestion();
    }
    
    @FXML
    private void speaker(ActionEvent event) throws InterruptedException {

        String[] equation = {firstNumber.getText(), comparison.getText(), secondNumber.getText()};
        
        
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                String currentString = "";
                for(String s : equation){
                    if(s.equals(">")) currentString = "greater";
                    else if(s.equals("<")) currentString = "less";
                    else currentString = s;
                    Media media = new Media(new File("src/audio/numbers/" + currentString + ".mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media); 
                    mediaPlayer.setRate(1.0);
                    mediaPlayer.setAutoPlay(true);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Comparison.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        th.start();
        
        
    }
    
    @FXML
    private void finishGame(ActionEvent event) {
        int incorrectAnswers = Integer.parseInt(incorrect.getText());
        int correctAnswers = Integer.parseInt(correct.getText());
        int total = correctAnswers + incorrectAnswers;
        double result = 0;
        if(total == 0) result = 0;
        else result = correctAnswers * 100.0 / total;  // ----> to be stored on the database
        resultPercentage.setText(String.format("%.2f", result) + " %");
        resultPane.setVisible(true);
        Utilities.FadeInTransition(resultPane);
    }
    
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        changePage(event, "Comparison.fxml");
    }
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../../MainPage/MainPage.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Utilities.FadeInTransition(gamePane);
        resultPane.setVisible(false);
        generateQuestion();
        // TODO
    }  
    
    private void generateQuestion(){
        int x = (int)(Math.random() * 8);;
        int y = (int)(Math.random() * 8);;
        String comparisonOperation = (Math.random() < 0.5)? "<" : ">";;
        switch(level){
            case 2:
                x += 7;
                y += 7;
                break;
            case 3:
                x += 17;
                y += 17;
                break;
        }
        currentQuestion = new ComparisonQuestion(x, comparisonOperation, y);
        firstNumber.setText(Integer.toString(x));
        secondNumber.setText(Integer.toString(y));
        comparison.setText(comparisonOperation);
    }
    
    
    
    /**
     * This class is to store tests
     */
    private class ComparisonQuestion{
        private int firstNumber, secondNumber;
        private boolean trueAnswer;
        private boolean isAnswerCorrect;
        public ComparisonQuestion(int firstNumber, String comparison, int secondNumber){
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            trueAnswer = comparison.equals(">")? firstNumber > secondNumber : firstNumber < secondNumber;
        }
        public boolean getTrueAnswer(){return trueAnswer;}
        public int getFirstNumber(){return firstNumber;}
        public int getSecondNumber(){return secondNumber;}
        public void setAnswer(boolean answer){isAnswerCorrect = answer;}
        public boolean getIsAnswerCorrect(){return isAnswerCorrect;}
    }
    
    private void changePage(ActionEvent event, String page) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}

