package MCTG.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.List;

public class StatsAndScoreHandler {

            private static String eloInfo = "\n---------------\nELO:\n"
                        + "Bronze < 70\n"
                        + "Silver < 130\n"
                        + "Gold < 170\n"
                        + "Platinum < 220\n"
                        + "Diamond < 270\n"
                        + "---------------\n";

        public static String showStats(List<String> headers){
            String authToken = headers.get(2);
            //splitting the Token in order to get Name
            String[] arrOfStr = authToken.split(" ",3);
            String[] arrOfStr2 = arrOfStr[2].split("-",2);
            String nameTemp = arrOfStr2[0];

            //String with all the Information
            String allInfo = null;
            int counter = 0;

            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE lower(username) LIKE ?;");
            ){
                statement.setString(1,nameTemp + "%");
                myRs = statement.executeQuery();

                while(myRs.next()) {
                    int elo = myRs.getInt(4);
                    int wins = myRs.getInt(7);
                    int losses = myRs.getInt(8);

                    ObjectMapper mapper = new ObjectMapper();
                    Stats stats = new Stats(elo, wins, losses);

                    // pretty print
                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stats);
                    if(counter == 0) {
                        allInfo = nameTemp + " Stats:\n" + json;
                        counter++;
                    }
                    else {
                        allInfo = allInfo + json;
                    }

                }
            } catch (SQLException | JsonProcessingException ex) {
                ex.printStackTrace();
            }
            allInfo = allInfo + eloInfo;
            return allInfo;
        }

        public static String showScoreboard(){

            //String with all the Information
            String allScoreInfo = null;
            int counter = 0;

            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ORDER BY elo ASC;");
            ){
                myRs = statement.executeQuery();

                while(myRs.next()) {

                    String username = myRs.getString(1);
                    int elo = myRs.getInt(4);

                    ObjectMapper mapper = new ObjectMapper();
                    Scoreboard scoreboard = new Scoreboard(username,elo);

                    // pretty print
                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scoreboard);
                    if(counter == 0) {
                        counter++;
                        allScoreInfo = "Scoreboard:\n" + "Rank "+ String.valueOf(counter) + "\n" + json;
                    }
                    else {
                        counter++;
                        allScoreInfo = allScoreInfo + "\nRank " + String.valueOf(counter) + "\n" + json;
                    }

                }
            } catch (SQLException | JsonProcessingException ex) {
                ex.printStackTrace();
            }
            allScoreInfo = allScoreInfo + eloInfo;
            return allScoreInfo;
        }
}
