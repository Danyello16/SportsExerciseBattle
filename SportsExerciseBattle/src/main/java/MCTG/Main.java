package MCTG;
import MCTG.RestHttp.RequestContext;
import MCTG.RestHttp.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to MONSTER TRADING CARDS GAME - MTCG!");

        ServerSocket serverSocket;
        try {
            //ready to listen incoming connection on port 8080
            serverSocket = new ServerSocket(10001);
            System.out.println("Listening for connection on port 10001 ...");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            while (true) {
                //accepting incoming connection
                Socket socket = serverSocket.accept();
                handleRequest(socket);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket client) throws IOException{

        //read the content of the InputStream with BufferedReader
        BufferedReader breader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder build = new StringBuilder();
        String lines;
        StringBuilder content = new StringBuilder();

        //only HTTP Form Terms
        while (!(lines = breader.readLine()).isBlank()) {
            build.append(lines + "\r\n");
        }
        String request = build.toString();
        System.out.println(request);

        RequestContext requestClient = new RequestContext(request);
        requestClient.printContexts();

        //--------------------------------
        //which Request is being received
        String usedMethod = requestClient.getMethod();


            System.out.println(requestClient.getPath());

            if((usedMethod.equals("POST") || usedMethod.equals("PUT")) && !requestClient.getPath().contains("battles")){
                int counter = Integer.parseInt(requestClient.getContentLength());
                int value;
                //saving payload by characters
                for (int j = 0; j < counter; j++) {
                    value = breader.read();
                    content.append((char) value);
                }
                //payload being set
                requestClient.setPayload(content.toString());

                System.out.println("Message:");
                System.out.println(requestClient.getPayload());
            }


        RequestHandler handlingR = new RequestHandler();
        handlingR.pathsHandling(requestClient.getPath(),requestClient.getMethod(),requestClient.getPayload(),requestClient.getHeaders(),client);



        client.close();
    }

}
