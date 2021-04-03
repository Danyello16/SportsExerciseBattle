package MCTG.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class UserHandling {

    public static int registration(String payload) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(payload);
        String usernameTemp = jsonNode.get("Username").asText();
        String passwordTemp = jsonNode.get("Password").asText();

        User user1 = new User(usernameTemp,passwordTemp);

        ResultSet myRs = null;

        String compareUser;
        int count = 0;

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM users")
        ){
            myRs = statement2.executeQuery();

            while(myRs.next()) {

                String name = myRs.getString(1);
                if(name.equals(usernameTemp)) {
                    count++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(count==0){
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username,password, elo, accName) VALUES(?,?,?,?,?);")
            ){
                //statement.setInt(1,13);
                statement.setString(1, user1.getUsername());
                statement.setString(2, user1.getPassword());
                statement.setInt(4, user1.getElo());
                statement.setString(5, user1.getUsername());
                statement.execute();
                return 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else {
            return 1;
        }
        return 1;
    };

    public static int login(String payload) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(payload);
        String usernameTemp = jsonNode.get("Username").asText();
        String passwordTemp = jsonNode.get("Password").asText();

        int counter = 0;
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM users")
        ){
            myRs = statement2.executeQuery();

            while(myRs.next()) {
                String name = myRs.getString(1);
                String pwd = myRs.getString(2);
                //System.out.println(name+pwd);
                if(name.equals(usernameTemp) && pwd.equals(passwordTemp)) {
                    counter++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(counter == 1){
            return 0;
        }else{
            return 1;
        }
    };

    //when User wants to GET User Data, check if the right Token is given
    public static int checkTokenForUserData1(String path ,List<String> headers){
        String authToken = headers.get(2);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        if(!path.contains(nameTemp)){
            return 1;
        }else{
            return 0;

        }
    }
    //when User wants to edit User Data with PUT, check if the right Token is given
    public static int checkTokenForUserData2(String path ,List<String> headers){
        String authToken = headers.get(3);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        if(!path.contains(nameTemp)){
            return 1;
        }else{
            return 0;

        }
    }

    public static String showUserData(List<String> headers){
        String authToken = headers.get(2);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        //all Data of User in a String
        String allDataOfUser = null;
        int counter = 0;

        //save cards from a user into string
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement03 = connection.prepareStatement("SELECT * FROM users WHERE lower(userName) LIKE ?;")
        ){
            //name + wildcard, if name is changed when edited
            statement03.setString(1, nameTemp + "%");
            myRs = statement03.executeQuery();

            while(myRs.next()) {

                String userName = myRs.getString(9);
                String bio = myRs.getString(5);
                String image = myRs.getString(6);


                ObjectMapper mapper = new ObjectMapper();
                UserData user01 = new UserData(userName, bio, image);

                // pretty print
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user01);
                if(counter == 0) {
                    allDataOfUser = nameTemp + " Data:\n" + json;
                    counter++;
                }
                else {
                    allDataOfUser = allDataOfUser + json;
                }

            }

        } catch (SQLException | JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return allDataOfUser;
    }


    public static void editUserData(String payload,List<String> headers) throws JsonProcessingException {
        String authToken = headers.get(3);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        //saving Data from Input
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        String usernameTemp = jsonNode.get("Name").asText();
        String bioTemp = jsonNode.get("Bio").asText();
        String imageTemp = jsonNode.get("Image").asText();

        UserData user02 = new UserData(usernameTemp, bioTemp,imageTemp);
        System.out.println(user02.getUsername() + user02.getBio() + user02.getImage());


        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgre");
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET accName = ?, bio = ?, image = ? WHERE userName = ?;");
        ){
            statement.setString(1, user02.getUsername());
            statement.setString(2, user02.getBio());
            statement.setString(3, user02.getImage());
            statement.setString(4, nameTemp);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
