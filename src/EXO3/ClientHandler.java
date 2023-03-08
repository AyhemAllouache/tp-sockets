package EXO3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.client =clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);

    }



    @Override
    public void run() {
            try {
                while (true){
                    out.println("Please entrer l'expression");
                    String request = in.readLine();
                    if (!request.equals("")){
                        Eval eval = new Eval();
                        String result = eval.evaluateMathExpression(request);
                        out.println(result);
                    }else{
                        out.println("Entrer quelque chose");
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
