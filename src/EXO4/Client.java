package EXO4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(SERVER_IP,SERVER_PORT);

        ServerConnection serverCon = new ServerConnection(socket);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
//        String hello = input.readLine();
//        System.out.println("Server said : "+hello);
        new Thread(serverCon).start();
        while (true){
            String command = keyboard.readLine();
            if (command.equals("stop")) break;

            out.println(command);

        }
//        String ServerResponse2  = input.readLine();
//        System.out.println("server said  : " + ServerResponse2);
    }
}
