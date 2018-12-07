package Database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        //1: Declare/Define variables
        // GO into Java DB -> Properties : Databas location should end with computer-based-kit-for-learning-disability\Database
        String url = "jdbc:derby://localhost:1527/LearningDisabilityDataBase",
                query = "Select *"
                + "FROM Player";

        ArrayList<Player> players = new ArrayList();
        Player player;
        PreparedStatement ps;

        //2: Work with the database
        try (Connection con = DriverManager.getConnection(url, "LDDB", "LDDB");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            
            //Get the rows from the result table.
            while (rs.next()) {
                player = new Player(rs.getString("username"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                players.add(player);

                System.out.println(player.toString());
            }

            ps = con.prepareStatement("INSERT INTO PLAYER(username,email,name,password) VALUES(?,?,?,?)");

            ps.setString(1, "oop");
            ps.setString(2, "oop@gmail.com");
            ps.setString(3, "opo");
            ps.setString(4, "lol");
            boolean check=ps.execute();
            
            System.out.println("check="+check);

        } catch (SQLException e) {
            /*SQLException: This class extends Exception thrown by the following methods:
            .   DriverManager.
            .   Statement.
            .   ResultSet.
             */

            while (e != null) {

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

    }

}
