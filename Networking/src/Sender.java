import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
            //name = "[" + socket.getInetAddress() +": "+socket.getPort() + "]";
            name = "임예진: ";
        } catch (Exception e) {}
    }

    static public void exest(String msg1) {
        try {
            System.out.println("msg1: " + msg1);
            out.writeUTF(name + msg1);
                //out.writeUTF(name + scanner.nextLine());
        } catch (IOException e) {}
    } //run
}
