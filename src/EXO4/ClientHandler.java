package EXO4;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    public PrintWriter out;
    private OutputStream outputStream;
    private ArrayList<ClientHandler> clients;
    private static boolean gameOver = false;
    public ClientHandler(Socket clientSocket,ArrayList<ClientHandler> clients) throws IOException {
        this.client =clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);

    }


    public static boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void run() {
        out.println("Hello clients , I have generated a number guess what is it");
            try {
                while (true){
                    String number = in.readLine();
                    int myNumber = Integer.parseInt(number);
//                    if (number.equals(String.valueOf(Server.getSecretNumber()))){
//                        out.println("Congratulation you found the number");
//
//                    }else{
//                        out.println("Wrong number try again");
//                    }

                    if (myNumber < 1 || myNumber > 100) {
                        out.println("Invalid input. Please enter a number between 1 and 100.");
                        continue;
                    }

                    if (myNumber < Server.getSecretNumber()) {
                        out.println("Too low. Guess again.");
                    } else if (myNumber > Server.getSecretNumber()) {
                        out.println("Too high. Guess again.");
                    } else if(myNumber == Server.getSecretNumber()) {
//                        out.println("Congratulations, you guessed the secret number\n GameOver");
                        Server.broadcast("Game is over");
//                        gameOver = true;
                    }
                }
            }
            catch (IOException e){
                System.err.println("IO exception in client handler");
                System.err.println(e.getStackTrace());
            }
                finally {
                out.close();
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }


}
