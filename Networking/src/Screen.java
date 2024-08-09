import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screen extends JFrame {

    Panel panel_sv = new Panel();
    JTextField message_sd = new JTextField();
    JLabel label1;
    JLabel label2;
    JLabel label_user;
    static String snd = "";
    static int cnt = 0;

    File file = new File("/Users/imagine/Downloads/채팅창-2.jpg");
    BufferedImage image;

    class Panel extends JPanel implements ActionListener {
        public void paintComponent(Graphics g) {
            try{
                image = ImageIO.read(file);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(image, 0, 0, 393, 705, this);
        }

        public void actionPerformed(ActionEvent e) {
            //if(((JButton)e.getSource()).getText().equals(" ")){ //전송 버튼 누를 때
                System.out.println("[버튼] 전송");
                //System.out.println("message_sd.getText(); " + message_sd.getText());
                send(message_sd.getText());
                message_sd.setText(null);
                System.out.println("sen : " + snd);
                Sender.exest(snd);
                textBox(snd);
            //}
        }
    }

    public Screen(){
        setSize(393, 705);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel_sv.setBounds(0, 0, 393, 705);
        panel_sv.setLayout(null);


        /*Text Field*/
        message_sd.setBackground(Color.WHITE);
        message_sd.setBounds(35, 610, 296, 32);
        message_sd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        message_sd.addActionListener(panel_sv); //엔터
        panel_sv.add(message_sd);

        /*BUTTON*/
        JButton btn_snd = new JButton(); // 전송 버튼
        btn_snd.addActionListener(panel_sv);
        btn_snd.setBounds(337,610,32,32);
        btn_snd.setContentAreaFilled(false);
        btn_snd.setFocusPainted(false);
        btn_snd.setBorderPainted(false);
        //btn_logout.setFont(new Font("Inter", Font.PLAIN, 16));
        panel_sv.add(btn_snd);

        //panel_sv.add(textBox(label));


        add(panel_sv);
        setVisible(true);
    }

    public void send(String s){
        snd = s;
    }

    public void textBox(String snd){
        label1 = new JLabel(snd);
        System.out.println("snd: " + snd);
        //label.setText(snd);
        //new JLabel(snd)
        label1.setBounds(19, 92+(cnt*30), 355,22);
        label1.setHorizontalAlignment(JLabel.RIGHT);
        label1.setFont(new Font("Arial", Font.PLAIN, 18));
        label1.setForeground(Color.BLUE);
        panel_sv.add(label1);
        panel_sv.repaint();
        cnt++;
    }

    public void fromBox(String frm){
        String name = frm;
        String[] split = name.split(":");
        String s1 = split[0];
        String s2 = split[1];

        label_user = new JLabel(s1);
        label_user.setBounds(167, 23, 70,30);
        label_user.setFont(new Font("Arial", Font.BOLD, 20));
        panel_sv.add(label_user);



        label2 = new JLabel(s2);
        System.out.println("frm:"+s2);
        //JLabel label2 = new JLabel(a);
        label2.setBounds(19, 92+(cnt*30), 355,22);
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        panel_sv.add(label2);
        panel_sv.repaint();
        cnt++;
    }

}


