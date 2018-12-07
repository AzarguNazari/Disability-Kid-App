package dyslixia;


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
public class dyslixiaMain implements Initializable {

    @FXML
    private AnchorPane body;
    
    @FXML
    private void colorShapGame(ActionEvent event) throws IOException {
        changePage(event, "colorShapGame/colorShapeGame.fxml");
    }
    
    @FXML
    private void abcGame(ActionEvent event) throws IOException {
        changePage(event, "abcGame/abcGameMain.fxml");
    }
    
    @FXML
    private void listeningSkils(ActionEvent event) throws IOException {
        changePage(event, "listeningSkills/listeningSkillsMain.fxml");
    }
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../MainPage/MainPage.fxml");
    }
    
    private void changePage(ActionEvent event, String page) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilities.Utilities.FadeInTransition(body);
    }  
   
}

