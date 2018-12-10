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
public class GameDAOImpl implements GameDAO {

    @Override
    public List<Game> getAllGames() {
        try {

            //1: Create a preparated statement
            List<Game> games = new ArrayList();
            String checkstmt = "SELECT * FROM GAME";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                games.add(new Game(rs.getString("gameid"), rs.getString("gamename"), rs.getInt("MAXPOSSIBLESCORE")));
            }

            return games;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public Game getGame(String gameID) {
        try {

            //1: Create a preparated statement
            Game game = null;
            String checkstmt = "SELECT * FROM GAME WHERE GAMEID=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, gameID);

            //3: Return the game
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                game = new Game(rs.getString("gameid"), rs.getString("gamename"), rs.getInt("MAXPOSSIBLESCORE"));
            }

            return game;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean updateGame(Game game) {
        try {

            //1: Create a preparated statement
            String update = "UPDATE GAME SET GAMENAME=?, MAXPOSSIBLESCORE=? WHERE GAMEID=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(update);

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, game.getGameName());
            stmt.setInt(2, game.getMaxPossibleScore());
            stmt.setString(3, game.getGameID());

            //3: Check if the update worked
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean deleteGame(String gameID) {
        try {
            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM GAME WHERE GAMEID = ?");

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, gameID);

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
    public boolean addGame(Game game) {
        try {
            //1: Create a preparated statement
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO GAME(GAMEID,GAMENAME,MAXPOSSIBLESCORE) VALUES(?,?,?)");

            //2: Assign values
            statement.setString(1, game.getGameID());
            statement.setString(2, game.getGameName());
            statement.setInt(3, game.getMaxPossibleScore());
            
            //3: Check if at least one record has been affected
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }

        return false;
    }

    @Override
    public boolean doesGameExist(String gameID) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM GAME WHERE GAMEID=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, gameID);

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

}
