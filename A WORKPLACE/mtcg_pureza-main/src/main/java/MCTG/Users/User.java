package MCTG.Users;

public class User {
    private String Username;
    private String Password;
    private int Coins;
    private int Elo;
    private String Bio;
    private String Image;
    private int Wins;
    private int Losses;

    public User(){

    }

    public User(String name, String password) {
        this.Username = name;
        this.Password = password;
        this.Wins = 0;
        this.Losses = 0;
        this.Elo = 100;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public int getCoins() {
        return Coins;
    }

    public int getElo() {
        return Elo;
    }

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }

    public int getWins() {
        return Wins;
    }

    public int getLosses() {
        return Losses;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setCoins(int coins) {
        Coins = coins;
    }

    public void setElo(int elo) {
        Elo = elo;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setWins(int wins) {
        Wins = wins;
    }

    public void setLosses(int losses) {
        Losses = losses;
    }
}
