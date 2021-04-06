package MCTG.RestHttp;

public class ResponseHandler {

    //------ THESE ARE ALL RESPONSES WITH SPECIFIED MESSAGES ----------


    public String responseHistoryPOST(){
        String response = "POST Request SUCCESS\nAdded entry to history - Added player to tournament";
        String httpResponse = "HTTP/1.1 201 Created\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 180 \r\n"
                + "Content-Lenght: 58 \r\n\r\n" + response;
        return httpResponse;
    }

    public String responseRegistrationPOST(){
        String response = "POST Request SUCCESS\nUser is now in the Database";
        String httpResponse = "HTTP/1.1 201 Created\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }

    public String responseErrorRegistrationPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 16 \r\n\r\n" + "Already Exists! Or Name is already been used!";
        return httpResponse;
    }

    public String responseLoginPOST(){
        String response = "POST Request SUCCESS\nUser is now logged in";
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }

    public String responseErrorLoginPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 16 \r\n\r\n" + "Could not Log in.. Try again";
        return httpResponse;
    }


    public String responseUserDataGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "The Token has been approved... This is the Users Data:\n";
        return httpResponse;
    }

    public String tournament1GET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 220 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "No tournament started yet!!!\n";
        return httpResponse;
    }

    public String responseErrorUserDataGETAndPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "The wrong Token has been given...";
        return httpResponse;
    }

    public String responseUserDataPUT(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "Users Data has been edited and updated\n";
        return httpResponse;
    }

    public String responseErrorStats(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "No Token is given... Try again";
        return httpResponse;
    }

    public String responseStatsGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "This is the Users Stats: \n";
        return httpResponse;
    }

    public String responseScoreGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "This is the Scoreboard: \n";
        return httpResponse;
    }

    public String responseBattle1POST(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "Waiting for another Player... \n";
        return httpResponse;
    }
    public String responseBattle2POST(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n";
        return httpResponse;
    }


}
