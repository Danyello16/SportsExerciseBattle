package MCTG.Users;

public class BattleUser {
    public String username;
    public int duration;
    public int count;
    public String type;

    public BattleUser(String username, int duration, int count, String type) {
        this.username = username;
        this.duration = duration;
        this.count = count;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BattleUser{" +
                "username='" + username + '\'' +
                ", duration=" + duration +
                ", count=" + count +
                ", type='" + type + '\'' +
                '}';
    }
}
