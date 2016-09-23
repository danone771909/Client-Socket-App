import java.io.IOException;
import java.net.Socket;

public class Launcher {
    private static final int PORT = 9002;
    private static final String IP = "127.0.0.1";
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        for(int i=0; i<2; i++) {
            Runnable r = new Client(i);
            new Thread(r).start();
        }
    }
}
