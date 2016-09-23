import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {
    private final int PORT = 9002;
    private final String IP = "127.0.0.1";
    private Socket socket;
    private int id;
    private PrintWriter out;
    private BufferedReader in;


    public Client(int id) throws IOException {
        this.id = id;
    }

    public void run() {
        try {
            socket = new Socket(IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true) {

//                try {
                if (in.ready()) {
                    String val = in.readLine();

                    System.out.println(val);
                    if("FINALLY".equals(val))
                        break;

                    sendMessage(val);
                }

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                } catch (Exception e) {
//                    reconnect();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("SOCKET CLOSE");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void reconnect() {
        try {
            socket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendMessage(String serverCommand) {
        switch (serverCommand) {
            case "REQUEST_CLIENT_ID":
                out.println(id);
                break;
            case "REQUEST_CLIENT_NAME":
                out.println("Client " + id);
                break;
            case "REQUEST_CLIENT_LEVEL":
                out.println(1);
                break;
            case "REQUEST_CLIENT_ROUTE":
                out.println("Route");
                break;
            case "WAIT_FOR_OPPONENT":
                out.println("wait");
                break;
            case "CHECKPOINT":
                out.println(new Random().nextInt(20));
                break;
            case "FINALLY":
                out.println("Finally");
                break;
            default:
                out.println("1");
                break;
        }
    }
}
