/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAndLocalization;

import static DatabaseAndLocalization.DatabaseHandler.handleSQLExceptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author J
 */
public class PlayerDAOImpl implements PlayerDAO {

    @Override
    public List<Player> getAllPlayers() {
        try {

            //1: Create a preparated statement
            List<Player> players = new ArrayList();
            String checkstmt = "SELECT * FROM PLAYER";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                players.add(new Player(rs.getString("username"), rs.getString("email"), rs.getString("name"), rs.getString("password")));
            }

            return players;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public Player getPlayer(String username) {
        try {

            //1: Create a preparated statement
            Player player = null;
            String checkstmt = "SELECT * FROM PLAYER WHERE username=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, username);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                player = new Player(rs.getString("username"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
            }

            return player;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean updatePlayer(Player player) {
        try {

            //1: Create a preparated statement
            String update = "UPDATE PLAYER SET EMAIL=?, NAME=?, PASSWORD=? WHERE USERNAME=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(update);

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, player.getEmail());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getPassword());
            stmt.setString(4, player.getUsername());

            //3: Check if the update worked
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean deletePlayer(String username) {
        try {

            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM PLAYER WHERE USERNAME = ?");

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, username);

            //3: Check if the deletion worked
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean addPlayer(Player player) {
        try {

            //1: Create a preparated statement
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO PLAYER(Username,Email,Name,Password) VALUES(?,?,?,?)");

            //2: Assign values
            statement.setString(1, player.getUsername());
            statement.setString(2, player.getEmail());
            statement.setString(3, player.getName());
            statement.setString(4, player.getPassword());

            //3: Check if at least one record has been affected
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }

        return false;
    }

    @Override
    public boolean doesPlayerExist(String username) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM PLAYER WHERE username=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, username);

            //3: Check if the player exists
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean loginPlayer(Player player) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM PLAYER WHERE USERNAME=? AND PASSWORD=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, player.getUsername());
            stmt.setString(2, player.getPassword());

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean isEmailUnique(String email) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM PLAYER WHERE EMAIL=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, email);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count== 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

}
