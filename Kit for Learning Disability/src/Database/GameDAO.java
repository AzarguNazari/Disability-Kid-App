/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.List;

/**
 *
 * @author J
 */
public interface GameDAO {
    public List<Game> getAllGames();

    public Game getGame(String gameID);

    public boolean updateGame(Game game);

    public boolean deleteGame(String gameID);

    public boolean addGame(Game game);

    public boolean doesGameExist(String gameID);
    
}
