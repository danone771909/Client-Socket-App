import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Launcher {
    private static final int PORT = 9002;
    private static final String IP = "127.0.0.1";
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        for(int i=0; i<100; i++) {
            Runnable r = new Client(i);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(r).start();


        }
    }
}
