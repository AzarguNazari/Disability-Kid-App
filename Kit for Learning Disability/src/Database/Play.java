/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author J
 */
public class Play {

    private final String username;
    private final String gameID;
    private Timestamp TimePlayed;
    private int score;

    public Play(String username, String gameID, Timestamp TimePlayed, int score) {
        this.username = username;
        this.gameID = gameID;
        this.TimePlayed = TimePlayed;
        this.score = score;
    }

    public Play(String username, String gameID, int score) {
        this.username = username;
        this.gameID = gameID;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getGameID() {
        return gameID;
    }

    public Timestamp getTimePlayed() {

        return TimePlayed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Play{" + "username=" + username + ", gameID=" + gameID + ", TimePlayed=" + TimePlayed + ", score=" + score + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.username);
        hash = 73 * hash + Objects.hashCode(this.gameID);
        hash = 73 * hash + Objects.hashCode(this.TimePlayed);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Play other = (Play) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.gameID, other.gameID)) {
            return false;
        }
        if (!Objects.equals(this.TimePlayed, other.TimePlayed)) {
            return false;
        }
        return true;
    }

}
