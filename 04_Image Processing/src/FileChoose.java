import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileChoose extends JFrame {
    FC_panel panel = new FC_panel();
    BufferedImage image;

    static String path = "";
    File file = new File(path);

    JFileChooser fileComponent = new JFileChooser();
    JButton btnOpen = new JButton("열기");
    JButton btnSave = new JButton("저장");
    JLabel labelOpen = new JLabel(" ");
    JLabel labelSave = new JLabel(" ");

    class FC_panel extends JPanel implements ActionListener {
//        public void paintComponent(Graphics g) {
//            try {
//                image = ImageIO.read(file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            super.paintComponents(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.drawImage(image, 20, 100, 200, 150, this);
//        }

        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == btnOpen) {
                if (fileComponent.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    labelOpen.setText("경로 : \n" + fileComponent.getSelectedFile().toString());
                    path = fileComponent.getSelectedFile().toString();
                    System.out.println("path: " + path);
                    setVisible(false);
                    new Home().setVisible(true);
                }
            }
//파일 이미지 저장
//        else if (arg0.getSource() == btnSave) {
//            if (fileComponent.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
//                // 파일 저장을 원하면 아래에 파일 저장 로직을 구현하면 됨.
//                labelSave.setText("저장 파일 경로 : " + fileComponent.getSelectedFile().toString());
//            }
//        }
        }
    }

    /*생성자*/
    public FileChoose() {
        setSize(500, 300);

        panel.setBounds(10,10,480,250);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        getContentPane().setLayout(null);

        /*BUTTON*/
        btnOpen.setBounds(180,100,100,40); // [열기]
        labelOpen.setBounds(20,50,460,30); // [열기]
        panel.add(btnOpen);
        panel.add(labelOpen);
        btnOpen.addActionListener(panel);

//        btnSave.setBounds(100,80,80,30); // [저장]
//        btnSave.addActionListener(this);
//        panel.add(btnSave);
//        panel.add(labelSave);

        fileComponent.setFileFilter(new FileNameExtensionFilter("jpg","jpeg","png")); // 확장자
        fileComponent.setMultiSelectionEnabled(false);

        add(panel);
        panel.setVisible(true);
        setVisible(true);
    }
}