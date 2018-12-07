package profile;

import Database.DatabaseHandler;
import static Database.DatabaseHandler.isEmailCorrect;
import Database.Player;
import Database.PlayerDAO;
import Database.PlayerDAOImpl;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Hazar Gul Nazari
 */
public class changeProfile implements Initializable {

    @FXML
    private AnchorPane page;
    @FXML
    private Label errorMessage;
    @FXML
    private AnchorPane header;
    @FXML
    private AnchorPane profilePic;
    @FXML
    private AnchorPane body;

    @FXML
    private Button image1;

    @FXML
    private TextField displayName;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private ImageView image;

    private String imageURL, userName, pass, emailAddress;

//    private User currentUser;
    @FXML
    private void cancelPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../MainPage/MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void hidePics(ActionEvent event) {
        Utilities.Utilities.hideFadeOut(profilePic);
    }

    @FXML
    private void changePicture(ActionEvent event) {
        Node node = (Node) event.getSource();
        System.out.println(node.getId());
        Button but = (Button) node;
        image.setImage(((ImageView) but.getGraphic()).getImage());
        imageURL = "../images/profilePics/" + node.getId() + ".png";
        Utilities.Utilities.hideFadeOut(profilePic);
    }

    @FXML
    private void showImageChooser(ActionEvent event) throws IOException {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        profilePic.setVisible(true);
        fadeTransition.setNode(profilePic);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //1: Declare/Define variables
        PlayerDAO playerDao = new PlayerDAOImpl();
        Player player = playerDao.getPlayer(DatabaseHandler.getCurrentUsername());

        //2: Set the content of the text fields
        displayName.setText(player.getName());
        email.setText(player.getEmail());
        password.setText(player.getPassword());

        //3: Change player fields
        if (!displayName.getText().equals("")) {
            player.setName(displayName.getText());
        } else {

        }

        if (!isEmailCorrect.test(email.getText())) {

        } else if (!playerDao.isEmailUnique(email.getText()) && !player.getEmail().equals(email.getText())) {

        } else {
            player.setEmail(email.getText());
        }

        if (!password.getText().equals("")) {
            player.setPassword(password.getText());
        } else {

        }

        //4: Update the player in the database
        playerDao.updatePlayer(player);

        //Image image = new Image(path1.getFile());
        Utilities.Utilities.FadeInTransition(page);
    }

    public void save(ActionEvent event) {
        userName = displayName.getText().trim();
        emailAddress = email.getText().trim();
        pass = password.getText().trim();
        System.out.println(imageURL + "  " + userName + "  " + pass + "  " + emailAddress);
    }

    private void errorHappened(String error) {
        switch (error) {

            case "email":
                errorMessage.setText("Error: please enter a correct email.");
                break;

            case "emailTaken":
                errorMessage.setText("Error: This email is already taken.");
                break;

            case "name":
                errorMessage.setText("Error: please enter a name.");
                break;

            case "password":
                errorMessage.setText("Error: please enter a password.");
                break;

        }
        errorMessage.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(errorMessage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

}
