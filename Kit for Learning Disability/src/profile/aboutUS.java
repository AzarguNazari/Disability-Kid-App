
package profile;

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
public class aboutUS implements Initializable {
    
    @FXML
    private AnchorPane body;
    
    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../MainPage/MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Utilities.Utilities.FadeInTransition(body);
    }    
    
    
    
}
