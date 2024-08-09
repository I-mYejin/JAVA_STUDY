import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Mypage extends JFrame {
    //Login lgn = new Login();
    UserInfo userInfo;

    JLabel user_name;
    JLabel user_birth;
    JLabel user_id;
    JLabel user_work;
    JLabel user_gender;
    JLabel user_married;


    Mypage_panel panel_m1 = new Mypage_panel();
    Sql my_sql = new Sql();//패널 생성

    File file = new File("/Users/imagine/Downloads/User Page-5.png");
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
            if(((JButton)e.getSource()).getText().equals("회원정보 수정")){
                System.out.println("[화면 이동] 마이페이지 -> 마이페이지 수정");
                setVisible(false);
                new Myedit(userInfo).setVisible(true);
            }
            else if(((JButton)e.getSource()).getText().equals("상담하기")){
                System.out.println("[화면 이동] 마이페이지 -> 상담");
                setVisible(false);
                new Consult(userInfo).setVisible(true);
            }
            else if(((JButton)e.getSource()).getText().equals("로그아웃")){
                System.out.println("[화면 이동] 마이페이지 -> 로그인");
                setVisible(false);
                new Login().setVisible(true);
            }
        }
    }


    public Mypage(UserInfo info) { // 생성자
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        userInfo = info;

        /*PANEL*/
        panel_m1.setBounds(0, 0, 1280, 832);
        panel_m1.setLayout(null);

        /*유저 정보 출력 LABEL*/
//        Login.u_id
        user_name = new JLabel(userInfo.name);
        user_name.setBounds(123, 287, 100, 40);
        user_name.setFont(new Font("Arial", Font.PLAIN, 32));
        panel_m1.add(user_name);

        user_birth = new JLabel(userInfo.birth);
        user_birth.setBounds(125, 335, 100, 20);
        user_birth.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_m1.add(user_birth);

        user_id = new JLabel(userInfo.id);
        user_id.setBounds(605, 274, 100, 20);
        user_id.setFont(new Font("Arial", Font.BOLD, 16));
        panel_m1.add(user_id);

        user_work = new JLabel(userInfo.work);
        user_work.setBounds(590, 309, 200, 20);
        user_work.setFont(new Font("Arial", Font.BOLD, 16));
        panel_m1.add(user_work);

        user_gender = new JLabel(userInfo.gender);
        user_gender.setBounds(590, 344, 200, 20);
        user_gender.setFont(new Font("Arial", Font.BOLD, 16));
        panel_m1.add(user_gender);

        user_married = new JLabel(userInfo.married);
        user_married.setBounds(650, 379, 200, 20);
        user_married.setFont(new Font("Arial", Font.BOLD, 16));
        panel_m1.add(user_married);




        /*BUTTON*/
        JButton btn_my_edit = new JButton("회원정보 수정"); // 회원가입 버튼
        btn_my_edit.addActionListener(panel_m1);
        btn_my_edit.setBounds(920,427,120,30);
        btn_my_edit.setContentAreaFilled(false);
        btn_my_edit.setFocusPainted(false);
        btn_my_edit.setBorderPainted(false);
        btn_my_edit.setFont(new Font("Inter", Font.PLAIN, 13));
        panel_m1.add(btn_my_edit);

        JButton btn_logout = new JButton("로그아웃"); // 회원가입 버튼
        btn_logout.addActionListener(panel_m1);
        btn_logout.setBounds(120,743,100,20);
        btn_logout.setContentAreaFilled(false);
        btn_logout.setFocusPainted(false);
        btn_logout.setBorderPainted(false);
        btn_logout.setFont(new Font("Inter", Font.PLAIN, 16));
        panel_m1.add(btn_logout);

        JButton btn_council = new JButton("상담하기"); // 회원가입 버튼
        btn_council.addActionListener(panel_m1);
        btn_council.setBounds(705,569,130,27);
        btn_council.setForeground(Color.WHITE);
        btn_council.setContentAreaFilled(false);
        btn_council.setFocusPainted(false);
        btn_council.setBorderPainted(false);
        btn_council.setFont(new Font("Inter", Font.PLAIN, 20));
        panel_m1.add(btn_council);

        add(panel_m1); //add 나중에
        setVisible(true);
    }
}
