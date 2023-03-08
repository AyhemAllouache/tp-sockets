package EXO4;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;


public class Server {
    private static final int PORT = 4444;
    private static int secretNumber;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(PORT);
        secretNumber = new Random().nextInt(100) + 1;
        System.out.println("The secret number is : "+ secretNumber);
        try {
            while (true) {
                System.out.println("Server waiting for client Connection ...");
                Socket client = listener.accept();
                System.out.println("Client has conencted to server : "+ client.getPort());
                ClientHandler clientThread = new ClientHandler(client,clients);
                clients.add(clientThread);
                pool.execute(clientThread);

            }
        }catch (SocketException e){
            System.err.println(e);
        }





    }
    public static int getSecretNumber() {
        return secretNumber;
    }

    public static synchronized void broadcast(String message) {
        for (ClientHandler cliente : clients) {
                cliente.out.println("Game is over");
        }
    }
}
