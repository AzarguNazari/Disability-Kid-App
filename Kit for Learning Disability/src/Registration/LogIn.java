package Registration;

import Database.DatabaseHandler;
import Database.Player;
import Database.PlayerDAOImpl;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class LogIn implements Initializable {

    @FXML
    private Label error;
    @FXML
    private ToggleGroup loginType;
    @FXML
    private AnchorPane mainBody;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    private boolean areFieldsEmpty(String userName, String pass) {

        if (userName.length() == 0) {
            errorHappened("userName");
            email.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            error.setVisible(false);
            email.setStyle("-fx-border-width: 0;");
        }

        if (pass.length() == 0) {
            errorHappened("password");
            password.setStyle("-fx-border-width: 2;");
            return true;
        } else {
            error.setVisible(false);
            password.setStyle("-fx-border-width: 0;");
        }

        return false;

    }

    private void loginPlayer(ActionEvent event) throws IOException {
        
        error.setVisible(false);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../MainPage/MainPage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        //1: Declare/Define variables
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        Player player;
        String userName = email.getText().trim(), pass = this.password.getText().trim();

        //2: Check if the username or password fields are empty
        if (areFieldsEmpty(userName, pass)) {
            return;
        }

        //3: Check if the player has an account
        if (playerDAO.doesPlayerExist(userName)) {
            player = new Player(userName, "", "", pass);

            //Log player if the password is correct
            if (playerDAO.loginPlayer(player)) {
                DatabaseHandler.setCurrentUsername(userName);
                loginPlayer(event);
            } else {
                errorHappened("wrongPassword");
            }

        } else {
            errorHappened("notExist");
        }

    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        Scene tableViewScene = new Scene(FXMLLoader.load(getClass().getResource("RegisterPage.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    private void errorHappened(String errorMessage) {
        switch (errorMessage) {
            case "username":
                error.setText("Please enter a username.");
                break;
            case "password":
                error.setText("Please enter a password.");
                break;

            case "wrongPassword":
                error.setText("Sorry, the password is incorrect.");
                break;
            case "notExist":
                error.setText("Sorry, the user does not exist.");
                break;
            case "databaseError":
                error.setText("Error: database connection.");
                break;
        }
        error.setVisible(true);
        Utilities.Utilities.FadeInTransition(error);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        error.setVisible(false);
        Utilities.Utilities.FadeInTransition(mainBody);
    }
}
