package MCTG.Battle;

import MCTG.Users.Player;

public class Battle {
    private Player player1;
    private Player player2;

    public void fight(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;

        
    }
    public void winner(){}

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
