package Registration;

import static Database.DatabaseHandler.isEmailCorrect;
import Database.Player;
import Database.PlayerDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Hazar Gul
 */
public class Register implements Initializable {

    @FXML
    Label errorMessage;

    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    TextField fullName;
    @FXML
    PasswordField password;
    @FXML
    PasswordField password2;
    @FXML
    private AnchorPane mainBody;

    private boolean areFieldsEmpty(String userName, String emailAddress, String fullName, String pass1, String pass2) {

        //Username
        if (userName.length() == 0) {
            errorHappened("username");
            username.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            errorMessage.setVisible(false);
            username.setStyle("-fx-border-width: 0;");
        }

        //Email
        if (emailAddress.length() == 0 || !isEmailCorrect.test(emailAddress)) {
            errorHappened("email");
            email.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            email.setStyle("-fx-border-width: 0;");
        }

        //Name
        if (fullName.length() == 0) {
            errorHappened("name");
            this.fullName.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            errorMessage.setVisible(false);
            this.fullName.setStyle("-fx-border-width: 0;");
        }
        //Password 1
        if (pass1.length() == 0) {
            errorHappened("password");
            password.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            errorMessage.setVisible(false);
            password.setStyle("-fx-border-width: 0;");
        }

        //Password 2
        if (pass2.length() == 0) {
            errorHappened("password2");
            this.password.setStyle("-fx-border-width: 2;");
            this.password2.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            this.password.setStyle("-fx-border-width: 0;");
            this.password2.setStyle("-fx-border-width: 0;");
        }

        return false;

    }

    private void registerPlayer(ActionEvent event) throws IOException {

        errorMessage.setVisible(false);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../Registration/LogIn.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException {

    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        //1: Declare/Define variables
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        String userName = username.getText().trim();
        String name = fullName.getText().trim();
        String emailAddress = email.getText().trim();
        String pass1 = this.password.getText().trim();
        String pass2 = this.password2.getText().trim();

        //2: Check if the username or password fields are empty
        if (areFieldsEmpty(userName, emailAddress, name, pass1, pass2)) {
            return;
        }

        //3: Check if the player doesn't have an account
        if (!playerDAO.doesPlayerExist(userName)) {

            //Check if the email is unique
            if (playerDAO.isEmailUnique(emailAddress)) {

                //Check if the passwords match
                if (pass1.equals(pass2)) {
                    playerDAO.addPlayer(new Player(userName, emailAddress, name, pass1));
                    registerPlayer(event);

                } else {
                    errorHappened("passMismatch");
                }

            } else {
                errorHappened("emailTaken");
            }

        } else {
            errorHappened("usernameTaken");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(mainBody);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    private void insertKey(ActionEvent event) {

    }

    private void errorHappened(String error) {
        switch (error) {
            case "username":
                errorMessage.setText("Error: please enter the username.");
                break;

            case "usernameTaken":
                errorMessage.setText("Error: This username is already taken.");
                break;

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
            case "password2":
                errorMessage.setText("Error: please enter the same password.");
                break;

            case "passMismatch":
                errorMessage.setText("Error: The passwords don't match.");
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
