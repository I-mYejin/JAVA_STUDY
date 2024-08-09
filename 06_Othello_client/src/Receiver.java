import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class Receiver extends Thread {
    Socket socket;
    DataInputStream in;
    Home hm;

    Receiver(Socket socket, Home home) {
        this.socket = socket;
        hm = home;
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
                System.out.println(from);
                String[] split = from.split(",");
                int stn_j = Integer.valueOf(split[0]);
                int stn_i = Integer.valueOf(split[1]);
                hm.setOtherStone(stn_j, stn_i); // 상대방에게 받은 j, i 값 넣어서 실행
                //scrn.fromBox(from);

            } catch (IOException e) {}
        }
    }//run
}