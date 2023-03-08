package EXO51;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private  String clientUsername;
    private static String username;
//    private boolean stop =false;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            username = clientUsername;
            broadcastMessage("Server : "+clientUsername+" has entred the chat");
        }catch (IOException e){
            closeTotal(socket,bufferedWriter,bufferedReader);
        }
    }

    @Override
    public void run() {
        System.out.println("Server : "+clientUsername+" has entred the chat");
        String clientMessage;
        while (socket.isConnected()){
            try {
                clientMessage= bufferedReader.readLine();
               if (!clientMessage.contains("logout")) {
                   broadcastMessage(clientMessage);
                   System.out.println(clientMessage);
               }else{
                   removeClientHandler();
               }

            }catch (IOException e){
                closeTotal(socket,bufferedWriter,bufferedReader);
                break;
            }

        }

    }
    public void broadcastMessage(String message){

        for (ClientHandler clientHandler:clientHandlers){
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeTotal(socket,bufferedWriter,bufferedReader);
            }

        }

    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("Server : "+clientUsername+" has disconnected");
        System.out.println("Server : "+clientUsername+" has disconnected");
    }
    public void closeTotal(Socket socket,BufferedWriter bufferedWriter,BufferedReader bufferedReader){
        removeClientHandler();
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public static String getClientUsername() {
        return username;
    }
}
