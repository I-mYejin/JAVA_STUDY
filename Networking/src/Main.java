//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main { //Server
//    public static void main(String[] args) {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(7777);
//            System.out.println(getTime() + "서버가 준비되었습니다.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while (true) {
//            try{
//                System.out.println(getTime() + "연결요청을 기다립니다.");
//                //서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다림
//                //클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성
//                Socket socket = serverSocket.accept();
//                System.out.println(getTime()+socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
//                OutputStream out = socket.getOutputStream();
//                DataOutputStream dos = new DataOutputStream(out);
//
//                dos.writeUTF("[Notice] Test Message1 from Server.");
//                System.out.println(getTime()+"데이터를 전송했습니다.");
//
//                dos.close();
//                socket.close();;
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }//while
//    }//main
//
//    static String getTime(){
//        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
//        return f.format(new Date());
//    }
//}//class