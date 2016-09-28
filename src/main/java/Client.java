import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {
    private final int PORT = 9002;
    private final String IP = "10.30.0.166";
    private Socket socket;
    private int id;
    private PrintWriter out;
    private BufferedReader in;
    private static AtomicInteger count = new AtomicInteger(0);


    public Client(int id) throws IOException {
        this.id = id;
    }

    public void run() {
        try {
            socket = new Socket(IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            count.incrementAndGet();
            if(count.get() == 9999)
                System.out.println("10000");
            while(true) {
                if (in.ready()) {
                    String val = in.readLine();
                    System.out.println(val);

                   // System.out.println(val);
                    if("FINALLY".equals(val))
                        break;

                    if(sendMessage(val)==0)
                        break;

                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //System.out.println("SOCKET CLOSE");
                System.out.println("########### ----- " + Thread.activeCount());
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
    private int sendMessage(String serverCommand) {
        switch (serverCommand) {
            case "REQUEST_CLIENT_ID":
                out.println(id);
                break;
            case "REQUEST_CLIENT_NAME":
                out.println("Client " + id);
                break;
            case "REQUEST_CLIENT_LEVEL":
                Random random = new Random();
                out.println(random.nextInt(100));
                break;
            case "REQUEST_CLIENT_ROUTE":
                out.println("Route");
                break;
            case "WAIT_FOR_OPPONENT":
                out.println("wait");
                break;
            case "START":
                out.println("start");
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
        return 1;
    }
}
