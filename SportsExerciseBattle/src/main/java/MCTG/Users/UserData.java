package MCTG.Users;

public class UserData {
    private String Username;
    private String Bio;
    private String Image;

    public UserData(String name, int coins, String bio , String image){
        this.Username = name;
        this.Bio = bio;
        this.Image = image;
    }
    public UserData(String name, String bio , String image){
        this.Username = name;
        this.Bio = bio;
        this.Image = image;
    }

    public String getUsername() {
        return Username;
    }

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }
}
