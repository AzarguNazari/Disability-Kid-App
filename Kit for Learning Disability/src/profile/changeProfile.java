package profile;

import DatabaseAndLocalization.DatabaseHandler;
import static DatabaseAndLocalization.DatabaseHandler.isEmailCorrect;
import DatabaseAndLocalization.Player;
import DatabaseAndLocalization.PlayerDAO;
import DatabaseAndLocalization.PlayerDAOImpl;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Hazar Gul Nazari
 */
public class changeProfile implements Initializable {

    DatabaseHandler dbh = DatabaseHandler.getInstance();
    @FXML
    private Label errorMessage;
    @FXML
    private AnchorPane header, profilePic, body, page;

    @FXML
    private Button image1, changePicture, Save, Back;

    @FXML
    private TextField displayName, password, email;

    @FXML
    private Text EditProfile;

    @FXML
    private ImageView image;

    private String imageURL, userName, pass, emailAddress;

    private Player player;
    private PlayerDAO playerDao;

//    private User currentUser;
    @FXML
    private void cancelPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../profile/profile.fxml"));
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

        errorMessage.setVisible(false);

        //1: Declare/Define variables
        playerDao = new PlayerDAOImpl();
        player = playerDao.getPlayer(DatabaseHandler.getCurrentUsername());

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

        //5:Image image = new Image(path1.getFile());
        Utilities.Utilities.FadeInTransition(page);

        //6: Localize the profile page
        changePicture.setText(dbh.getMessages().getString("ChangePicture"));
        Save.setText(dbh.getMessages().getString("Save"));
        Back.setText(dbh.getMessages().getString("Back"));
        EditProfile.setText(dbh.getMessages().getString("EditProfile"));

    }

    public void save(ActionEvent event) throws InterruptedException {

        //3: Change player fields
        if (displayName.getText().trim().length() > 0) {
            errorMessage.setVisible(false);
            player.setName(displayName.getText());
        } else {
            errorHappened("name");
            return;
        }

        if (!isEmailCorrect.test(email.getText().trim())) {
            errorHappened("email");
            return;
        } else if (!player.getEmail().equals(email.getText().trim()) && !playerDao.isEmailUnique(email.getText().trim())) {
            errorHappened("emailTaken");
            return;
        } else {
            errorMessage.setVisible(false);
            player.setEmail(email.getText());
        }

        if (password.getText().trim().length() > 0) {
            errorMessage.setVisible(false);
            player.setPassword(password.getText());
        } else {
            errorHappened("password");
            return;
        }

        //4: Update the player in the database
        playerDao.updatePlayer(player);

        errorMessage.setStyle("-fx-background-color: #00ff00");
        errorMessage.setText(dbh.getMessages().getString("ChangeHappened"));
        errorMessage.setVisible(true);

    }

    private void errorHappened(String error) {
        errorMessage.setStyle("-fx-background-color: #FF4500");
        switch (error) {

            case "email":
                errorMessage.setText(dbh.getMessages().getString("EmailError"));
                break;

            case "emailTaken":
                errorMessage.setText(dbh.getMessages().getString("EmailTaken"));
                break;

            case "name":
                errorMessage.setText(dbh.getMessages().getString("NameError"));
                break;

            case "password":
                errorMessage.setText(dbh.getMessages().getString("PasswordError"));
                break;

        }
        errorMessage.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(errorMessage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

//        fadeTransition = new FadeTransition(Duration.millis(500));
//        fadeTransition.setNode(errorMessage);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
    }

}
