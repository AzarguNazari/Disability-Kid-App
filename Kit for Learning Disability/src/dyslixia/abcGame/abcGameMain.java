
package dyslixia.abcGame;

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

public class abcGameMain implements Initializable {

    @FXML
    private AnchorPane body;
    
    private int level = 1;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Utilities.Utilities.FadeInTransition(body);
    }  
    
    @FXML
    private void level1(ActionEvent event) throws IOException {
        level = 1;
    }
    
    @FXML
    private void level2(ActionEvent event) throws IOException {
        level = 2;
    }
    
    @FXML
    private void level3(ActionEvent event) throws IOException {
        level = 3;
    }
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../../MainPage/MainPage.fxml");
    }
    
    private void changePage(ActionEvent event, String page) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

