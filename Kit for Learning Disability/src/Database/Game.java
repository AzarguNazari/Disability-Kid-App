/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Objects;

/**
 *
 * @author J
 */
public class Game {
    private final String gameID;
    private String gameName;
    private int maxPossibleScore;

    public Game(String gameID, String gameName, int maxPossibleScore) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.maxPossibleScore = maxPossibleScore;
    }

    public String getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public int getMaxPossibleScore() {
        return maxPossibleScore;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setMaxPossibleScore(int maxPossibleScore) {
        this.maxPossibleScore = maxPossibleScore;
    }

    @Override
    public String toString() {
        return "Game{" + "gameID=" + gameID + ", gameName=" + gameName + ", maxPossibleScore=" + maxPossibleScore + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.gameID);
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
        final Game other = (Game) obj;
        if (!Objects.equals(this.gameID, other.gameID)) {
            return false;
        }
        return true;
    }

}
