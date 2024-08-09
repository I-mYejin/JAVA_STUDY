import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Math extends JFrame {
    //Login lgn = new Login();
    UserInfo userInfo;

    int femal = 0;
    int male = 0;


    Mypage_panel panel_mth = new Mypage_panel();
    Sql my_sql = new Sql();//패널 생성

    File file = new File("/Users/imagine/Downloads/math-3.png");
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

            for(int i = 0; i<femal; i++){
                super.paintComponents(g);
                g.setColor(new Color(180, 180, 180));
                g.fillRoundRect(885, 559-40*i, 25, 65, 25, 25);
            }
            for(int i = 0; i<male; i++){
                super.paintComponents(g);
                g.setColor(new Color(131, 131, 131));
                g.fillRoundRect(641, 559-40*i, 25, 65, 25, 25);
            }

        }

        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getText().equals("전체목록")){
                System.out.println("[화면 이동] 관리자 통계 -> 관리자 메인");
                setVisible(false);
                new Manager(userInfo).setVisible(true);
            }
            else if(((JButton)e.getSource()).getText().equals("로그아웃")){
                System.out.println("[화면 이동] 관리자 통계 -> 로그인");
                setVisible(false);
                new Login().setVisible(true);
            }
        }
    }


    public Math() { // 생성자
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //userInfo = info;

        /*PANEL*/
        panel_mth.setBounds(0, 0, 1280, 832);
        panel_mth.setLayout(null);

        /*LABEL*/
        JLabel manager = new JLabel("관리자");
        manager.setBounds(123, 287, 100, 40);
        manager.setFont(new Font("Arial", Font.PLAIN, 32));
        panel_mth.add(manager);

        /*GRAPH*/
        try {
            femal = my_sql.selectFemalData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("female: "+femal);

        try {
            male = my_sql.selectMaleData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("male: "+male);


        /*BUTTON*/
        JButton btn_logout = new JButton("로그아웃"); //
        btn_logout.addActionListener(panel_mth);
        btn_logout.setBounds(120,743,100,20);
        btn_logout.setContentAreaFilled(false);
        btn_logout.setFocusPainted(false);
        btn_logout.setBorderPainted(false);
        btn_logout.setFont(new Font("Inter", Font.PLAIN, 16));
        panel_mth.add(btn_logout);

        JButton btn_list = new JButton("전체목록"); //
        btn_list.addActionListener(panel_mth);
        btn_list.setBounds(25,398,150,30);
        btn_list.setContentAreaFilled(false);
        btn_list.setFocusPainted(false);
        btn_list.setBorderPainted(false);
        btn_list.setFont(new Font("Inter", Font.PLAIN, 20));
        panel_mth.add(btn_list);


        add(panel_mth); //add 나중에
        setVisible(true);
    }
}
