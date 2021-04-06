package MCTG.Users;

public class Stats {
    int elo;
    int wins;
    int losses;

    public Stats(int Elo, int Wins, int Losses){
        this.elo = Elo;
        this.wins = Wins;
        this.losses = Losses;
    }

    public int getElo() {
        return elo;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
}
