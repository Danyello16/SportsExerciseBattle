package MCTG.Battle;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class BattleHandler {

    public static String getPlayersName(List<String> headers){
        String authToken = headers.get(2);
        //splitting the TplayerNameoken in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String playersName = arrOfStr2[0];

        return playersName;
    }

    public static int checkWinner(int first, int second){
        if(second == 0)
            return 1;
        else if(first == 0)
            return 2;
        else return 3;
    }




    public static String letsBattle(String PlayerOne, String PlayerTwo){

        String battleLog = "\n\nLET THE BATTLE BEGIN:\n";

        battleLog = "\n----------------------------------------\n\n";

        return battleLog;
    }

    public static void updateStats(String playerName, boolean winOrLose){

        int wins = 0;
        int losses = 0;
        int elo = 0;

        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE lower(username) LIKE ?;");
        ){
            statement.setString(1,playerName + "%");
            myRs = statement.executeQuery();

            while(myRs.next()) {
                elo = myRs.getInt(4);
                wins = myRs.getInt(7);
                losses = myRs.getInt(8);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        if(winOrLose){
            wins = wins + 1;
            elo = elo + 3;
        }else {
            losses = losses +1;
            elo = elo - 5;
        }

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET elo = ?, wins = ?, losses = ? WHERE lower(username) LIKE ?;");
        ){
            statement.setInt(1, elo);
            statement.setInt(2, wins);
            statement.setInt(3, losses);
            statement.setString(4,playerName + "%");
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
