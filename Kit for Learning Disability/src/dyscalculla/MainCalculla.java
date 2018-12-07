package dyscalculla;

import Experiment.CountTheFigures;
import dyscalculla.arithmaticGame.AddSub;
import dyscalculla.arithmaticGame.Comparison;
import dyscalculla.countFigure.CountFigureGame;
import dyscalculla.identifyNumber.IdentifyingNumberGame;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class MainCalculla implements Initializable {

    @FXML
    private AnchorPane body;
    @FXML
    private AnchorPane arithmaticGame;
    @FXML
    private AnchorPane identifyingNumsGame;
    @FXML
    private AnchorPane countingFiguresGame;

    @FXML
    private void identifyingNumbers(ActionEvent event) throws IOException {
        if (identifyingNumsGame.isVisible()) {
            toggleOptionPanel(false, false, false);
        } else {
            toggleOptionPanel(false, true, false);
        }

    }

    @FXML
    private void identifyingNumbersGameLevel1(ActionEvent event) throws IOException {
        IdentifyingNumberGame.level = 1;
        changePage(event, "identifyNumber/IdentifyingNumberGame.fxml");
    }

    @FXML
    private void identifyingNumbersGameLevel2(ActionEvent event) throws IOException {
        IdentifyingNumberGame.level = 2;
        changePage(event, "identifyNumber/IdentifyingNumberGame.fxml");
    }

    @FXML
    private void identifyingNumbersGameLevel3(ActionEvent event) throws IOException {
        IdentifyingNumberGame.level = 3;
        changePage(event, "identifyNumber/IdentifyingNumberGame.fxml");
    }

    @FXML
    private void countingFigures(ActionEvent event) throws IOException {
        if (countingFiguresGame.isVisible()) {
            toggleOptionPanel(false, false, false);
        } else {
            toggleOptionPanel(false, false, true);
        }
    }

    @FXML
    private void countingFiguresLevel1(ActionEvent event) throws IOException {
        CountFigureGame.level = 1;
        changePage(event, "countFigure/CountFiguresGame.fxml");
    }

    @FXML
    private void countingFiguresLevel2(ActionEvent event) throws IOException {
        CountFigureGame.level = 2;
        changePage(event, "countFigure/CountFiguresGame.fxml");
    }

    @FXML
    private void countingFiguresLevel3(ActionEvent event) throws IOException {
        CountFigureGame.level = 3;
        changePage(event, "countFigure/CountFiguresGame.fxml");
    }

    @FXML
    private void arithmaticProblems(ActionEvent event) throws IOException {
        if (arithmaticGame.isVisible()) {
            toggleOptionPanel(false, false, false);
        } else {
            toggleOptionPanel(true, false, false);
        }
    }
    
    @FXML
    private void addSubLevel1(ActionEvent event) throws IOException {
        AddSub.level = 1;
        changePage(event, "arithmaticGame/AddSub.fxml");
    }
    
    @FXML
    private void addSubLevel2(ActionEvent event) throws IOException {
        AddSub.level = 2;
        changePage(event, "arithmaticGame/AddSub.fxml");
    }
    
    @FXML
    private void addSubLevel3(ActionEvent event) throws IOException {
        AddSub.level = 3;
        changePage(event, "arithmaticGame/AddSub.fxml");
    }

    @FXML
    private void comparisonLevel1(ActionEvent event) throws IOException {
        Comparison.level = 1;
        changePage(event, "arithmaticGame/Comparison.fxml");
    }
    
    @FXML
    private void comparisonLevel2(ActionEvent event) throws IOException {
        Comparison.level = 2;
        changePage(event, "arithmaticGame/Comparison.fxml");
    }
    
    @FXML
    private void comparisonLevel3(ActionEvent event) throws IOException {
        Comparison.level = 3;
        changePage(event, "arithmaticGame/Comparison.fxml");
    }
    
    @FXML
    private void learnTable(ActionEvent event) throws IOException {
        changePage(event, "arithmaticGame/LearnTable.fxml");
    }
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../../MainPage/MainPage.fxml");
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
        toggleOptionPanel(false, false, false);
        Utilities.Utilities.FadeInTransition(body);
    }

    private void toggleOptionPanel(boolean arithGame, boolean identifyGame, boolean countFig) {
        arithmaticGame.setVisible(arithGame);
        identifyingNumsGame.setVisible(identifyGame);
        countingFiguresGame.setVisible(countFig);
        Utilities.Utilities.FadeInTransition(arithmaticGame);
        Utilities.Utilities.FadeInTransition(identifyingNumsGame);
        Utilities.Utilities.FadeInTransition(countingFiguresGame);
    }

}
