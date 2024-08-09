import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    Socket socket;
    DataInputStream in;
    Screen scrn;

    Receiver(Socket socket, Screen screen) {
        this.socket = socket;
        scrn = screen;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {}
    }

//    static public void rcv(String from){
//        while(in != null){
//            try {
//                String from = in.readUTF();
//                //System.out.println(in.readUTF());
//            } catch (IOException e) {}
//        }
//    }

    public void run() {
        while(in!=null){
            try {
//                System.out.println(in.readUTF());
//                System.out.println("in.readUTF(): " + in.readUTF());
                String from = in.readUTF();
                System.out.println("from: " + from);
                scrn.fromBox(from);

            } catch (IOException e) {}
        }
    }//run
}
