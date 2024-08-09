import java.io.IOException;
import java.net.ConnectException;
import java.net.*;

public class TcpIpClient5 {
    public static void main(String[] args) {

        Screen screen = new Screen();

        try{
            String serverIp = "172.17.207.148";
            //내꺼 172.18.147.227
            //수민이꺼 172.17.207.148
            //현재오빠 172.17.219.17
            Socket socket = new Socket(serverIp, 7777);
            System.out.println("서버에 연결되었습니다.");
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket, screen);

            sender.start();
            receiver.start();

        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}//class


//class Receiver extends Thread {
//    Socket socket;
//    DataInputStream in;
//
//    Receiver(Socket socket) {
//        this.socket = socket;
//        try {
//            in = new DataInputStream(socket.getInputStream());
//        } catch (IOException e) {}
//    }
//
//    public void run() {
//        while(in!=null){
//            try {
//                System.out.println(in.readUTF());
//            } catch (IOException e) {}
//        }
//    }//run
//}

