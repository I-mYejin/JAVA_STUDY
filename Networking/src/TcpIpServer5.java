
import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpServer5 {

    public static void main(String[] args) {

        Screen screen = new Screen();

        ServerSocket serverSocket = null;
        Socket socket = null;


        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버가 준비되었습니다.");
            socket = serverSocket.accept();

            Sender sender = new Sender(socket);
            Receiver receiver= new Receiver(socket, screen);

            sender.start();
            receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}//class

