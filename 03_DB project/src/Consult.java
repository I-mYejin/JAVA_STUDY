import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Consult extends JFrame {
    //Login lgn = new Login();
    UserInfo userInfo;

    JLabel user_name;
    JLabel user_birth;


    Mypage_panel panel_c1 = new Mypage_panel();

    File file = new File("/Users/imagine/Downloads/heart.png");
    BufferedImage image;

    /*Panel + Image*/
    class Mypage_panel extends JPanel implements ActionListener{

        public void paintComponent(Graphics g) {
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(image, 0,0, 1280, 832, this);
        }

        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getText().equals("뒤로가기")){
                System.out.println("[화면 이동] 상담페이지 -> 마이페이지");
                setVisible(false);
                new Mypage(userInfo).setVisible(true);
            }
            else if(((JButton)e.getSource()).getText().equals("로그아웃")){
                System.out.println("[화면 이동] 상담페이지 -> 로그인");
                setVisible(false);
                new Login().setVisible(true);
            }
        }
    }


    public Consult(UserInfo info) { // 생성자
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        userInfo = info;

        /*PANEL*/
        panel_c1.setBounds(0, 0, 1280, 832);
        panel_c1.setLayout(null);

        /*유저 정보 출력 LABEL*/
//        Login.u_id
        user_name = new JLabel(userInfo.name);
        user_name.setBounds(123, 287, 100, 40);
        user_name.setFont(new Font("Arial", Font.PLAIN, 32));
        panel_c1.add(user_name);

        user_birth = new JLabel(userInfo.birth);
        user_birth.setBounds(125, 335, 100, 20);
        user_birth.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_c1.add(user_birth);




        /*BUTTON*/
        JButton btn_logout = new JButton("로그아웃"); // 회원가입 버튼
        btn_logout.addActionListener(panel_c1);
        btn_logout.setBounds(120,743,100,20);
        btn_logout.setContentAreaFilled(false);
        btn_logout.setFocusPainted(false);
        btn_logout.setBorderPainted(false);
        btn_logout.setFont(new Font("Inter", Font.PLAIN, 16));
        panel_c1.add(btn_logout);

        JButton btn_back = new JButton("뒤로가기"); // 회원가입 버튼
        btn_back.addActionListener(panel_c1);
        btn_back.setBounds(703,667,130,27);
        btn_back.setForeground(Color.WHITE);
        btn_back.setContentAreaFilled(false);
        btn_back.setFocusPainted(false);
        btn_back.setBorderPainted(false);
        btn_back.setFont(new Font("Inter", Font.PLAIN, 20));
        panel_c1.add(btn_back);

        add(panel_c1); //add 나중에
        setVisible(true);
    }
}

