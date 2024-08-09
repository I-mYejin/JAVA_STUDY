import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Myedit extends JFrame {
    UserInfo userInfo;
    JLabel user_name;
    JLabel user_birth;
    JLabel user_id;
    boolean send = true;
    Sql my_sql = new Sql();

    Myedit_panel panel_e1 = new Myedit_panel(); //패널 생성

    File file = new File("/Users/imagine/Downloads/User Edit Page-8.png");
    BufferedImage image;

    JPasswordField pwd1;
    JPasswordField pwd2;
    JTextField name;
    JTextField work;
    JDateChooser birth = new JDateChooser();
    JRadioButton marry_n = new JRadioButton("없음");
    JRadioButton marry_y = new JRadioButton("재혼");
    JRadioButton m = new JRadioButton("남자");
    JRadioButton fm = new JRadioButton("여자");

    JLabel error_id = new JLabel();
    JLabel error_pwdD = new JLabel();
    JLabel error_pwdL = new JLabel();
    JLabel error_else = new JLabel();


    /*Panel + Image*/
    class Myedit_panel extends JPanel implements ActionListener{

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
            if(((JButton)e.getSource()).getText().equals("완료")){
                //예외 처리
                String get_pwd1 = pwd1.getText();
                String get_pwd2 = pwd2.getText();
                int pwd_size = get_pwd1.length();
                int name_size = userInfo.name.length();
                int work_size = userInfo.work.length();

                if(pwd_size != 6){
                    send = false;
                    error_pwdL.setBounds(627, 340, 170, 15);
                    error_pwdL.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_pwdL.setText("*숫자 6자리를 입력해주세요");
                    error_pwdL.setForeground(Color.RED);
                    error_pwdL.setVisible(true);
                    panel_e1.add(error_pwdL);
                }

                else if(!get_pwd1.equals(get_pwd2)){
                    System.out.println("get_pwd1: " +get_pwd1);
                    System.out.println("get_pwd2: " +get_pwd2);
                    send = false;
                    error_pwdD.setBounds(970, 340, 170, 15);
                    error_pwdD.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_pwdD.setText("*비밀번호가 일치하지 않습니다");
                    error_pwdD.setForeground(Color.RED);
                    error_pwdL.setVisible(false);
                    error_pwdD.setVisible(true);
                    panel_e1.add(error_pwdD);
                }

                else if(name_size == 0 || work_size == 0
                        || birth.getDate()==null
                        || (marry_n.getText()=="" && marry_y.getText()=="")
                        || (m.getText()=="" && fm.getText()=="")
                ){
                    send = false;
                    System.out.println("name: " +get_pwd1);
                    error_else.setBounds(710, 658, 130, 15);
                    error_else.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_else.setText("*정보를 다 입력해주세요");
                    error_else.setForeground(Color.RED);
                    error_id.setVisible(false);
                    error_pwdL.setVisible(false);
                    error_pwdD.setVisible(false);
                    error_else.setVisible(true);
                    panel_e1.add(error_else);
                }
                else send = true;

                String gen;
                if(m.isSelected()) gen = "남자";
                else gen = "여자";
                String ring;
                if(marry_y.isSelected()) ring = "재혼";
                else ring = "없음";

                if(send == true){
                    String[] list = birth.getDate().toString().split(" ");
                    String year = list[5];
                    String month = list[1];
                    if(month.equals("Jen")) month = "01";
                    else if(month.equals("Feb")) month = "02";
                    else if(month.equals("Mar")) month = "03";
                    else if(month.equals("Apr")) month = "04";
                    else if(month.equals("May")) month = "05";
                    else if(month.equals("Jun")) month = "06";
                    else if(month.equals("Jul")) month = "07";
                    else if(month.equals("Aug")) month = "08";
                    else if(month.equals("Sep")) month = "09";
                    else if(month.equals("Oct")) month = "10";
                    else if(month.equals("Nov")) month = "11";
                    else month = "12";
                    String day = list[2];
                    String birthday = year+"."+month+"."+day;
                    try {
                        userInfo =  my_sql.updateData(userInfo.id, pwd1.getText(), name.getText(),
                                birthday, work.getText(),ring, gen);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("[화면 이동] 마이페이지 수정 -> 마이페이지");
                    setVisible(false);
                    new Mypage(userInfo).setVisible(true);
                }
            }
            else if(((JButton)e.getSource()).getText().equals("취소")) {
                System.out.println("[화면 이동] 마이페이지 수정 -> 마이페이지");
                setVisible(false);
                new Mypage(userInfo).setVisible(true);

            }
        }
    }


    public Myedit(UserInfo info) { // 생성자
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        /*PANEL*/
        panel_e1.setBounds(0, 0, 1280, 832);
        panel_e1.setLayout(null);

        userInfo = info;

//        /*TEXT LABEL*/
        user_name = new JLabel(userInfo.name);
        user_name.setBounds(123, 287, 100, 40);
        user_name.setFont(new Font("Arial", Font.PLAIN, 32));
        panel_e1.add(user_name);

        user_birth = new JLabel(userInfo.birth);
        user_birth.setBounds(125, 335, 100, 20);
        user_birth.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_e1.add(user_birth);

        user_id = new JLabel(userInfo.id);
        user_id.setBounds(478, 205, 100, 20);
        user_id.setFont(new Font("Arial", Font.BOLD, 16));
        panel_e1.add(user_id);

        /*TEXT FIELD*/
        pwd1 = new JPasswordField(userInfo.password);
        pwd1.setBackground(Color.WHITE);
        pwd1.setBounds(477, 298, 286, 31);
        pwd1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_e1.add(pwd1);

        pwd2 = new JPasswordField(userInfo.password);
        pwd2.setBackground(Color.WHITE);
        pwd2.setBounds(829, 298, 286, 31);
        pwd2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_e1.add(pwd2);

        name = new JTextField(userInfo.name);
        name.setBackground(Color.WHITE);
        name.setBounds(478, 435, 228, 31);
        name.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_e1.add(name);

        work = new JTextField(userInfo.work);
        work.setBackground(Color.WHITE);
        work.setBounds(774, 435, 341, 31);
        work.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_e1.add(work);

        birth.setBounds(477, 537, 230, 34);
        birth.setBackground(Color.WHITE);
        panel_e1.add(birth);

        /*MARRY 라디오 버튼*/
        ButtonGroup marryGroup = new ButtonGroup();
        marryGroup.add(marry_n);
        marry_n.setBounds(750, 542, 60, 20);
        panel_e1.add(marry_n);
        marryGroup.add(marry_y);
        marry_y.setBounds(810, 542, 60, 20);
        panel_e1.add(marry_y);
        marry_n.setBackground(Color.WHITE);
        marry_y.setBackground(Color.WHITE);
        marry_n.setSize(60,20);
        marry_y.setSize(60,20);
        marry_n.setFont(new Font("Intel", Font.PLAIN, 16));
        marry_y.setFont(new Font("Intel", Font.PLAIN, 16));

        /*GENDER 라디오 버튼*/
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(m);
        m.setBounds(940, 542, 60, 20);
        panel_e1.add(m);
        genderGroup.add(fm);
        fm.setBounds(1000, 542, 60, 20);
        panel_e1.add(fm);
        m.setBackground(Color.WHITE);
        fm.setBackground(Color.WHITE);
        m.setSize(60,20);
        fm.setSize(60,20);
        m.setFont(new Font("Intel", Font.PLAIN, 16));
        fm.setFont(new Font("Intel", Font.PLAIN, 16));

        /*BUTTON*/
        JButton btn_edit_done = new JButton("완료"); // 회원가입 버튼
        btn_edit_done.addActionListener(panel_e1);
        btn_edit_done.setBounds(620,694,90,30);
        btn_edit_done.setForeground(Color.WHITE);
        btn_edit_done.setContentAreaFilled(false);
        btn_edit_done.setFocusPainted(false);
        btn_edit_done.setBorderPainted(false);
        btn_edit_done.setFont(new Font("Inter", Font.PLAIN, 24));
        panel_e1.add(btn_edit_done);

        JButton btn_cancel = new JButton("취소"); // 회원가입 버튼
        btn_cancel.addActionListener(panel_e1);
        btn_cancel.setBounds(803,694,90,30);
        btn_cancel.setForeground(Color.DARK_GRAY);
        btn_cancel.setContentAreaFilled(false);
        btn_cancel.setFocusPainted(false);
        btn_cancel.setBorderPainted(false);
        btn_cancel.setFont(new Font("Inter", Font.PLAIN, 24));
        panel_e1.add(btn_cancel);

        add(panel_e1); //add 나중에
        setVisible(true);
    }
}

