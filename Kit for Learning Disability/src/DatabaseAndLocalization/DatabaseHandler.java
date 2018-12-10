/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAndLocalization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;

/**
 *
 * @author J
 */
public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    public static final Predicate<String> isEmailCorrect = email -> Pattern.compile("[\\w._]+@\\w+(.\\w+)+").matcher(email).find();

    private static final String DB_URL = "jdbc:derby://localhost:1527/LearningDisabilityDataBase",
            DRIVER = "org.apache.derby.jdbc.ClientDriver",
            messageBundle="DatabaseAndLocalization/MessagesBundle";

    private static String currentUsername;
    private static Connection conn = null;
    private static Statement stmt = null;

    private final Locale english, arabic;
    private Locale currentLocale;
    ResourceBundle messages;

    static {
        createConnection();

    }

    private DatabaseHandler() {
        this.arabic = new Locale("ar","SA");
        this.english = Locale.US;
        Locale.setDefault(english);

        this.currentLocale = Locale.getDefault();
        this.messages = ResourceBundle.getBundle(messageBundle, currentLocale);
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    //Connects to this database
    private static void createConnection() {
        try {

            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, "LDDB", "LDDB");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.out.println("Works!");
    }

    public Connection getConnection() {
        return conn;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String currentUsername) {
        DatabaseHandler.currentUsername = currentUsername;
    }

    public static void handleSQLExceptions(SQLException e) {
        while (e != null) {

            //Vendor-dependent state codes, error codes and messages.
            System.out.println("SQLState:   " + e.getSQLState());
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Message:    " + e.getMessage());

            Throwable t = e.getCause();

            while (t != null) {
                System.out.println("Cause:" + t);

                //Iterate to the next cause.
                t = t.getCause();
            }

            //Iterate to the next SQL exception
            e = e.getNextException();
        }
    }

    public void setArabic() {
        this.currentLocale = arabic;
        this.messages = ResourceBundle.getBundle(messageBundle, currentLocale);
    }

    public void setEnglish() {
        this.currentLocale = english;
        this.messages = ResourceBundle.getBundle(messageBundle, currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public static void main(String[] args) throws Exception {
        DatabaseHandler.getInstance();

    }
}
