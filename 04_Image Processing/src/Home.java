//이미지별로 흑백 전환 값 다르게!
//⭐️mask y축 적용
//⭐️mask 배경 합성??

/*
- 흑백 ^^
- edge 추출 ^^
- 그림 합성
- 돋보기
- contrast 조정
- 밝기 ^^
- smoothing ^^
- mask ^
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;


public class Home extends JFrame {
    JPanel panel1 = new JPanel();
    //JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();

    String back_path = "/Users/imagine/Downloads/흰색 배경.png";
     // 고구마 배경 이미지
    BufferedImage back_image;


    BufferedImage Bimage1;
    BufferedImage Bimage2;
    BufferedImage Bimage3;
    float[] data;
    float[] clr=new float[3];
    int[][] pixel;
    int x1 = -1;
    int y1 = -1;
    int[][] xP;
    int[][] yP;
    Image image1;
    Image newImage1;
    Image image2;
    Image newImage2;

    JLabel orgLabel = new JLabel();
    JLabel transLabel = new JLabel();

    ImageIcon icon1;
    ImageIcon icon2;
    ImageIcon orgIcon;
    ImageIcon transIcon;

    Kernel kernel;
    BufferedImageOp convolve;
    int trim = 30;

    button button_action = new button();

    int btn_var = 0;

    Panel2 panel2 = new Panel2();

    {
        try {
            Bimage1 = ImageIO.read(new File(FileChoose.path));
            Bimage2 = ImageIO.read(new File(FileChoose.path));
            Bimage3 = ImageIO.read(new File(FileChoose.path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Panel2 extends JPanel implements ActionListener {
        public void paintComponent(Graphics g) {
            File back1 = new File(back_path);
            try {
                back_image = ImageIO.read(back1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(back_image, 0,0,360,360, this);
        }

        public void actionPerformed(ActionEvent e) {
//            if (((JButton) e.getSource()).getText().equals("고구마")) {
//                System.out.println("[버튼] 고구마");
//                back_path = "/Users/imagine/Downloads/고구마배경.png";
//                panel2.repaint();
//            }
//            else if (((JButton) e.getSource()).getText().equals("길 가")) {
//                System.out.println("[버튼] 길 가");
//                back_path = "/Users/imagine/Downloads/길가 배경.png";
//                panel2.repaint();
//            }
//            else if (((JButton) e.getSource()).getText().equals("흰색")) {
//                System.out.println("[버튼] 흰색");
//                back_path = "/Users/imagine/Downloads/흰색 배경.png";
//                panel2.repaint();
//            }
        }
    }


    public Home(){
        pixel = new int[Bimage1.getHeight()][Bimage1.getWidth()];
        yP = new int[2][Bimage1.getWidth()];
        xP = new int[Bimage1.getHeight()][2];


        setSize(840, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.YELLOW);
        setLayout(null);

        /*PANEL*/
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);
//        panel1.setBackground(Color.GRAY);
//        panel2.setBackground(Color.ORANGE);
//        panel3.setBackground(Color.ORANGE);
        panel1.setBounds(30,30,360,360);
        panel2.setBounds(450,30,360,360);
        panel3.setBounds(30,410,780,110);

        /*LABEL*/
        icon1 = new ImageIcon(Bimage1);
        image1 = icon1.getImage();
        newImage1 = image1.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        orgIcon = new ImageIcon(newImage1);
        orgLabel.setIcon(orgIcon);
        orgLabel.setBounds(0,0,360,360);
        panel1.add(orgLabel);

        //변경된 이미지 출력
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
        transLabel.setBounds(0,0,360,360);
        panel2.add(transLabel);

        /*BUTTON*/
        JButton btn_black = new JButton("흑백");
        btn_black.addActionListener(button_action);
        btn_black.setBounds(0,0,100,40);
        panel3.add(btn_black);

        JButton btn_blr = new JButton("흐리게");
        btn_blr.addActionListener(button_action);
        btn_blr.setBounds(110,0,100,40);
        panel3.add(btn_blr);

        JButton btn_shrp = new JButton("뚜렷");
        btn_shrp.addActionListener(button_action);
        btn_shrp.setBounds(220,0,100,40);
        panel3.add(btn_shrp);

        JButton btn_edge = new JButton("테두리");
        btn_edge.addActionListener(button_action);
        btn_edge.setBounds(330,0,100,40);
        panel3.add(btn_edge);

        JButton btn_whiten = new JButton("반전");
        btn_whiten.addActionListener(button_action);
        btn_whiten.setBounds(440,0,100,40);
        panel3.add(btn_whiten);

        JButton btn_brgt_p = new JButton("밝기(+)");
        btn_brgt_p.addActionListener(button_action);
        btn_brgt_p.setBounds(550,0,60,40);
        panel3.add(btn_brgt_p);

        JButton btn_brgt_m = new JButton("밝기(-)");
        btn_brgt_m.addActionListener(button_action);
        btn_brgt_m.setBounds(610,0,60,40);
        panel3.add(btn_brgt_m);

        JButton btn_mask = new JButton("누끼");
        btn_mask.addActionListener(button_action);
        btn_mask.setBounds(680,0,100,40);
        panel3.add(btn_mask);

        JButton btn_back1 = new JButton("고구마");
        btn_back1.addActionListener(button_action);
        btn_back1.setBounds(0,45,100,40);
        panel3.add(btn_back1);

        JButton btn_back2 = new JButton("길 가");
        btn_back2.addActionListener(button_action);
        btn_back2.setBounds(110,45,100,40);
        panel3.add(btn_back2);

        JButton btn_back0 = new JButton("흰색");
        btn_back0.addActionListener(button_action);
        btn_back0.setBounds(220,45,100,40);
        panel3.add(btn_back0);

        JButton btn_reset = new JButton("파일 불러오기");
        btn_reset.addActionListener(button_action);
        btn_reset.setBounds(330,45,100,40);
        panel3.add(btn_reset);

        JButton btn_save = new JButton("저장");
        btn_save.addActionListener(button_action);
        btn_save.setBounds(550,45,120,40);
        panel3.add(btn_save);

        JButton btn_org = new JButton("원본");
        btn_org.addActionListener(button_action);
        btn_org.setBounds(680,45,100,40);
        panel3.add(btn_org);

        add(panel1);
        add(panel2);
        add(panel3);
        setVisible(true);
    }

    /*흑백*/
    public void black(){
        if(btn_var == 1){
            for(int y=0; y<Bimage2.getHeight(); y++){
                for(int x=0; x<Bimage2.getWidth(); x++){
                    Color color = new Color(Bimage2.getRGB(x,y));
                    int Y = (int) (0.2126 * color.getRed()
                            + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                    Bimage2.setRGB(x, y, new Color(Y,Y,Y).getRGB());
                }
            }
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*뚜렷하게*/
    public void sharp(){
        if(btn_var == 3){
            data = new float[]{ 0, -1, 0, -1, 5, -1, 0, -1, 0};
            kernel = new Kernel(3, 3, data);
            convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            Bimage2  = convolve.filter(Bimage2, null);
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*테두리 따기*/
    public void edge(){
        if (btn_var == 2){
            /*흑백*/
            for(int y=0; y<Bimage2.getHeight(); y++){
                for(int x=0; x<Bimage2.getWidth(); x++){
                    Color color = new Color(Bimage2.getRGB(x,y));
                    int Y = (int) (0.2126 * color.getRed()
                            + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                    Bimage2.setRGB(x, y, new Color(Y,Y,Y).getRGB());
                }
            }
            /*블러*/
            data = new float[]{ 1/256f, 4/256f, 6/256f, 4/256f, 1/256f,
                    4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                    6/256f, 24/256f, 36/256f, 24/256f, 6/256f,
                    4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                    1/256f, 4/256f, 6/256f, 4/256f, 1/256f };
            kernel = new Kernel(5, 5, data);
            convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            Bimage2  = convolve.filter(Bimage2, null);
            /*뚜렷*/
            data = new float[]{ 0, -1, 0, -1, 5, -1, 0, -1, 0};
            kernel = new Kernel(3, 3, data);
            convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            Bimage2  = convolve.filter(Bimage2, null);
            /*테두리 따기*/
            data = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -24, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1 };
            //data = new float[]{-1,-1,-1,-1,8,-1,-1,-1,-1};
            kernel = new Kernel(5, 5, data);
            convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            Bimage2  = convolve.filter(Bimage2, null);
            /*흑백반전*/
//            for(int y=0; y<Bimage2.getHeight(); y++){
//                for(int x=0; x<Bimage2.getWidth(); x++){
//                    Color color = new Color(Bimage2.getRGB(x,y));
//                    int Y1 = (int) (255 - color.getRed());
//                    int Y2 = (int) (255 - color.getGreen());
//                    int Y3 = (int) (255 - color.getBlue());
//                    if(Y1 > trim && Y2>trim && Y3>trim) {
//                        Y1 = (int) 255;
//                        Y2 = (int) 255;
//                        Y3 = (int) 255;
//                    }
//                    else {
//                        Y1 = (int) 0;
//                        Y2 = (int) 0;
//                        Y3 = (int) 0;
//                    }
//                    Bimage2.setRGB(x, y, new Color(Y1,Y2,Y3).getRGB());
//                }
//            }
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*흐리게*/
    public void blr(){
        if (btn_var == 4){
            /*흐리게_슬라이더*/
            data = new float[]{ 1/256f, 4/256f, 6/256f, 4/256f, 1/256f,
                    4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                    6/256f, 24/256f, 36/256f, 24/256f, 6/256f,
                    4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                    1/256f, 4/256f, 6/256f, 4/256f, 1/256f };
            Kernel kernel = new Kernel(5, 5, data);
            BufferedImageOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            Bimage2  = convolve.filter(Bimage2, null);
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*흑백전환*/
    public void whiten(){
        if(btn_var == 5){
            for(int y=0; y<Bimage2.getHeight(); y++){
                for(int x=0; x<Bimage2.getWidth(); x++){
                    Color color = new Color(Bimage2.getRGB(x,y));
                    int Y1 = (int) (255 - color.getRed());
                    int Y2 = (int) (255 - color.getGreen());
                    int Y3 = (int) (255 - color.getBlue());
                    /*trim*/
                    if(Y1 > trim && Y2>trim && Y3>trim) {
                        Y1 = (int) 255;
                        Y2 = (int) 255;
                        Y3 = (int) 255;
                    }
                    else {
                        Y1 = (int) 0;
                        Y2 = (int) 0;
                        Y3 = (int) 0;
                    }
                    Bimage2.setRGB(x, y, new Color(Y1,Y2,Y3).getRGB());
                }
            }
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*밝기(+)*/
    public void brgten(){
        for(int y=0; y<Bimage2.getHeight(); y++){
            for(int x=0; x<Bimage2.getWidth(); x++){
                Color color = new Color(Bimage2.getRGB(x,y));

                int Y1 = (int) color.getRed();
                int Y2 = (int) color.getGreen();
                int Y3 = (int) color.getBlue();

                Color.RGBtoHSB(Y1, Y2, Y3, clr);
                if(btn_var == 6){
                    if(clr[2] < 0.95){
                        clr[2] += 0.05;
                    }
                }
                else if(btn_var == 7){
                    if(clr[2] > 0.05){
                        clr[2] -= 0.05;
                    }
                }
                int newclr = Color.HSBtoRGB(clr[0], clr[1], clr[2]);
                
                Bimage2.setRGB(x, y, newclr);
            }
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*누끼*/
    public void mask(){
        for(int y=0; y<Bimage2.getHeight(); y++){
            for(int x=0; x<Bimage2.getWidth(); x++){
                Color color = new Color(Bimage2.getRGB(x,y));
                int Y = (int) (0.2126 * color.getRed()
                        + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                Bimage2.setRGB(x, y, new Color(Y,Y,Y).getRGB());
            }
        }
        /*블러*/
        data = new float[]{ 1/256f, 4/256f, 6/256f, 4/256f, 1/256f,
                4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                6/256f, 24/256f, 36/256f, 24/256f, 6/256f,
                4/256f, 16/256f, 24/256f, 16/256f, 4/256f,
                1/256f, 4/256f, 6/256f, 4/256f, 1/256f };
        kernel = new Kernel(5, 5, data);
        convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        Bimage2  = convolve.filter(Bimage2, null);
        /*뚜렷*/
        data = new float[]{ 0, -1, 0, -1, 5, -1, 0, -1, 0};
        kernel = new Kernel(3, 3, data);
        convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        Bimage2  = convolve.filter(Bimage2, null);
        /*테두리 따기*/
        data = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -24, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1 };
        //data = new float[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
        kernel = new Kernel(5, 5, data);
        convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        Bimage2  = convolve.filter(Bimage2, null);
        /*흑백반전*/
        for(int y=0; y<Bimage2.getHeight(); y++){
            for(int x=0; x<Bimage2.getWidth(); x++){
                Color color = new Color(Bimage2.getRGB(x,y));
                int Y1 = (int) (255 - color.getRed());
                int Y2 = (int) (255 - color.getGreen());
                int Y3 = (int) (255 - color.getBlue());
                if(Y1 > trim && Y2>trim && Y3>trim) {
                    Y1 = (int) 255;
                    Y2 = (int) 255;
                    Y3 = (int) 255;
                }
                else {
                    Y1 = (int) 0;
                    Y2 = (int) 0;
                    Y3 = (int) 0;
                }
                Bimage2.setRGB(x, y, new Color(Y1,Y2,Y3).getRGB());
            }
        }

        //x 기준 테두리 따기
        for(int y=2; y<Bimage2.getHeight()-4; y++){
            x1= -1;
            for(int x=2; x<Bimage2.getWidth()-4; x++){
                Color color = new Color(Bimage2.getRGB(x,y));
                //x 색 == 255 -> 첫번째 값 저장 x1 = [y][x]
                //x 색 == 255 마지막 값 저장 x2 = [y][x]
                if(color.getRed() == 0 && x1==-1){ // 0 = 검은색
                    xP[y][0] = x;
                    x1 = 1;
                }
                if(color.getRed() == 0){
                    xP[y][1] = x;
                }
            }
        }
        //y기준 테두리 따기
        for(int x=2; x<Bimage2.getWidth()-4; x++){
            y1= -1;
            for(int y=2; y<Bimage2.getHeight()-4; y++){
                Color color = new Color(Bimage2.getRGB(x,y));
                //y색 == 255 -> 첫번째 값 저장
                if(color.getRed() == 0 && y1==-1){ // 0 = 검은색 + 첫번째 검은색 위치 저장
                    yP[0][x] = y;
                    y1 = 1;
                }
                if(color.getRed() == 0){
                    yP[1][x] = y;
                }
            }
        }



        for(int y=0; y<Bimage2.getHeight(); y++){
            for(int x=0; x<Bimage2.getWidth(); x++) {
                if(xP[y][0] != 0 && xP[y][1] != 0){
                    if(x==xP[y][0] || x==xP[y][1]){
                        pixel[y][x] = 11; //검은색
                    }
                    else pixel[y][x] = 0; //흰색
                }
                else pixel[y][x] = 0; //흰색

                //System.out.print(pixel[y][x] + " ");
            }
            //System.out.print("\n");
        }

        for(int x=0; x<Bimage2.getWidth(); x++){
            for(int y=0; y<Bimage2.getHeight(); y++) {
                if(yP[0][x] != 0 && yP[1][x] != 0){
                    if(y==yP[0][x] || y==yP[1][x]){
                        pixel[y][x] = 11; //검은색
                    }
                    else pixel[y][x] = 0; //흰색
                }
                else pixel[y][x] = 0; //흰색

                System.out.print(pixel[y][x] + " ");
            }
            System.out.print("\n");
        }


        for(int y=0; y<Bimage3.getHeight(); y++){
            for(int x=0; x<Bimage3.getWidth(); x++) {
                if (x < xP[y][0] || x > xP[y][1] ){
                    Bimage3.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                }
                if (xP[y][0] == 0 || xP[y][1] ==0){
                    Bimage3.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                }
            }
        }

        for(int x=0; x<Bimage3.getWidth(); x++){
            for(int y=0; y<Bimage3.getHeight(); y++) {
                if (y < yP[0][x] || y > yP[1][x] ){
                    Bimage3.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                }
            }
        }

        icon2 = new ImageIcon(Bimage3); //누끼 출력
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    /*저장*/
    public void save(){
        File outputImage = new File("//Users/imagine/Desktop/save1.png"); //테두리
        try {
            ImageIO.write(Bimage2, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File outputImage2 = new File("//Users/imagine/Desktop/save2.png"); //누끼
        try {
            ImageIO.write(Bimage3, "png", outputImage2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*원본*/
    public void org(){
        if (btn_var == 0){
            try {
                Bimage2 = ImageIO.read(new File(FileChoose.path)); //원본 이미지로 복구
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        icon2 = new ImageIcon(Bimage2);
        image2 = icon2.getImage();
        newImage2 = image2.getScaledInstance(360,360,Image.SCALE_DEFAULT);
        transIcon = new ImageIcon(newImage2);
        transLabel.setIcon(transIcon);
    }

    class button implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getText().equals("흑백")){
                System.out.println("[버튼] 흑백");
                btn_var = 1;
                black();
            }
            else if(((JButton)e.getSource()).getText().equals("테두리")){
                btn_var = 2;
                System.out.println("[버튼] 테두리");
                edge();
            }
            else if(((JButton)e.getSource()).getText().equals("뚜렷")){
                btn_var = 3;
                System.out.println("[버튼] 뚜렷");
                sharp();
            }
            else if(((JButton)e.getSource()).getText().equals("흐리게")){
                btn_var = 4;
                System.out.println("[버튼] 흐리게");
                blr();
            }
            else if(((JButton)e.getSource()).getText().equals("반전")){
                btn_var = 5;
                System.out.println("[버튼] 반전");
                whiten();
            }
            else if(((JButton)e.getSource()).getText().equals("밝기(+)")){
                btn_var = 6;
                System.out.println("[버튼] 밝기(+)");
                brgten();
            }
            else if(((JButton)e.getSource()).getText().equals("밝기(-)")){
                btn_var = 7;
                System.out.println("[버튼] 밝기(-)");
                brgten();
            }
            else if(((JButton)e.getSource()).getText().equals("고구마")){
                System.out.println("[버튼] 고구마");
                back_path = "/Users/imagine/Downloads/고구마배경.png";
                panel2.repaint();
            }

            else if(((JButton)e.getSource()).getText().equals("길 가")){
                System.out.println("[버튼] 길 가");
                back_path = "/Users/imagine/Downloads/길가 배경.png";
                panel2.repaint();
            }

            else if(((JButton)e.getSource()).getText().equals("흰색")){
                System.out.println("[버튼] 흰색");
                back_path = "/Users/imagine/Downloads/흰색 배경.png";
                panel2.repaint();
            }

            else if(((JButton)e.getSource()).getText().equals("누끼")){
                btn_var = 8;
                System.out.println("[버튼] 누끼");
                mask();
            }
            else if(((JButton)e.getSource()).getText().equals("파일 불러오기")){
                System.out.println("[버튼] reset");
                setVisible(false);
                FileChoose fileChoose = new FileChoose();
            }
            else if(((JButton)e.getSource()).getText().equals("저장")){
                save();
                System.out.println("[버튼] 저장");
            }
            else if(((JButton)e.getSource()).getText().equals("원본")){
                btn_var = 0;
                System.out.println("[버튼] 원본");
                org();
            }
        }
    }
}
