package profile;

import Database.DatabaseHandler;
import Database.Player;
import Database.PlayerDAO;
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
    private Text username;

    @FXML
    private Text email;

    @FXML
    private Text password;

    @FXML
    private Button signout, profile, dylixia, dysgraphia, dyscalculla, visual, audio, about, language;

    @FXML
    private AreaChart<?, ?> dyslixiaProgress;

    @FXML
    private AreaChart<?, ?> dyscalcullaProgress;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private AnchorPane body;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println(LogIn.user.getEmail());
//        System.out.println(LogIn.user.getImage());
//        System.out.println(LogIn.user.getUserName());
//        System.out.println(LogIn.user.getPassword());
//        username.setText(LogIn.user.getUserName());
//        email.setText(LogIn.user.getEmail());
//        password.setText(LogIn.user.getPassword());

        PlayerDAO playerDao = new PlayerDAOImpl();
        Player player = playerDao.getPlayer(DatabaseHandler.getCurrentUsername());
        Utilities.Utilities.FadeInTransition(body);
        Utilities.Utilities.FadeInTransition(profilePane);

        username.setText(player.getName());
        email.setText(player.getEmail());
        password.setText(player.getPassword());

        
        
        XYChart.Series set1 = new XYChart.Series<>();
//        String names[] = {"Ahmad", "Karim", "Jamshid", "Mahmood", "Nabi"};
//        for (int x = 0; x < 100; x++) {
//            int value = (int) (Math.random() * 10000);
//            set1.getData().add(new XYChart.Data(names[(int) (Math.random() * names.length)], value));
//        }

        dyslixiaProgress.getData().addAll(set1);

        XYChart.Series set2 = new XYChart.Series<>();
//        for (int x = 50; x < 1000; x += 50) {
//            set2.getData().add(new XYChart.Data(x + "", x));
//        }
        dyscalcullaProgress.getData().addAll(set2);
    }

}
