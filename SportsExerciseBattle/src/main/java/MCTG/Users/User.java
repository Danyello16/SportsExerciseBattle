package MCTG.Users;

public class User {
    private String Username;
    private String Password;
    private int Elo;
    private String Bio;
    private String Image;
    private String Wins;
    private String Losses;

    public User(String name, String password){
        this.Username = name;
        this.Password = password;
        this.Elo = 100;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
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

    public String getWins() {
        return Wins;
    }

    public String getLosses() {
        return Losses;
    }
}
