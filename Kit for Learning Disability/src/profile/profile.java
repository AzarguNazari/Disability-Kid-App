package profile;

import DatabaseAndLocalization.DatabaseHandler;
import DatabaseAndLocalization.Play;
import DatabaseAndLocalization.PlayDAO;
import DatabaseAndLocalization.PlayDAOImpl;
import DatabaseAndLocalization.Player;
import DatabaseAndLocalization.PlayerDAO;
import DatabaseAndLocalization.PlayerDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul Nazari
 */
public class profile implements Initializable {

    @FXML
    private Text username, email, password,ProfileLabel,GamePlayProgress;

    @FXML
    private Button EditProfile,MainPage,signout, profile, dylixia, dysgraphia, dyscalculla, visual, audio, about, language;

    @FXML
    private AreaChart<?, ?> addSubProgress, numCompProgress, countProgress;

    @FXML
    private AnchorPane profilePane, body;

    @FXML
    private void mainPage(ActionEvent event) throws IOException {
        changePage(event, "../MainPage/MainPage.fxml");
    }

    @FXML
    private void editAccount(ActionEvent event) throws IOException {
        changePage(event, "changeProfile.fxml");
    }

    private void changePage(ActionEvent event, String page) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void drawData(AreaChart<?, ?> chart, PlayDAO playDAO, String gameID) {
        ArrayList<Play> gameData = (ArrayList<Play>) playDAO.getPlayByPlayerAndGame(DatabaseHandler.getCurrentUsername(), gameID);

        XYChart.Series set = new XYChart.Series<>();

        for (Play play : gameData) {

            set.getData().add(new XYChart.Data(play.getTimePlayed().toString(), play.getScore()));
        }

        chart.getData().addAll(set);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //1: Declare/Define variables
        DatabaseHandler dbh = DatabaseHandler.getInstance();
        PlayerDAO playerDao = new PlayerDAOImpl();
        PlayDAO playDAO = new PlayDAOImpl();
        Player player = playerDao.getPlayer(DatabaseHandler.getCurrentUsername());
        Utilities.Utilities.FadeInTransition(body);
        Utilities.Utilities.FadeInTransition(profilePane);

        //2: Set the player information in the GUI
        username.setText(player.getName());
        email.setText(player.getEmail());
        password.setText(player.getPassword());

        //3: Draw the gameplay data
        drawData(addSubProgress, playDAO, "AddSub");
        drawData(numCompProgress, playDAO, "NumComp");
        drawData(countProgress, playDAO, "Count");
        
        //4: Localize the profile page
        ProfileLabel.setText(dbh.getMessages().getString("ProfileLabel"));
        GamePlayProgress.setText(dbh.getMessages().getString("GamePlayProgress"));
        
        EditProfile.setText(dbh.getMessages().getString("EditProfile"));
        MainPage.setText(dbh.getMessages().getString("MainPage"));
        
        addSubProgress.setTitle(dbh.getMessages().getString("AddSubProgress"));
        numCompProgress.setTitle(dbh.getMessages().getString("NumCompProgress"));
        countProgress.setTitle(dbh.getMessages().getString("CountProgress"));

    }

}
