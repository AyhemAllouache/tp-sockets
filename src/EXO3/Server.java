package EXO3;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 4444;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(PORT);

        while (true) {
            System.out.println("Server waiting for client Connection ...");
            Socket client = listener.accept();
            System.out.println("Server connected to client");
            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);
            pool.execute(clientThread);
        }

    }
}
