import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Login extends JFrame {
    UserInfo userInfo;

    Login_panel panel_l1 = new Login_panel(); //패널 생성
    Sql my_sql = new Sql();
    boolean send = true;
    JLabel error_id = new JLabel();
    JLabel error_pwd = new JLabel();

    File file = new File("/Users/imagine/Downloads/Login_background-6.png");
    BufferedImage image;

    JTextField id;
    JPasswordField pwd;

    /*Panel + Image*/
    class Login_panel extends JPanel implements ActionListener{

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
            if(((JButton)e.getSource()).getText().equals("로그인")){
                String get_pwd = pwd.getText();
                //my_sql.selectData(id.getText());
                //u_id = id.getText();
                //
                try {
                    if(my_sql.selectIdData(id.getText()) == 0){
                        send = false;
                        error_id.setBounds(432, 383, 170, 15);
                        error_id.setFont(new Font("Inter", Font.PLAIN, 12));
                        error_id.setText("*존재하지 않는 아이디입니다");
                        error_id.setForeground(Color.RED);
                        error_id.setVisible(true);
                        error_pwd.setVisible(false);
                        panel_l1.add(error_id);
                    }
                    else if(!my_sql.selectData(id.getText()).password.equals(get_pwd)){
                        System.out.println("userInfo.pwd: "+ my_sql.selectData(id.getText()).password);
                        System.out.println("get_pwd: "+ get_pwd);
                        send = false;
                        error_pwd.setBounds(447, 476, 170, 15);
                        error_pwd.setFont(new Font("Inter", Font.PLAIN, 12));
                        error_pwd.setText("*비밀번호가 일치하지 않습니다");
                        error_pwd.setForeground(Color.RED);
                        error_id.setVisible(false);
                        error_pwd.setVisible(true);
                        panel_l1.add(error_pwd);
                    }
                    else send = true;


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if(send == true){
                    System.out.println("[화면 이동] 로그인 -> 마이페이지");
                    try {
                        userInfo = my_sql.selectData(id.getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                    if(userInfo.id.equals("rainyjin")){
                        new Manager(userInfo).setVisible(true);
                    }
                    else new Mypage(userInfo).setVisible(true);
                }
            }


            else if(((JButton)e.getSource()).getText().equals("회원가입")){
                System.out.println("[화면 이동] 로그인 -> 회원가입");
                setVisible(false);
                new Signup().setVisible(true);
            }
        }
    }


    public Login() { // 생성자
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        /*PANEL*/
        panel_l1.setBounds(0, 0, 1280, 832);
        panel_l1.setLayout(null);

        /*Text Fild*/
        id = new JTextField();
        pwd = new JPasswordField();
        id.setBackground(Color.WHITE);
        pwd.setBackground(Color.WHITE);
        id.setBounds(390, 409, 509, 32);
        pwd.setBounds(390, 501, 509, 32);
        id.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        pwd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_l1.add(id);
        panel_l1.add(pwd);

        /*BUTTON*/
        JButton btn_login = new JButton("로그인"); // 회원가입 버튼
        btn_login.addActionListener(panel_l1);
        btn_login.setBounds(519,645,242,40);
        btn_login.setForeground(Color.WHITE);
        btn_login.setContentAreaFilled(false);
        btn_login.setFocusPainted(false);
        btn_login.setBorderPainted(false);
        btn_login.setFont(new Font("Inter", Font.PLAIN, 24));
        panel_l1.add(btn_login);

        JButton btn_signup = new JButton("회원가입"); // 회원가입 버튼
        btn_signup.addActionListener(panel_l1);
        btn_signup.setBounds(519,696,242,40);
        btn_signup.setContentAreaFilled(false);
        btn_signup.setFocusPainted(false);
        btn_signup.setBorderPainted(false);
        btn_signup.setFont(new Font("Inter", Font.PLAIN, 24));
        panel_l1.add(btn_signup);

        add(panel_l1); //add 나중에
        setVisible(true);
    }
}
