package dyscalculla;

import DatabaseAndLocalization.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class MainCalculla implements Initializable {

    @FXML
    private AnchorPane body;

    @FXML
    private Text Dyscalculia, GameOver, CorrectAnswers,ChooseGame;

    @FXML
    private Button StartAgain, MainPage, Finish;
    
    @FXML
    private Label NumCompLabel,CountLabel,AddSubLabel;

    @FXML
    private void identifyingNumbers(ActionEvent event) throws IOException {
        changePage(event, "/dyscalculla/NumberComparison.fxml");
    }

    @FXML
    private void countingFigures(ActionEvent event) throws IOException {

        changePage(event, "/dyscalculla/CountFigures.fxml");
    }

    @FXML
    private void arithmaticProblems(ActionEvent event) throws IOException {
        changePage(event, "/dyscalculla/AddSub.fxml");
    }

    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../MainPage/MainPage.fxml");
    }

    private void changePage(ActionEvent event, String page) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilities.Utilities.FadeInTransition(body);

        DatabaseHandler dbh = DatabaseHandler.getInstance();
        
        ChooseGame.setText(dbh.getMessages().getString("ChooseGame"));
        NumCompLabel.setText(dbh.getMessages().getString("NumCompLabel"));
        CountLabel.setText(dbh.getMessages().getString("CountLabel"));
        AddSubLabel.setText(dbh.getMessages().getString("AddSubLabel"));
        MainPage.setText(dbh.getMessages().getString("MainPage"));

    }

}
