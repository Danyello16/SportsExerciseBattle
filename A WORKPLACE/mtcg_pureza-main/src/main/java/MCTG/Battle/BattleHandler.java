package MCTG.Battle;

import MCTG.Users.BattleUser;
import MCTG.Users.User;
import org.json.JSONObject;

import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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




    public void letsBattle(String payload,List<String> headers) {

// first time it is called
        //sleep for 2 mins
        //resume again
        Timer timer = new Timer();


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        String battleLog = "\n\nLET THE EXERCISE COMMENCE:\n";

        battleLog = "\n----------------------------------------\n\n";


        String authToken = headers.get(3);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ", 3);
        String[] arrOfStr2 = arrOfStr[2].split("-", 2);
        String nameTemp = arrOfStr2[0];

        System.out.println("\n" + nameTemp + " This is the user");

// name ,type
// fire one query on databse -> select * from table_name where name='nameTemp' and type='pushups'
//if data exist then update -> update table_name set count='new_count' where name='nameTemp' and type='pushups'
//not exist then insert in database

        //if seconds >120 then not insert in databse

        //all JsonObjects in an Array
        JSONObject jsonObj = new JSONObject(payload);

        String type = jsonObj.get("Name").toString();
        Integer duration = Integer.parseInt(jsonObj.get("DurationInSeconds").toString());
        Integer count = Integer.parseInt(jsonObj.get("Count").toString());


        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO history (username, duration, count, type) VALUES(?,?,?,?);")
        ) {
            //statement.setInt(1,13);
            statement.setString(1, nameTemp);
            statement.setInt(2, duration);
            // statement.setInt(3, user1.getCoins());
            statement.setInt(3, count);
            statement.setString(4, type);
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //BattleUser Stuff
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        int exist = 0;
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM history;");
        ){
            myRs = statement.executeQuery();
            Map<String,BattleUser> userDataMap=new HashMap<>();
            BattleUser battleUser;
            int rowCount=0;

            while(myRs.next()) {
                rowCount++;
        //        int counter = myRs.getInt(1);
                String name =myRs.getString(1);
                int countData  =myRs.getInt(2);
                int durationData =myRs.getInt(3);
                String typeData=myRs.getString(4);

                if(!userDataMap.containsKey(name)){
                    battleUser=new BattleUser(name,durationData,countData,typeData);
                    userDataMap.put(name,battleUser);
                }else{
                    battleUser=userDataMap.get(name);
                    int tempDuration=battleUser.getDuration();
                    int tempCount=battleUser.getCount();
                    battleUser.setDuration(tempDuration+durationData);
                    battleUser.setCount(tempCount+countData);
                }


            }
            List<BattleUser> battleUserListForWin=new ArrayList<>();

            userDataMap.entrySet().forEach(a->{
                BattleUser loopData=a.getValue();
                battleUserListForWin.add(loopData);
                System.out.println(loopData.getUsername()+"-----"+loopData.getCount()+"-----"+loopData.getDuration());
            });
            System.out.println(rowCount+"     rowCount");
            if(!battleUserListForWin.isEmpty() && battleUserListForWin.size()==2 && rowCount==3){


            int user1Count=battleUserListForWin.get(0).getCount();
            int user2Count=battleUserListForWin.get(1).getCount();
            if(user1Count==user2Count){
                System.out.println(battleUserListForWin.get(0) +"   &&  "+battleUserListForWin.get(1)+"            winner");

                PreparedStatement winnerQuery =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(0).getUsername()+"'");
                ResultSet winnerRs = winnerQuery.executeQuery();
                User winnerUser=new User();
                while(winnerRs.next()){

                    winnerUser.setElo(winnerRs.getInt(3));
                    winnerUser.setWins(winnerRs.getInt(5));
                }
                PreparedStatement winnerUpdate=connection.prepareStatement("Update users set elo="+(winnerUser.getElo()+1)+" , wins ="+(winnerUser.getWins()+1)+" where username='"+battleUserListForWin.get(0).getUsername()+"'");
                winnerUpdate.executeUpdate();

                PreparedStatement winner2Query =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(1).getUsername()+"'");
                ResultSet winner2Rs = winner2Query.executeQuery();
                User winner2User=new User();
                while(winner2Rs.next()){

                    winner2User.setElo(winner2Rs.getInt(3));
                    winner2User.setWins(winner2Rs.getInt(5));
                }
                PreparedStatement winner2Update=connection.prepareStatement("Update users set elo="+(winner2User.getElo()-1)+" , wins ="+(winner2User.getWins()+1)+" where username='"+battleUserListForWin.get(1).getUsername()+"'");
                winner2Update.executeUpdate();

            }else if(user1Count>user2Count){
                System.out.println(battleUserListForWin.get(0) +"         winner else if");

                PreparedStatement winnerQuery =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(0).getUsername()+"'");
                ResultSet winnerRs = winnerQuery.executeQuery();
                User winnerUser=new User();
                while(winnerRs.next()){

                   winnerUser.setElo(winnerRs.getInt(3));
                    winnerUser.setWins(winnerRs.getInt(5));
                }
                PreparedStatement winnerUpdate=connection.prepareStatement("Update users set elo="+(winnerUser.getElo()+1)+" , wins ="+(winnerUser.getWins()+1)+" where username='"+battleUserListForWin.get(0).getUsername()+"'");
                winnerUpdate.executeUpdate();

                PreparedStatement looserQuery =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(1).getUsername()+"'");
                ResultSet looserRs = looserQuery.executeQuery();
                User looserUser=new User();
                while(looserRs.next()){

                    looserUser.setElo(looserRs.getInt(3));
                    looserUser.setLosses(looserRs.getInt(6));
                }
                PreparedStatement looserUpdate=connection.prepareStatement("Update users set elo="+(looserUser.getElo()-1)+" , losses ="+(looserUser.getLosses()+1)+" where username='"+battleUserListForWin.get(1).getUsername()+"'");
                looserUpdate.executeUpdate();


            }else{
                System.out.println(battleUserListForWin.get(1) +"             winner");

                PreparedStatement winnerQuery =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(1).getUsername()+"'");
                ResultSet winnerRs = winnerQuery.executeQuery();
                User winnerUser=new User();
                while(winnerRs.next()){

                    winnerUser.setElo(winnerRs.getInt(3));
                    winnerUser.setWins(winnerRs.getInt(5));
                }
                PreparedStatement winnerUpdate=connection.prepareStatement("Update users set elo="+(winnerUser.getElo()+1)+" , wins ="+(winnerUser.getWins()+1) +" where username="+battleUserListForWin.get(1).getUsername());
                winnerUpdate.executeUpdate();

                PreparedStatement looserQuery =  connection.prepareStatement("select * from users where username='"+battleUserListForWin.get(0).getUsername()+"'");
                ResultSet looserRs = looserQuery.executeQuery();
                User looserUser=new User();
                while(looserRs.next()){

                    looserUser.setElo(looserRs.getInt(3));
                    looserUser.setLosses(looserRs.getInt(6));
                }
                PreparedStatement looserUpdate=connection.prepareStatement("Update users set elo="+(looserUser.getElo()-1)+" , losses ="+(looserUser.getLosses()+1) +" where username="+battleUserListForWin.get(0).getUsername());
                looserUpdate.executeUpdate();


            }
            }
          //  connection.prepareStatement("")

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        /* String season = null;
        if(myRs.next())
            exist = myRs.getString(1); */

        System.out.println("'EXIST VARIABLE = '");
    //    System.out.println(exist);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


     /*   if (exist == 1){
            BattleUser user1 = new BattleUser(nameTemp, duration, count, type);
            System.out.println("values inserted: ");
            System.out.println(user1.username);
            System.out.println(user1.duration);
            System.out.println(user1.count);
            System.out.println(user1.type);



        } else if (exist == 2)
            {
                BattleUser user2 = new BattleUser(nameTemp, duration, count, type);
                System.out.println("values inserted: ");
                System.out.println(user2.username);
                System.out.println(user2.duration);
                System.out.println(user2.count);
                System.out.println(user2.type);
        } else if(exist==3){
            BattleUser user1 = new BattleUser(nameTemp, duration, count, type);

            System.out.println(duration +"duration");
            System.out.println(count +"count");
          //  System.out.println(duration +"duration");
           // user1.duration = user1.duration + duration;
          //  user1.count = user1.count + count;
            int countTemp=user1.getCount();
            int durationTemp=user1.getDuration();
            user1.setCount(countTemp+count);
            user1.setDuration(durationTemp+duration);
            System.out.println("values updated: ");
            System.out.println(user1.username);
            System.out.println(user1.duration);
            System.out.println(user1.count);
            System.out.println(user1.type);
        }
*/



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
