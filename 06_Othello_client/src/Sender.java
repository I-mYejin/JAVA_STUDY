import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class Sender extends Thread {
    Socket socket;
    static DataOutputStream out;
    static String name;

    Sender(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            name = "임예진: ";

            //name = "[" + socket.getInetAddress() + ": " + socket.getPort() + "]";
        } catch (Exception e) {
        }
    }

    static public void exest(int j, int i) {
        try {
            System.out.println("[" + j +","+i+"]");
            out.writeUTF(j +","+i);
            //out.writeUTF(name + scanner.nextLine());
        } catch (IOException e) {}
    } //run
}
