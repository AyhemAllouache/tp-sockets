package EXO4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private Socket server;
    private BufferedReader in;
    public PrintWriter out;
    public ServerConnection(Socket s) throws IOException {
        server =s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(),true);
    }

    @Override
    public void run() {

        try {
            while (true) {

                String ServerResponse = in.readLine();
                if (ServerResponse == null) break;
                System.out.println("server said  : " + ServerResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
