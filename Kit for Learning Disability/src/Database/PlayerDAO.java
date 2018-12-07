
package Database;

import java.util.List;

public interface PlayerDAO {

    public List<Player> getAllPlayers();

    public Player getPlayer(String username);

    public boolean updatePlayer(Player player);

    public boolean deletePlayer(String username);

    //Registers a new player
    public boolean addPlayer(Player player);

    public boolean doesPlayerExist(String username);
    
    public boolean isEmailUnique(String email);

    //Logs in an existing player 
    public boolean loginPlayer(Player player);

}
