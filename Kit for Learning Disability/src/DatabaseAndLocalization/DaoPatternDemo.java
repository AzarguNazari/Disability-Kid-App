/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAndLocalization;

/**
 *
 * @author J
 */
public class DaoPatternDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlayerDAO playerDAO = new PlayerDAOImpl();
        
        
        
        for (Player player: playerDAO.getAllPlayers()){
            System.out.println(player.toString());
        }

    }

}
