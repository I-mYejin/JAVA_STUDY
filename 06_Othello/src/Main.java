import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        Home home = new Home();

        //Screen screen = new Screen();

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버가 준비되었습니다.");
            socket = serverSocket.accept();

            Sender sender = new Sender(socket);
            Receiver receiver= new Receiver(socket, home);

            sender.start();
            receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}