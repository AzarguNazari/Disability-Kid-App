
package dysgraphia.shapeColors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
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
public class ShapeColorsGame implements Initializable {
    
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
    private AnchorPane scorePane;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane startPane;
    @FXML
    private AnchorPane resultPane;
    
    private Answers answer;
    
    private Queue<CalcullaTest> allTests;
    
    private CalcullaTest currentTest;
    
    public static int level = 1;
    
    
    @FXML
    private void trueButton(ActionEvent event) {
        if(currentTest.result){
            answer.correctAnswer++;
            correct.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        else{
            answer.wrongAnswer++;
            incorrect.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        displayNext();
    }
    
    @FXML
    private void falseButton(ActionEvent event) {
        if(currentTest.result){
            answer.wrongAnswer++;
            incorrect.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        else{
            answer.correctAnswer++;
            correct.setText(Integer.toString(Integer.parseInt(incorrect.getText()) + 1));
        }
        
        displayNext();
        
    }
    
    @FXML
    private void speaker(ActionEvent event) {

        Media media = new Media(new File(currentTest.audioPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media); 
        mediaPlayer.setAutoPlay(true);
        
    }
    
    @FXML
    private void startGame(ActionEvent event) {
        scorePane.setVisible(true);
        gamePane.setVisible(true);
        Utilities.Utilities.FadeInTransition(scorePane);
        Utilities.Utilities.FadeInTransition(gamePane);
        startPane.setVisible(false);
        displayNext();
    }
    
    @FXML
    private void rePlayGame(ActionEvent event) throws IOException {
        changePage(event, "dyscalculla.fxml");
    }
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../MainPage/MainPage.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Utilities.Utilities.FadeInTransition(startPane);
        
        allTests = new LinkedList();
        allTests.addAll(
                Arrays.asList(new CalcullaTest(12, "<", 21, "src/audio/dyscalcullah/12less21.mp3"),
                              new CalcullaTest(23, ">", 98, "src/audio/dyscalcullah/23greater98.mp3"))
        );
        
        answer = new Answers(); // to store the answer (correct and wrong)
        
        // to keep all the panes unvissble first
        scorePane.setVisible(false);
        gamePane.setVisible(false);
        resultPane.setVisible(false);
       
        // TODO
    }  
    
    
    
    private void displayNext(){
        if(!allTests.isEmpty()){
            currentTest = allTests.poll();
            firstNumber.setText(Integer.toString(currentTest.firstNumber));
            secondNumber.setText(Integer.toString(currentTest.secondNumber));
            comparison.setText(currentTest.comparison);
            
        }
        else{
            String percentage = new DecimalFormat("#.0#").format(answer.getResult());
            resultPercentage.setText(percentage + " %");
            scorePane.setVisible(false);
            gamePane.setVisible(false);
            resultPane.setVisible(true);
            Utilities.Utilities.FadeInTransition(resultPane);
        }
    }
    
    /**
     * This class is to store tests
     */
    private class CalcullaTest{
        int firstNumber, secondNumber;
        String comparison;
        String audioPath;
        boolean result;
        public CalcullaTest(int firstNumber, String comparison, int secondNumber, String path){
            this.firstNumber = firstNumber;
            this.comparison = comparison;
            this.secondNumber = secondNumber;
            this.audioPath = path;
            result = comparison.equals(">")? firstNumber > secondNumber : firstNumber < secondNumber;
        }
    }
    
    private void changePage(ActionEvent event, String page) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    class Answers{
        int correctAnswer;
        int wrongAnswer;
        public double getResult(){
            return (correctAnswer * 100.0)/(correctAnswer + wrongAnswer);
        }
    }
}

