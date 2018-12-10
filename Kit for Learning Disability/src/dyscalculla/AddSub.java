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
public class AddSub implements Initializable {

    @FXML
    private Text number1, GameOver, number2, operator, correctAnswerCounter, resultPercentage, incorrectAnswerCounter, Dyscalculia, CorrectAnswers;

    @FXML
    private Button speaker, StartAgain, MainPage, Finish, Choice1, Choice2, Choice3, Choice4;

    @FXML
    private AnchorPane scorePane, gamePane, startPane, resultPane, game, result;

    private Arithmatic arithmatic;
    private Answers answer;
    private final DatabaseHandler dbh = DatabaseHandler.getInstance();
    private final PlayDAO playDAO = new PlayDAOImpl();
    private final GameDAO gameDAO = new GameDAOImpl();
    private final Game addSubGame = gameDAO.getGame("AddSub");
    private int counter = 0;

    @FXML
    private void choice(ActionEvent event) {

        int choice = Integer.parseInt(((Button) event.getSource()).getText().trim());

        if (arithmatic.result(choice)) {
            answer.correctAnswer++;
            correctAnswerCounter.setText(String.valueOf(Integer.parseInt(correctAnswerCounter.getText()) + 1));
        } else {
            answer.wrongAnswer++;
            incorrectAnswerCounter.setText(String.valueOf(Integer.parseInt(incorrectAnswerCounter.getText()) + 1));
        }

        counter++;

        if (counter >= addSubGame.getMaxPossibleScore()) {

            finish();
        }

        arithmatic = new Arithmatic(this);

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
                    if (equation1.equals("/")) {
                        op = "divides";
                    } else if (equation1.equals("*")) {
                        op = "x";
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
        Parent root = FXMLLoader.load(getClass().getResource("AddSub.fxml"));
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

        additionGame();

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
        playDAO.addPlay(new Play(DatabaseHandler.getCurrentUsername(), addSubGame.getGameID(), Integer.parseInt(correctAnswerCounter.getText())));
        String percentage = new DecimalFormat("#.0#").format(answer.getResult());
        resultPercentage.setText(percentage + " %");
        startPane.setVisible(false);
        gamePane.setVisible(false);
        scorePane.setVisible(false);
        resultPane.setVisible(true);
    }

    @FXML
    private void additionGame() {

        arithmatic = new Arithmatic(this);

        showGamePage();
    }

    private void showGamePage() {
        startPane.setVisible(false);
        resultPane.setVisible(false);
        scorePane.setVisible(true);
        gamePane.setVisible(true);
    }

    private final class Arithmatic {

        int number1, number2, correctAnswer;
        boolean isEquationCorrect;

        public Arithmatic(AddSub o) {
            //1: Declare/Define variables
            ArrayList<Integer> options;
            Random rand = new Random();

            //2: Generate two random values and calculate their addition
            number1 = rand.nextInt(10 - 0 + 1) + 0;
            number2 = rand.nextInt(10 + 10 + 1) - 10;
            correctAnswer = number1 + number2;

            //3: Generate 4 possible answers
            options = generateOptions(correctAnswer);

            //4: Set the text of Labels and Buttons  
            o.number1.setText(getNum1());

            o.operator.setText((number2 < 0) ? "-" : "+");

            o.number2.setText(getNum2());

            o.Choice1.setText(options.get(0).toString());
            o.Choice2.setText(options.get(1).toString());
            o.Choice3.setText(options.get(2).toString());
            o.Choice4.setText(options.get(3).toString());

        }

        public boolean result(int option) {

            return correctAnswer == option;

        }

        public String getNum1() {
            return String.valueOf(number1);
        }

        public String getNum2() {
            return String.valueOf((int) Math.abs(number2));
        }

        public String getResult() {
            return String.valueOf(result);
        }

        public ArrayList<Integer> generateOptions(Integer correctAnswer) {
            ArrayList<Integer> nums = new ArrayList(),
                    options = new ArrayList();

            for (int i = 0; i <= 20; i++) {
                nums.add(i);
            }

            //Add the correct result to the options
            options.add(correctAnswer);

            //Remove it from num so it doesn't duplicate
            nums.remove(correctAnswer);

            for (int i = 0; i < 3; i++) {
                options.add(nums.remove((int) (Math.random() * nums.size() - 1)));
            }

            Collections.shuffle(options);

            return options;
        }

        public int pickCorrectAnswer(ArrayList<Integer> options) {
            return 0;
        }

        public void main(String[] args) {
            ArrayList<Integer> options;
            Random rand = new Random();
            int num1, num2, correctAnswer,
                    userOption = 0,
                    numQuestions = 10, numOfCorrectAnswers = 0;

//          rand.nextInt(max - min + 1) + min;
            for (int i = 0; i < numQuestions; i++) {

                num1 = rand.nextInt(10 - 0 + 1) + 0;
                num2 = rand.nextInt(10 + 10 + 1) - 10;
                correctAnswer = num1 + num2;

                options = generateOptions(correctAnswer);

                System.out.printf("%d + (%d) = %d\n", num1, num2, correctAnswer);

                if (correctAnswer == userOption) {
                    numOfCorrectAnswers++;
                }

                System.out.println("Options");
//            options.forEach(System.out::println);
                System.out.println("");

            }

            System.out.println("Score: " + numOfCorrectAnswers + "/" + numQuestions);

        }
    }

    class Answers {

        int correctAnswer;
        int wrongAnswer;

        public double getResult() {
            return (correctAnswer * 100.0) / (addSubGame.getMaxPossibleScore());
        }
    }
}
