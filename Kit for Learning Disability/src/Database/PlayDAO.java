/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Date;
import java.util.List;

/**
 *
 * @author J
 */
public interface PlayDAO {

    public List<Play> getAllPlay();

    public boolean wipeAllPlay();

    public List<Play> getPlayByPlayer(String username);
    
     public List<Play> getPlayByPlayerAndTimeRange(String username,Date startDate,Date endDate);

    public List<Play> getPlayByGame(String gameID);
    
    public List<Play> getPlayByPlayerAndGame(String username,String gameID);

    public boolean addPlay(Play play);
}
