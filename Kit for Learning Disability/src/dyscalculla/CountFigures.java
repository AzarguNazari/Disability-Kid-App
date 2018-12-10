package dyscalculla;

import DatabaseAndLocalization.DatabaseHandler;
import DatabaseAndLocalization.Game;
import DatabaseAndLocalization.GameDAO;
import DatabaseAndLocalization.GameDAOImpl;
import DatabaseAndLocalization.Play;
import DatabaseAndLocalization.PlayDAO;
import DatabaseAndLocalization.PlayDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class CountFigures implements Initializable {

    @FXML
    private Text number1, number2, operator, correctAnswerCounter, resultPercentage, incorrectAnswerCounter, Dyscalculia, GameOver, CorrectAnswers;

    @FXML
    private Button Choice1, Choice2, Choice3, Choice4, StartAgain, MainPage, Finish;

    @FXML
    private ImageView pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9, pic10;

    @FXML
    private AnchorPane scorePane, gamePane, startPane, resultPane, game, result;

    private Counting count;
    private Answers answer;
    private final DatabaseHandler dbh = DatabaseHandler.getInstance();
    private final PlayDAO playDAO = new PlayDAOImpl();
    private final GameDAO gameDAO = new GameDAOImpl();
    private final Game countGame = gameDAO.getGame("Count");
    private int counter = 0;

    @FXML
    private void choice(ActionEvent event) {

        int choice = Integer.parseInt(((Button) event.getSource()).getText().trim());

        if (count.result(choice)) {
            answer.correctAnswer++;
            correctAnswerCounter.setText(String.valueOf(Integer.parseInt(correctAnswerCounter.getText()) + 1));
        } else {
            answer.wrongAnswer++;
            incorrectAnswerCounter.setText(String.valueOf(Integer.parseInt(incorrectAnswerCounter.getText()) + 1));
        }

        counter++;

        if (counter >= countGame.getMaxPossibleScore()) {

            finish();
        }

        count = new Counting(this);

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
        Parent root = FXMLLoader.load(getClass().getResource("CountFigures.fxml"));
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

        countFigureGame();

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
        playDAO.addPlay(new Play(DatabaseHandler.getCurrentUsername(), countGame.getGameID(), Integer.parseInt(correctAnswerCounter.getText())));
        String percentage = new DecimalFormat("#.0#").format(answer.getResult());
        resultPercentage.setText(percentage + " %");
        startPane.setVisible(false);
        gamePane.setVisible(false);
        scorePane.setVisible(false);
        resultPane.setVisible(true);
    }

    @FXML
    private void countFigureGame() {

        count = new Counting(this);

        showGamePage();
    }

    private void showGamePage() {
        startPane.setVisible(false);
        resultPane.setVisible(false);
        scorePane.setVisible(true);
        gamePane.setVisible(true);
    }

    private final class Counting {

        int correctAnswer;
        boolean isEquationCorrect;

        public Counting(CountFigures o) {
            //1: Declare/Define variables
            ArrayList<Integer> options;
            Random rand = new Random();
            Image image = null;
            //2: Generate 4 possible answers
            options = generateOptions();

            //rand.nextInt(max - min + 1) + min;
            correctAnswer = options.get(rand.nextInt(options.size()));

            //4: Set the text of Labels and Buttons  
            o.Choice1.setText(options.get(0).toString());
            o.Choice2.setText(options.get(1).toString());
            o.Choice3.setText(options.get(2).toString());
            o.Choice4.setText(options.get(3).toString());

            switch (rand.nextInt(2) + 1) {
                case 1:
                    image = new Image("\\images\\Cosmos+Flowers.jpg");
                    break;

                case 2:
                    image = new Image("\\images\\caf--goldfish__main.jpg");
                    break;

            }

            //4: Turn off images that have a number bigger than correctAnswer
            o.pic1.setImage(image);
            o.pic1.setVisible((1 <= correctAnswer));

            o.pic2.setImage(image);
            o.pic2.setVisible((2 <= correctAnswer));

            o.pic3.setImage(image);
            o.pic3.setVisible((3 <= correctAnswer));

            o.pic4.setImage(image);
            o.pic4.setVisible((4 <= correctAnswer));

            o.pic5.setImage(image);
            o.pic5.setVisible((5 <= correctAnswer));

            o.pic6.setImage(image);
            o.pic6.setVisible((6 <= correctAnswer));

            o.pic7.setImage(image);
            o.pic7.setVisible((7 <= correctAnswer));

            o.pic8.setImage(image);
            o.pic8.setVisible((8 <= correctAnswer));

            o.pic9.setImage(image);
            o.pic9.setVisible((9 <= correctAnswer));

            o.pic10.setImage(image);
            o.pic10.setVisible((10 <= correctAnswer));

        }

        public boolean result(int option) {

            return correctAnswer == option;

        }

        public String getResult() {
            return String.valueOf(result);
        }

        public ArrayList<Integer> generateOptions() {
            ArrayList<Integer> nums = new ArrayList(),
                    options = new ArrayList();

            for (int i = 1; i < 11; i++) {
                nums.add(i);
            }

            for (int i = 0; i < 4; i++) {
                options.add(nums.remove((int) (Math.random() * nums.size() - 1)));
            }

            Collections.shuffle(options);

            return options;
        }

        public int pickCorrectAnswer(ArrayList<Integer> options) {
            return 0;
        }

    }

    class Answers {

        int correctAnswer;
        int wrongAnswer;

        public double getResult() {
            return (correctAnswer * 100.0) / (countGame.getMaxPossibleScore());
        }
    }
}
