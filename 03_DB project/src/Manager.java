import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Manager extends JFrame {

    Mypage_panel panel_m1 = new Mypage_panel(); //패널 생성
    //UserInfo userInfo;
    UserInfo userInfo;
    Sql my_sql = new Sql();
    JLabel error_id = new JLabel();
    JTextField id;
    boolean send = true;

    File file = new File("/Users/imagine/Downloads/admin_edit-3.png");
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
            if(((JButton)e.getSource()).getText().equals("검색")){
                try {
                    if(my_sql.selectIdData(id.getText()) == 0){
                        send = false;
                        error_id.setBounds(875, 265, 170, 15);
                        error_id.setFont(new Font("Inter", Font.PLAIN, 12));
                        error_id.setText("*존재하지 않는 아이디입니다");
                        error_id.setForeground(Color.RED);
                        error_id.setVisible(true);
                        panel_m1.add(error_id);
                    }
                    else send = true;
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                if(send == true){
                    try {
                        userInfo = my_sql.selectData(id.getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("[화면 이동] 관리자 메인 -> 수정");
                    setVisible(false);
                    new Myedit(userInfo).setVisible(true);
                }
            }

            else if(((JButton)e.getSource()).getText().equals("회원 수 통계")){
                System.out.println("[화면 이동] 관리자 메인 -> 통계");
                setVisible(false);
                new Math().setVisible(true);
            }
            else if(((JButton)e.getSource()).getText().equals("로그아웃")){
                System.out.println("[화면 이동] 마이페이지 -> 로그인");
                setVisible(false);
                new Login().setVisible(true);
            }
        }
    }


    public Manager(UserInfo info) { // 생성자
        my_sql.allData();
        setSize(1280, 832);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        userInfo = info;

        /*PANEL*/
        panel_m1.setBounds(0, 0, 1280, 832);
        panel_m1.setLayout(null);

        /*LABEL*/
        JLabel manager = new JLabel("관리자");
        manager.setBounds(123, 287, 100, 40);
        manager.setFont(new Font("Arial", Font.PLAIN, 32));
        panel_m1.add(manager);

        JLabel search = new JLabel("수정할 회원 아이디 : ");
        search.setBounds(645, 220, 250, 40);
        search.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_m1.add(search);

        /*TABEL*/
        String header[] = {"이름", "아이디", "비밀번호", "생년월일", "직업", "결혼경험 여부", "성별"};
        String contents[][] = new String[Sql.users.size()][7];

        int size = Sql.users.size();

        DefaultTableModel model = new DefaultTableModel(contents, header);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(441, 295, 669, 436);

        DefaultTableModel m = (DefaultTableModel) table.getModel();

        for (int i=0; i<size; i++){
            System.out.println("너의 이름은: " + Sql.users.get(i).name);
            m.insertRow(i, new Object[]{Sql.users.get(i).name , Sql.users.get(i).id, Sql.users.get(i).password,
                    Sql.users.get(i).birth, Sql.users.get(i).work, Sql.users.get(i).married, Sql.users.get(i).gender});
        }

        //table.updateUI();

        panel_m1.add(scrollPane);





        /*BUTTON*/
        JButton btn_my_edit = new JButton("검색"); // 회원가입 버튼
        btn_my_edit.setForeground(Color.WHITE);
        btn_my_edit.addActionListener(panel_m1);
        btn_my_edit.setBounds(1025,231,100,20);
        btn_my_edit.setContentAreaFilled(false);
        btn_my_edit.setFocusPainted(false);
        btn_my_edit.setBorderPainted(false);
        btn_my_edit.setFont(new Font("Inter", Font.PLAIN, 17));
        panel_m1.add(btn_my_edit);

        /*BUTTON*/
        JButton btn_math = new JButton("회원 수 통계"); // 회원가입 버튼
        btn_math.addActionListener(panel_m1);
        btn_math.setBounds(25,445,170,30);
        btn_math.setContentAreaFilled(false);
        btn_math.setFocusPainted(false);
        btn_math.setBorderPainted(false);
        btn_math.setFont(new Font("Inter", Font.PLAIN, 20));
        panel_m1.add(btn_math);

        JButton btn_logout = new JButton("로그아웃"); // 회원가입 버튼
        btn_logout.addActionListener(panel_m1);
        btn_logout.setBounds(120,743,100,20);
        btn_logout.setContentAreaFilled(false);
        btn_logout.setFocusPainted(false);
        btn_logout.setBorderPainted(false);
        btn_logout.setFont(new Font("Inter", Font.PLAIN, 16));
        panel_m1.add(btn_logout);

        /*TEXT FIELD*/
        id = new JTextField();
        id.setBackground(Color.WHITE);
        id.setBounds(791, 224, 229, 33);
        id.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel_m1.add(id);

        add(panel_m1); //add 나중에
        setVisible(true);
    }
}

