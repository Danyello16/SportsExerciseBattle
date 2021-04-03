package MCTG.RestHttp;

import MCTG.Battle.BattleHandler;
import MCTG.Users.StatsAndScoreHandler;
import MCTG.Users.UserHandling;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;


// NU AVEM NIMIC CU HISTORY, TRE SA FACEM
// NICI CU TOURNAMENT

public class RequestHandler {
    private static int battleCounter = 1;
    private static String Player1;
    private static String Player2;

    public static void pathsHandling(String path, String method, String payload, List<String> headers, Socket client) throws IOException {

        UserHandling user = new UserHandling();
        StatsAndScoreHandler statsAndScore = new StatsAndScoreHandler();
        BattleHandler battle = new BattleHandler();
        ResponseHandler responder = new ResponseHandler();

        //USER REGISTRATION AND LOGIN
        if(path.equals("/users") && method.equals("POST")){

            int number = user.registration(payload);

            if(number == 0)
                client.getOutputStream().write(responder.responseRegistrationPOST().getBytes(StandardCharsets.UTF_8));
            else if (number == 1)
                client.getOutputStream().write(responder.responseErrorRegistrationPOST().getBytes(StandardCharsets.UTF_8));
        }
        else if(path.equals("/sessions")&& method.equals("POST")){
            int number = user.login(payload);

            if(number == 0)
                client.getOutputStream().write(responder.responseLoginPOST().getBytes(StandardCharsets.UTF_8));
            else if (number == 1)
                client.getOutputStream().write(responder.responseErrorLoginPOST().getBytes(StandardCharsets.UTF_8));
        }

        //show User Data
        else if(path.contains("/users")&& method.equals("GET")){
            int checkToken = user.checkTokenForUserData1(path,headers);

            if(checkToken == 0){
                //if Token and Header are the same
                System.out.println("Token Check successful...");

                String allData = user.showUserData(headers);
                String httpResponse = responder.responseUserDataGET() + allData;
                client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));

            }else{
                // sendError
                System.out.println("False Token...");
                client.getOutputStream().write(responder.responseErrorUserDataGETAndPOST().getBytes(StandardCharsets.UTF_8));
            }

        }
        //edit User Data
        else if(path.contains("/users")&& method.equals("PUT")){
            int checkToken2 = user.checkTokenForUserData2(path,headers);

            if(checkToken2 == 0){
                //if Token and Header are the same
                System.out.println("Token Check successful...");
                user.editUserData(payload,headers);
                client.getOutputStream().write(responder.responseUserDataPUT().getBytes(StandardCharsets.UTF_8));


            }else{
                // sendError
                System.out.println("False Token...");
                client.getOutputStream().write(responder.responseErrorUserDataGETAndPOST().getBytes(StandardCharsets.UTF_8));
            }

        }
        //show Stats of User
        else if(path.equals("/stats")&& method.equals("GET")){

            //check if Token is given (Header)
            if(headers.size()!=2){
                System.out.println("Stats will be shown...");
                String allInfo = statsAndScore.showStats(headers);
                String httpResponse = responder.responseStatsGET() + allInfo;
                client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
            }else{
                client.getOutputStream().write(responder.responseErrorStats().getBytes(StandardCharsets.UTF_8));
            }

        }
        //show Scoreboard
        else if(path.equals("/score")&& method.equals("GET")){

            //check if Token is given (Header)
            if(headers.size()!=2){
                System.out.println("Scoreboard will be shown...");
                String allInfo = statsAndScore.showScoreboard();
                String httpResponse = responder.responseScoreGET() + allInfo;
                client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
            }else{
                client.getOutputStream().write(responder.responseErrorStats().getBytes(StandardCharsets.UTF_8));
            }

        }
        //BATTLE!!!
        else if(path.equals("/battles")&& method.equals("POST")){
            if((battleCounter % 2 == 1)){
                Player1 = battle.getPlayersName(headers);
                client.getOutputStream().write(responder.responseBattle1POST().getBytes(StandardCharsets.UTF_8));
            }else{
                Player2 = battle.getPlayersName(headers);
                System.out.println(Player1 + " vs "+ Player2);
                String battleLog = battle.letsBattle(Player1,Player2);
                String httpResponse = responder.responseBattle2POST() + battleLog;
                client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
            }
            battleCounter++;
        }

    }
}
