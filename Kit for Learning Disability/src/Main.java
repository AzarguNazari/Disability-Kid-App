
import Database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

       // DatabaseHandler dH = DatabaseHandler.getInstance();

        
        Parent root = FXMLLoader.load(getClass().getResource("dyscalculla/MainCalculla.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/MainPage/MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
