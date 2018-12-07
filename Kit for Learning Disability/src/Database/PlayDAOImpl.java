/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.DataHelper.handleSQLExceptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author J
 */
public class PlayDAOImpl implements PlayDAO {

    @Override
    public List<Play> getAllPlay() {
        try {

            //1: Create a preparated statement
            List<Play> plays = new ArrayList();
            String checkstmt = "SELECT * FROM PLAY";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                plays.add(new Play(rs.getString("username"), rs.getString("gameid"), rs.getTimestamp("timeplayed"), rs.getInt("score")));
            }

            return plays;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean wipeAllPlay() {
        try {

            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM Play");

            //2: Assign values to the ? in SQL statement
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
    public List<Play> getPlayByPlayer(String username) {
        try {

            //1: Create a preparated statement
            List<Play> plays = new ArrayList();
            String checkstmt = "SELECT * FROM PLAY WHERE username=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            stmt.setString(1, username);

            //2: Return a list of plays
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                plays.add(new Play(rs.getString("username"), rs.getString("gameid"), rs.getTimestamp("timeplayed"), rs.getInt("score")));
            }

            return plays;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public List<Play> getPlayByGame(String gameID) {
        try {

            //1: Create a preparated statement
            List<Play> plays = new ArrayList();
            String checkstmt = "SELECT * FROM PLAY WHERE gameID=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            stmt.setString(1, gameID);

            //2: Return a list of plays
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                plays.add(new Play(rs.getString("username"), rs.getString("gameid"), rs.getTimestamp("timeplayed"), rs.getInt("score")));
            }

            return plays;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public List<Play> getPlayByPlayerAndGame(String username, String gameID) {
        try {

            //1: Create a preparated statement
            List<Play> plays = new ArrayList();
            String checkstmt = "SELECT * FROM PLAY WHERE username=? AND gameID=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            stmt.setString(1, username);
            stmt.setString(2, gameID);

            //2: Return a list of plays
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                plays.add(new Play(rs.getString("username"), rs.getString("gameid"), rs.getTimestamp("timeplayed"), rs.getInt("score")));
            }

            return plays;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean addPlay(Play play) {
        try {

            //1: Create a preparated statement
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO PLAY(USERNAME,GAMEID,TIMEPLAYED,SCORE) VALUES(?,?,?,?)");

            //2: Assign values
            statement.setString(1, play.getUsername());
            statement.setString(2, play.getGameID());
            statement.setTimestamp(3, play.getTimePlayed());
            statement.setInt(4, play.getScore());

            //3: Check if at least one record has been affected
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }

        return false;
    }

    @Override
    public List<Play> getPlayByPlayerAndTimeRange(String username, Date startDate, Date endDate) {
        //https://stackoverflow.com/questions/10483123/how-to-compare-timestamp-dates-with-date-only-parameter-in-mysql
        return null;
    }

}
