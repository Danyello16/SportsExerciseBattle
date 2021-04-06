package MCTG.Users;

public class Scoreboard {
    String userName;
    int elo;

    public Scoreboard(String user, int elo){
        this.userName = user;
        this.elo = elo;
    }

    public String getUserName() {
        return userName;
    }

    public int getElo() {
        return elo;
    }
}
