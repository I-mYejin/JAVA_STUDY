import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    public void start() {
        setSize(1280, 832);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel panel_login = new JPanel();  //로그인 패널
        add(panel_login); //add 나중에
        setVisible(true);

        JPanel panel_signup = new JPanel();  //회원가입 패널
        //add(panel_signup);
    }

//    class button1 implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            if(((JButton)e.getSource()).getText().equals("회원가입")){
//                new Signup();
//                System.out.println("[화면 이동] 로그인 -> 회원가입");
//                setVisible(false); //창 안보이게 하기
//            }
//            else if(((JButton)e.getSource()).getText().equals("(회원가입)완료")){
//                new Login();
//                System.out.println("[화면 이동] 회원가입 -> 로그인");
//                setVisible(false); //창 안보이게 하기
//            }
//        }
//    }
}
