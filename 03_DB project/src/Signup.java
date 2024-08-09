import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import com.toedter.calendar.JDateChooser;


public class Signup extends JFrame {

    Login_panel panel_s1 = new Login_panel(); //패널 생성
    Sql my_sql = new Sql();

    File file = new File("/Users/imagine/Downloads/Sign Up-2.png");
    BufferedImage image;

    JTextField id;
    JPasswordField pwd1;
    JPasswordField pwd2;
    JTextField name;
    JTextField work;
    JDateChooser birth = new JDateChooser();
    JRadioButton marry_n = new JRadioButton("없음");
    JRadioButton marry_y = new JRadioButton("재혼");
    JRadioButton m = new JRadioButton("남자");
    JRadioButton fm = new JRadioButton("여자");
    JCheckBox agree = new JCheckBox();

    JLabel error_id = new JLabel();
    JLabel error_pwdD = new JLabel();
    JLabel error_pwdL = new JLabel();
    JLabel error_else = new JLabel();
    //JLabel error = new JLabel();
    boolean send = true;

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
            if(((JButton)e.getSource()).getText().equals("완료")){
                //
                //id 20자 이하 , 비번 4~8자리 숫자, 비번 중복확인,
                /*아이디 중복 확인*/
                try {
                    if(my_sql.selectIdData(id.getText()) != 0){
                        send = false;
                        error_id.setBounds(505, 260, 131, 15);
                        error_id.setFont(new Font("Inter", Font.PLAIN, 12));
                        error_id.setText("*아이디가 중복됩니다");
                        error_id.setForeground(Color.RED);
                        error_id.setVisible(true);
                        panel_s1.add(error_id);
                    }
                    else error_id.setVisible(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                /*비번 6자리, 동일 확인*/
                String get_pwd1 = new String(pwd1.getPassword());
                String get_pwd2 = new String(pwd2.getPassword());
                String get_name = name.getText();
                String get_work = work.getText();
                int pwd_size = get_pwd1.length();
                int name_size = get_name.length();
                int work_size = get_work.length();

                if(pwd_size != 6){
                    send = false;
                    error_pwdL.setBounds(465, 360, 170, 15);
                    error_pwdL.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_pwdL.setText("*숫자 6자리를 입력해주세요");
                    error_pwdL.setForeground(Color.RED);
                    error_pwdL.setVisible(true);
                    panel_s1.add(error_pwdL);
                }

                else if(!get_pwd1.equals(get_pwd2)){
                    send = false;
                    error_pwdD.setBounds(815, 360, 170, 15);
                    error_pwdD.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_pwdD.setText("*비밀번호가 일치하지 않습니다");
                    error_pwdD.setForeground(Color.RED);
                    error_pwdL.setVisible(false);
                    error_pwdD.setVisible(true);
                    panel_s1.add(error_pwdD);
                }

                else if(name_size == 0 || work_size == 0
                        || birth.getDate()==null
                        || (marry_n.getText()=="" && marry_y.getText()=="")
                        || (m.getText()=="" && fm.getText()=="")
                ){
                    send = false;
                    error_else.setBounds(577, 665, 170, 15);
                    error_else.setFont(new Font("Inter", Font.PLAIN, 12));
                    error_else.setText("*정보를 다 입력해주세요");
                    error_else.setForeground(Color.RED);
                    error_id.setVisible(false);
                    error_pwdL.setVisible(false);
                    error_pwdD.setVisible(false);
                    error_else.setVisible(true);
                    panel_s1.add(error_else);
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
                    my_sql.insertData(id.getText(), pwd1.getText(), name.getText(),
                            birthday, work.getText(),ring,
                            gen);
                    System.out.println("[화면 이동] 회원가입 -> 로그인");
                    setVisible(false);
                    new Login().setVisible(true);
                }
            }
        }
    }


    public Signup() { // 생성자
        setSize(1280, 832);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

//        JDateChooser dateChooser = new JDateChooser();
        birth.setBounds(320, 563, 170, 35);
        //dateChooser.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(birth);

//
//        UtilDateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl(model);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
//
////        new JDatePickerEx();
////
////        UtilDateModel model = new UtilDateModel();
////        JDatePanelImpl datePanel = new JDatePanelImpl(model);
////        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
////        add(datePicker);
//
//        panel_s1.add(datePicker);

        /*PANEL*/
        panel_s1.setBounds(0, 0, 1280, 832);
        panel_s1.setLayout(null);

        /*Text Fild*/
        id = new JTextField();
        id.setBackground(new Color(235, 235, 235));
        id.setBounds(321, 222, 288, 32);
        id.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(id);

        pwd1 = new JPasswordField();
        pwd1.setBackground(new Color(235, 235, 235));
        pwd1.setBounds(321, 321, 288, 32);
        pwd1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(pwd1);

        pwd2 = new JPasswordField();
        pwd2.setBackground(new Color(235, 235, 235));
        pwd2.setBounds(672, 321, 288, 32);
        pwd2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(pwd2);
        //이름 text field
        name = new JTextField();
        name.setBackground(new Color(235, 235, 235));
        name.setBounds(321, 461, 230, 32);
        name.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(name);

        work = new JTextField();
        work.setBackground(new Color(235, 235, 235));
        work.setBounds(619, 461, 341, 32);
        work.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_s1.add(work);


        /*MARRY 라디오 버튼*/
        ButtonGroup marryGroup = new ButtonGroup();
        marryGroup.add(marry_n);
        marry_n.setBounds(570, 567, 60, 20);
        panel_s1.add(marry_n);
        marryGroup.add(marry_y);
        marry_y.setBounds(630, 567, 60, 20);
        panel_s1.add(marry_y);
        marry_n.setBackground(Color.WHITE);
        marry_y.setBackground(Color.WHITE);
        marry_n.setSize(60,20);
        marry_y.setSize(60,20);
        marry_n.setFont(new Font("Intel", Font.PLAIN, 16));
        marry_y.setFont(new Font("Intel", Font.PLAIN, 16));

        JLabel marriedLabel = new JLabel("결혼경험 유무");
        marriedLabel.setFont(new Font("Intel", Font.PLAIN, 16));
        marriedLabel.setBounds(580, 533, 200, 20);
        panel_s1.add(marriedLabel);

        /*GENDER 라디오 버튼*/
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(m);
        m.setBounds(800, 567, 60, 20);
        panel_s1.add(m);
        genderGroup.add(fm);
        fm.setBounds(860, 567, 60, 20);
        panel_s1.add(fm);
        m.setBackground(Color.WHITE);
        fm.setBackground(Color.WHITE);
        m.setSize(60,20);
        fm.setSize(60,20);
        m.setFont(new Font("Intel", Font.PLAIN, 16));
        fm.setFont(new Font("Intel", Font.PLAIN, 16));

        JLabel genderLabel = new JLabel("성별");
        genderLabel.setFont(new Font("Intel", Font.PLAIN, 16));
        genderLabel.setBounds(810, 533, 60, 20);
        panel_s1.add(genderLabel);

        /*체크박스*/
        agree.setBackground(Color.WHITE);
        agree.setBounds(310, 633, 25, 20);
        panel_s1.add(agree);
        JLabel agreeLabel = new JLabel("개인정보 수집 및 이용에 동의합니다.");
        agreeLabel.setFont(new Font("Intel", Font.PLAIN, 16));
        agreeLabel.setBounds(335, 633, 300, 20);
        panel_s1.add(agreeLabel);


        /*BUTTON*/
        JButton btn_login = new JButton("완료"); // 회원가입 버튼
        btn_login.addActionListener(panel_s1);
        btn_login.setBounds(519,687,242,40);
        btn_login.setForeground(Color.WHITE);
        btn_login.setContentAreaFilled(false);
        btn_login.setFocusPainted(false);
        btn_login.setBorderPainted(false);
        btn_login.setFont(new Font("Inter", Font.PLAIN, 24));
        panel_s1.add(btn_login);

        add(panel_s1); //add 나중에
        setVisible(true);
    }
}
