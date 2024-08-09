import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class Receiver extends Thread {
    Socket socket;
    DataInputStream in;
    Screen scrn;

    Receiver(Socket socket, Screen scrn) {
        this.scrn = scrn;
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {}
    }

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