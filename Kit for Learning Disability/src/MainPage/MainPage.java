package MainPage;

import DatabaseAndLocalization.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

/**
 *
 * @author Hazar Gul Nazari
 */
public class MainPage implements Initializable {

    @FXML
    private Label ProfileLabel,SignOutLabel,DyscalculiaLabel,AboutUsLabel;
    
    @FXML
    private Text MainPanel;

    @FXML
    private Button signout, profile, dylixia, dysgraphia, dyscalculla, visual, audio, about, language;

    @FXML
    private AnchorPane mainBody;

    @FXML
    private void signout(ActionEvent event) throws IOException {
        changePage(event, "../Registration/LogIn.fxml");
    }

    @FXML
    private void aboutus(ActionEvent event) throws IOException {
        changePage(event, "../profile/aboutUS.fxml");
    }

    @FXML
    private void dysgraphiaTest(ActionEvent event) throws IOException {
//        changePage(event, "dysgraphia/dysGraphia.fxml");
        changePage(event, "../NotSupported/notSupported.fxml");
    }

    @FXML
    private void dyscalcullaTest(ActionEvent event) throws IOException {
        changePage(event, "../dyscalculla/mainCalculla.fxml");
        
    }

    @FXML
    private void dyslexiaTest(ActionEvent event) throws IOException {
//        changePage(event, "../dyslixia/dyslixiaMain.fxml");
        System.out.println("Current user: " + DatabaseHandler.getCurrentUsername());
        changePage(event, "../NotSupported/notSupported.fxml");
    }

    @FXML
    private void visualTest(ActionEvent event) throws IOException {
        changePage(event, "../NotSupported/notSupported.fxml");
    }

    @FXML
    private void profile(ActionEvent event) throws IOException {
        changePage(event, "../profile/profile.fxml");
    }

    @FXML
    private void languageTest(ActionEvent event) throws IOException {
        changePage(event, "../NotSupported/notSupported.fxml");
    }

    @FXML
    private void audioTest(ActionEvent event) throws IOException {
        changePage(event, "../NotSupported/notSupported.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MainPanel.setText(DatabaseHandler.getInstance().getMessages().getString("MainPanel"));
        ProfileLabel.setText(DatabaseHandler.getInstance().getMessages().getString("ProfileLabel"));
        DyscalculiaLabel.setText(DatabaseHandler.getInstance().getMessages().getString("DyscalculiaLabel"));
        AboutUsLabel.setText(DatabaseHandler.getInstance().getMessages().getString("AboutUsLabel"));
        SignOutLabel.setText(DatabaseHandler.getInstance().getMessages().getString("SignOutLabel"));
        
        // TODO
        makeFadeTo();
    }

    private void makeFadeTo() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(mainBody);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void changePage(ActionEvent event, String page) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
