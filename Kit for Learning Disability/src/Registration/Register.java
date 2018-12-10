package Registration;

import DatabaseAndLocalization.DatabaseHandler;
import static DatabaseAndLocalization.DatabaseHandler.isEmailCorrect;
import DatabaseAndLocalization.Player;
import DatabaseAndLocalization.PlayerDAOImpl;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
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
    Label error;

    @FXML
    TextField username, email, name;

    @FXML
    PasswordField password, password2;

    @FXML
    private Button register;

    @FXML
    private RadioButton English, Arabic;
    @FXML
    private AnchorPane mainBody;

    @FXML
    private void changeLanguage() {

        if (English.isSelected()) {
            DatabaseHandler.getInstance().setEnglish();
        } else {
            DatabaseHandler.getInstance().setArabic();
        }

        English.setText(DatabaseHandler.getInstance().getMessages().getString("English"));
        Arabic.setText(DatabaseHandler.getInstance().getMessages().getString("Arabic"));

        username.setPromptText(DatabaseHandler.getInstance().getMessages().getString("Username"));
        email.setPromptText(DatabaseHandler.getInstance().getMessages().getString("Email"));
        name.setPromptText(DatabaseHandler.getInstance().getMessages().getString("Name"));
        password.setPromptText(DatabaseHandler.getInstance().getMessages().getString("Password"));
        password2.setPromptText(DatabaseHandler.getInstance().getMessages().getString("RePassword"));

        register.setText(DatabaseHandler.getInstance().getMessages().getString("Register"));

    }

    private boolean areFieldsEmpty(String userName, String emailAddress, String fullName, String pass1, String pass2) {

        //Username
        if (userName.length() == 0) {
            errorHappened("username");
            username.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            error.setVisible(false);
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
            this.name.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            error.setVisible(false);
            this.name.setStyle("-fx-border-width: 0;");
        }
        //Password 1
        if (pass1.length() == 0) {
            errorHappened("password");
            password.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            error.setVisible(false);
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

        error.setVisible(false);
        goBackToLogin(event);
    }

    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../Registration/LogIn.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        //1: Declare/Define variables
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        String userName = username.getText().trim();
        String name = this.name.getText().trim();
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
        error.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(mainBody);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (DatabaseHandler.getInstance().getCurrentLocale().getLanguage().equals("en")) {
            English.setSelected(true);

        } else {
            Arabic.setSelected(true);
        }

        changeLanguage();
    }

    @FXML
    private void insertKey(ActionEvent event) {

    }

    private void errorHappened(String error) {
        switch (error) {
            case "username":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("UsernameError"));
                break;

            case "usernameTaken":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("UsernameTaken"));
                break;

            case "email":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("EmailError"));
                break;

            case "emailTaken":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("EmailTaken"));
                break;

            case "name":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("NameError"));
                break;

            case "password":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("PasswordError"));
                break;
            case "password2":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("RePasswordError"));
                break;

            case "passMismatch":
                this.error.setText(DatabaseHandler.getInstance().getMessages().getString("passMismatch"));
                break;
        }
        this.error.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(this.error);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
