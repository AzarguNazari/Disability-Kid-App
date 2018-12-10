package dyscalculla;

import DatabaseAndLocalization.DatabaseHandler;
import DatabaseAndLocalization.Game;
import DatabaseAndLocalization.GameDAO;
import DatabaseAndLocalization.GameDAOImpl;
import DatabaseAndLocalization.Play;
import DatabaseAndLocalization.PlayDAO;
import DatabaseAndLocalization.PlayDAOImpl;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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
public class NumberComparison implements Initializable {

    @FXML
    private Text number1, number2, operator, correctAnswerCounter, resultPercentage, incorrectAnswerCounter, Dyscalculia, CorrectAnswers, GameOver;

    @FXML
    private Button speaker, False, True, StartAgain, MainPage, Finish;

    @FXML
    private AnchorPane scorePane, gamePane, startPane, resultPane, game, result;

    private Comparison comparison;
    private Answers answer;
    private final DatabaseHandler dbh = DatabaseHandler.getInstance();
    private final PlayDAO playDAO = new PlayDAOImpl();
    private final GameDAO gameDAO = new GameDAOImpl();
    private final Game NumComp = gameDAO.getGame("NumComp");
    private int counter = 0;

    @FXML
    private void choice(ActionEvent event) {

        String choice = ((Button) event.getSource()).getText().trim();

        if (DatabaseHandler.getInstance().getCurrentLocale().getLanguage().equalsIgnoreCase("ar")) {
            choice = (choice.equals("صح")) ? "true" : "false";
        }

        if (comparison.result(choice)) {
            answer.correctAnswer++;
            correctAnswerCounter.setText(String.valueOf(Integer.parseInt(correctAnswerCounter.getText()) + 1));
        } else {
            answer.wrongAnswer++;
            incorrectAnswerCounter.setText(String.valueOf(Integer.parseInt(incorrectAnswerCounter.getText()) + 1));
        }

        counter++;

        if (counter >= NumComp.getMaxPossibleScore()) {

            finish();
        }

        comparison = new Comparison(this);

    }

    @FXML
    private void speaker(ActionEvent event) throws InterruptedException {
        String[] equation = getEquation();
        Media media;
        MediaPlayer mediaPlayer;
        String op = "";
        Thread equationVoice = new Thread() {
            @Override
            public void run() {
                String[] equation = getEquation();
                Media media;
                MediaPlayer mediaPlayer;
                String op = "";
                for (String equation1 : equation) {
                    if (equation1.equals("<")) {
                        op = "less";
                    } else if (equation1.equals(">")) {
                        op = "greater";
                    } else if (equation1.equals("==")) {
                        op = "=";
                    } else {
                        op = equation1;
                    }

                    String path = "src/audio/numbers/" + op + ".mp3";
                    media = new Media(new File(path).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setAutoPlay(true);

                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AddSub.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        };

        equationVoice.start();
    }

    @FXML
    private void startGame(ActionEvent event) {
        scorePane.setVisible(true);
        gamePane.setVisible(true);
        startPane.setVisible(false);

    }

    @FXML
    private void rePlayGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("NumberComparison.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainCalculla.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        answer = new Answers(); // to store the answer (correct and wrong)

        // to keep all the panes unvissble first
        scorePane.setVisible(false);
        gamePane.setVisible(false);
        resultPane.setVisible(false);

        Dyscalculia.setText(dbh.getMessages().getString("DyscalculiaLabel"));
        GameOver.setText(dbh.getMessages().getString("GameOver"));
        CorrectAnswers.setText(dbh.getMessages().getString("CorrectAnswers"));
        StartAgain.setText(dbh.getMessages().getString("StartAgain"));
        MainPage.setText(dbh.getMessages().getString("MainPage"));
        Finish.setText(dbh.getMessages().getString("Finish"));
        True.setText(dbh.getMessages().getString("True"));
        False.setText(dbh.getMessages().getString("False"));

        comparisonGame();

    }

    public int generateNumber(int range) {
        return (int) (Math.random() * range);
    }

    public String[] getEquation() {
        return new String[]{
            number1.getText(),
            operator.getText(),
            number2.getText()};
    }

    @FXML
    private void finish() {
        playDAO.addPlay(new Play(DatabaseHandler.getCurrentUsername(), NumComp.getGameID(), Integer.parseInt(correctAnswerCounter.getText())));
        String percentage = new DecimalFormat("#.0#").format(answer.getResult());
        resultPercentage.setText(percentage + " %");
        startPane.setVisible(false);
        gamePane.setVisible(false);
        scorePane.setVisible(false);
        resultPane.setVisible(true);
    }

    @FXML
    private void comparisonGame() {

        comparison = new Comparison(this);

        showGamePage();
    }

    private void showGamePage() {
        startPane.setVisible(false);
        resultPane.setVisible(false);
        scorePane.setVisible(true);
        gamePane.setVisible(true);
    }

    private final class Comparison {

        int number1, number2;
        String correctAnswer;
        boolean isEquationCorrect;

        public Comparison(NumberComparison o) {
            //1: Declare/Define variables
            Random rand = new Random();
            String textOperator = ">";

            //2: Generate two random values and calculate their addition
            number1 = rand.nextInt(10 + 10 + 1) - 10;
            number2 = rand.nextInt(10 + 10 + 1) - 10;

            //3: Generate an operand
            switch ((rand.nextInt(3) + 1)) {

                case 1:
                    textOperator = ">";
                    correctAnswer = "" + (number1 > number2);
                    break;
                case 2:
                    textOperator = "==";
                    correctAnswer = "" + (number1 == number2);
                    break;
                case 3:
                    textOperator = "<";
                    correctAnswer = "" + (number1 < number2);
            }

            //4: Set the text of Labels and Buttons  
            o.number1.setText(getNum1());

            o.operator.setText(textOperator);

            o.number2.setText(getNum2());

        }

        public boolean result(String option) {

            return correctAnswer.equalsIgnoreCase(option);

        }

        public String getNum1() {
            return String.valueOf(number1);
        }

        public String getNum2() {
            return String.valueOf((int) number2);
        }

        public String getResult() {
            return String.valueOf(result);
        }

        public int pickCorrectAnswer(ArrayList<Integer> options) {
            return 0;
        }

    }

    class Answers {

        int correctAnswer;
        int wrongAnswer;

        public double getResult() {
            return (correctAnswer * 100.0) / (NumComp.getMaxPossibleScore());
        }
    }
}
