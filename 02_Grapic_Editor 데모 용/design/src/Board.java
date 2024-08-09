import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.io.File;

//🩷🩷🩷
//drag & drop
//마우스 닿으면 지우기

public class Board extends JFrame implements MouseListener, MouseMotionListener{

    ArrayList<My_shape> bucket = new ArrayList<>();
    ArrayList<My_image> images = new ArrayList<>();

    //JFrame frame = new JFrame("Graphic Editor");
    JPanel panel_t1 = new JPanel();
    JPanel panel_t2 = new JPanel();
    JPanel panel_t3 = new JPanel();
    JPanel panel_t4 = new JPanel();
    JPanel panel_t5 = new JPanel();
    JPanel panel_brd;

    button1 button1_action = new button1();
    button2 button2_action = new button2();
    button3 button3_action = new button3();
    button4 button4_action = new button4();
    button5 button5_action = new button5();

    Shape pen;
    Point pen_start, pen_end;
    Point pen_fill;
    boolean pen_ing = false;
    boolean dash = false;
    Color clr = new Color(50,50,50);
    Stroke str = new BasicStroke(1);
    boolean filled = false;
    boolean moved = false;
    Shape pad;
    BasicStroke pad_stroke = new BasicStroke(10);
    boolean dlt = false;


    public class Canvas extends JPanel {
        public Canvas(){ //생성자~~~~
            setBounds(20,120,960,530);
            add(new JLabel("board:"));
            setBackground(Color.white);
        }


        public void paintComponent(Graphics g){
            super.paintComponent(g);



            //clr 지정
            if(pen_color==1){
                clr = new Color(223,28,28);
            }
            else if(pen_color==2){ //주황
                clr = new Color(251,125,9);
            }
            else if(pen_color==3){ //노랑
                clr = new Color(244,195,18);
            }
            else if(pen_color==4){ //초록
                clr = new Color(73,205,10);
            }
            else if(pen_color==5){ //파랑
                clr = new Color(4,113,212);
            }
            else if(pen_color==6){ //보라
                clr = new Color(167,78,236);
            }
            else if(pen_color==7){ //핑크
                clr = new Color(249,94,215);
            }
            else if(pen_color==8){ //검정
                clr = new Color(50,50,50);
            }

            //두께 지정
            if(pen_width==1){
                if(dash==true){
                    float[] dash=new float[]{10,5,5,5};
                    str = new BasicStroke(1,0,BasicStroke.JOIN_ROUND, 1, dash, 0);
                }
                else{
                    str = new BasicStroke(1);
                }
            }
            else if(pen_width==2){
                if(dash==true){
                    float[] dash=new float[]{10,5,5,5};
                    str = new BasicStroke(3,0,BasicStroke.JOIN_ROUND, 1, dash, 0);
                }
                else{
                    str = new BasicStroke(3);
                }
            }
            else if(pen_width==3){
                if(dash==true){
                    float[] dash=new float[]{10,5,5,5};
                    str = new BasicStroke(5,0,BasicStroke.JOIN_ROUND, 1, dash, 0);
                }
                else{
                    str = new BasicStroke(5);
                }
            }
            drawL(g, a, b, c, d);
        }
    }

    public void drawL(Graphics g, int a, int b, int c, int d){
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(image, dragX, dragY, 25, 25, panel_brd);

        /* line button */
        if(type == 1){
            Shape s = new Line2D.Double(a, b, c, d);
            My_shape my_shape = new My_shape();
            my_shape.sh = s;
            my_shape.color = clr; // 컬러 지정
            my_shape.stroke = str; // 선 두께 지정
            my_shape.padding = pad_stroke.createStrokedShape(s); // 테두리 padding
            g2d.setColor(clr); // 그림판에 보여지는 설정값
            g2d.setStroke(str);
                System.out.println(save);
            if(save == true){
                bucket.add(my_shape);
            }
            else {
                g.drawLine(a, b, c, d);
            }
        }
        /* rectangle button */
        else if(type == 2){
            Shape s = new Rectangle2D.Double(a, b, c-a, d-b);
            My_shape my_shape = new My_shape();
            my_shape.sh = s;
            my_shape.color = clr;
            my_shape.stroke = str;
            //my_shape.fill = filled;
            g2d.setColor(clr);
            g2d.setStroke(str);
            my_shape.padding = pad_stroke.createStrokedShape(s);
            //g2d.fill(filled);

            if(save == true){
                bucket.add(my_shape);
            } 
            else {
                g.drawRect(a, b, c-a, d-b);
            }
            fill_rect = false;   
        }
        /* circle button */
        else if(type == 3){
            Shape s = new Ellipse2D.Double(a, b, c-a, d-b);
            My_shape my_shape = new My_shape();
            my_shape.sh = s;
            my_shape.color = clr;
            my_shape.stroke = str;
            g2d.setColor(clr);
            g2d.setStroke(str);
            my_shape.padding = pad_stroke.createStrokedShape(s);
            if(save == true){
                bucket.add(my_shape);
            } 
            else {
                g.drawOval(a, b, c-a, d-b);
            }
        } 
        /* free-line button */
        else if(type == 4){ 
            My_shape my_shape = new My_shape();
            my_shape.sh = pen;
            my_shape.color = clr;
            my_shape.stroke = str;
            g2d.setColor(clr);
            g2d.setStroke(str);
            //bucket.add(my_shape);
            if(save==false){
                g2d.draw(my_shape.sh);
            }
            else { //save==true -> released 됐을 때
                bucket.add(my_shape);
                //g2d.draw(pen);
            }
        } 
        /* clear button */
        else if(type == 5){
            //super.paintComponents(g);
            bucket = new ArrayList<>();
            //g2d.draw();
        }
        
         else if(type == 0){
             System.out.println("[ Drag 선택 ]");
        }

        // else if(type == 7){
        //     images.image.add(image);
        // }

        //버킷에 담긴거 다 다시 그려주기
        for( My_shape tmp : bucket){
            g2d.setColor(tmp.color);
            g2d.setStroke(tmp.stroke);
            //g2d.fill(filled);

            if(tmp.sh != null){
                if(tmp.fill == true){
                    g2d.setColor(tmp.color);
                    g2d.fill(tmp.sh);
                }
                else 
                    g2d.draw(tmp.sh);
            }
        }

    }

    //____________마우스 이벤트________________
    int a=0, b=0, c=0, d=0;
    int type = 1;
    int pen_color, pen_width;
    boolean save = false;
    int dragX, dragY;
    //int stickerX, stickerY;
    int moveX=0, moveY=0;
    boolean draged = false;
    int moveIndex = 0;


    @Override
    public void mousePressed(MouseEvent e){
        save = false;
        if(type == 1 || type == 2 || type == 3){
            a = (int)(e.getPoint().getX());
            b = (int)(e.getPoint().getY());
            System.out.println("ab"+a+" "+b);
            pen_fill = e.getPoint(); // 나중에 뭔지 알아보기
            System.out.println("x1: " + a + ", y1: " + b);
        }
        else if(type == 4){
            System.out.println("눌렸음~");
            pen_start = e.getPoint();
            Path2D tmp = new Path2D.Double();
            pen = tmp;
            System.out.println("x1: " + e.getPoint().getX() + ", y1: " + e.getPoint().getY());
        }

        if((type == 0 && moved == true) || dlt == true){
            for(int i=0; i<bucket.size(); i++){
                if(bucket.get(i).padding.contains(e.getX(), e.getY())){
                    if(dlt == true){ //지울 때 
                        System.out.println("삭제할건데~~");
                        System.out.println("!!!!!!!!"+i);
                        bucket.remove(i);
                        panel_brd.repaint();
                        dlt = false;
                    }
                    else { //움직일 때
                    System.out.println("**선이 선택되었습니다" + i);
                    moveIndex = i;
                    draged = true;
                    dragX = e.getX();
                    dragY = e.getY();
                    }
                }
            }
        }
        else if(type == 7){
            dragX = e.getX();
            dragY = e.getY();
            panel_brd.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(type == 1 || type == 2 || type == 3){
            c = (int)(e.getPoint().getX());
            d = (int)(e.getPoint().getY());
            panel_brd.repaint();
            System.out.println("x2: " + c + ", y2: " + d);
        }

        else if(type == 4){
            Path2D.Double tmp = (Path2D.Double)pen; // path2d 저장할 경로 P 생성?
            tmp.moveTo(pen_start.x, pen_start.y);
            tmp.lineTo(e.getX(), e.getY());

            //tmp = (Path2D.Double)pen;
            pen = tmp;
            panel_brd.repaint();

            pen_start.x = e.getX();
            pen_start.y = e.getY();

            //tmp.moveTo(e.getX(), e.getY());
            System.out.println("x2: "+pen_start.x + ", y2: "+pen_start.y);
        }

        else if(type == 0 && draged == true){
            moveX = e.getX() - dragX;
            moveY = e.getY() - dragY;

            AffineTransform transform = new AffineTransform();
            transform.translate(moveX, moveY);
            Shape transformSh = transform.createTransformedShape(bucket.get(moveIndex).sh);
            bucket.get(moveIndex).sh = transformSh;
            bucket.get(moveIndex).padding = pad_stroke.createStrokedShape(bucket.get(moveIndex).sh);


            dragX = e.getX();
            dragY = e.getY();

            repaint();
        }
        // else if(dlt == true){
        //     bucket.get(moveIndex).sh.remove(moveIndex);
        // }
        repaint();
    }

    boolean fill_rect, fill_circ;

    @Override
    public void mouseReleased(MouseEvent e){
        save = true;
        if(type == 1 || type ==2 || type == 3){
            c = (int)(e.getPoint().getX());
            d = (int)(e.getPoint().getY());
            panel_brd.repaint();
            System.out.println("x2: " + c + ", y2: " + d);

            if(filled == true){
                //System.out.println("type: "+type);
                for(int i=0; i<bucket.size(); i++){
                    //System.out.println("index: " + bucket.get(i));
                    if(bucket.get(i).sh.contains(pen_fill)){
                        System.out.println("안에 있다롱~!!!");
    
                        if(bucket.get(i).sh instanceof Rectangle2D.Double){
                            //fill_rect = true;
                            bucket.get(i).color = clr;
                            bucket.get(i).fill = true;
                            panel_brd.repaint();
                            //fill_rect = false;
                        }
                        else if(bucket.get(i).sh instanceof Ellipse2D.Double){
                            bucket.get(i).color = clr;
                            bucket.get(i).fill = true;
                            panel_brd.repaint();
                            //fill_circ = false;
                        }
                    }
                }
            }
            filled = false;
            moved = false;
            //fill_rect = false;
        } 
        else if(type == 4){
            Path2D.Double tmp = (Path2D.Double)pen;
            //pen_end = e.getPoint();
            tmp.closePath(); //이제 더 이상 그리지 않겠다
            panel_brd.repaint();
            System.out.println("떨어졌음~~");
        }

    draged = false;
    }

    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}





    public void start(){ //메소드
        //int a= 10, b=10;
        setSize(1000,700); //1000,700
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        panel_t1.setLayout(new BorderLayout());
        panel_t1.setLayout(new GridLayout(1, 4));
        panel_t2.setLayout(new BorderLayout());
        panel_t2.setLayout(new GridLayout(1, 8));
        panel_t3.setLayout(new BorderLayout());
        panel_t3.setLayout(new GridLayout(1, 2));
        panel_t4.setLayout(new BorderLayout());
        panel_t4.setLayout(new GridLayout(1, 3));
        panel_t5.setLayout(new BorderLayout());
        panel_t5.setLayout(new GridLayout(1, 8));

        panel_t1.setBounds(20,20,280,80); //기능 메뉴 (10,10,270,80)
        panel_t2.setBounds(310,20,380,35); //속성 메뉴 (10,10,270,80)
        panel_t3.setBounds(310,65,185,35);
        panel_t4.setBounds(505,65,185,35);
        panel_t5.setBounds(700,20,280,80); //부가 메뉴 (10,10,270,80)
        //panel_t1.add(new JLabel("line, rect, circ ..:"));

        //panel_t1.setBackground(Color.YELLOW);

        panel_brd = new Canvas();

        panel_brd.addMouseListener(this);
        panel_brd.addMouseMotionListener(this);

        add(panel_t1);
        add(panel_t2);
        add(panel_t3);
        add(panel_t4);
        add(panel_t5);
        add(panel_brd);

        try{
            image = ImageIO.read(new File("/Users/imagine/Desktop/tomato.png"));
            System.out.println("이미지 성공><~~");
        } catch (IOException e){
            image = null;
        }

        //___________BUTTON_____________
        //[선, 도형, 펜]
        JButton btn_line = new JButton("line");
        JButton btn_rect = new JButton("rectangle");
        JButton btn_circ = new JButton("circle");
        JButton btn_pen = new JButton("free line");
        //action Listener 추가
        btn_line.addActionListener(button1_action);
        btn_rect.addActionListener(button1_action);
        btn_circ.addActionListener(button1_action);
        btn_pen.addActionListener(button1_action);
        //action lister 추가하기
        panel_t1.add(btn_line);
        panel_t1.add(btn_rect);
        panel_t1.add(btn_circ);
        panel_t1.add(btn_pen);

        //[색상]
        JButton btn_clr1 = new JButton("🍎");
        JButton btn_clr2 = new JButton("🧡");
        JButton btn_clr3 = new JButton("💛");
        JButton btn_clr4 = new JButton("💚");
        JButton btn_clr5 = new JButton("💙");
        JButton btn_clr6 = new JButton("💜");
        JButton btn_clr7 = new JButton("🩷");
        JButton btn_clr8 = new JButton("🖤");
        btn_clr1.addActionListener(button2_action);
        btn_clr2.addActionListener(button2_action);
        btn_clr3.addActionListener(button2_action);
        btn_clr4.addActionListener(button2_action);
        btn_clr5.addActionListener(button2_action);
        btn_clr6.addActionListener(button2_action);
        btn_clr7.addActionListener(button2_action);
        btn_clr8.addActionListener(button2_action);
        panel_t2.add(btn_clr1);
        panel_t2.add(btn_clr2);
        panel_t2.add(btn_clr3);
        panel_t2.add(btn_clr4);
        panel_t2.add(btn_clr5);
        panel_t2.add(btn_clr6);
        panel_t2.add(btn_clr7);
        panel_t2.add(btn_clr8);
        //[선 스타일]
        JButton btn_strt = new JButton("_________");
        JButton btn_dash = new JButton("---------");
        btn_strt.addActionListener(button3_action);
        btn_dash.addActionListener(button3_action);
        panel_t3.add(btn_strt);
        panel_t3.add(btn_dash);
        //[선 두께]
        JButton btn_light = new JButton("light");
        //btn_light.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton btn_regular = new JButton("regular");
        btn_regular.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton btn_bold = new JButton("bold");
        btn_bold.setFont(new Font("Arial", Font.BOLD, 17));
        btn_light.addActionListener(button4_action);
        btn_regular.addActionListener(button4_action);
        btn_bold.addActionListener(button4_action);
        panel_t4.add(btn_light);
        panel_t4.add(btn_regular);
        panel_t4.add(btn_bold);

        //[부가기능+삭제]
        JButton btn_fill = new JButton("Fill"); //💛💛💛💛
        JButton btn_add2 = new JButton("Drag");
        JButton btn_delete = new JButton("Delete");
        JButton btn_clear = new JButton("Clear");
        btn_fill.addActionListener(button5_action);
        btn_add2.addActionListener(button5_action);
        btn_delete.addActionListener(button5_action);
        btn_clear.addActionListener(button5_action);
        panel_t5.add(btn_fill);
        panel_t5.add(btn_add2);
        panel_t5.add(btn_delete);
        panel_t5.add(btn_clear);

        //___________BUTTON_____________
       
        setVisible(true);
    }

    class button1 implements ActionListener {
        public void actionPerformed(ActionEvent e){
            filled = false;
            if(((JButton)e.getSource()).getText().equals("line")){
                System.out.println("직선 그리기 선택"); 
                type = 1;
            } 
            else if(((JButton)e.getSource()).getText().equals("rectangle")){
                System.out.println("사각형 그리기 선택");
                type = 2;
            } 
            else if(((JButton)e.getSource()).getText().equals("circle")){
                System.out.println("원 그리기 선택");
                type = 3;
            } 
            else if(((JButton)e.getSource()).getText().equals("free line")){
                System.out.println("자유곡선 그리기 선택");
                type = 4;
            }
            System.out.println("직선 그리기 선택"); 
        }
    }

    class button2 implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(((JButton)e.getSource()).getText().equals("🍎")){
                System.out.println("스티커 넣기");
                type = 7;
            }
            else if(((JButton)e.getSource()).getText().equals("🧡")){
                System.out.println("주황 선택");
                pen_color = 2;
            }
            else if(((JButton)e.getSource()).getText().equals("💛")){
                System.out.println("노랑 선택");
                pen_color = 3;
            }
            else if(((JButton)e.getSource()).getText().equals("💚")){
                System.out.println("초록 선택");
                pen_color = 4;
            }
            else if(((JButton)e.getSource()).getText().equals("💙")){
                System.out.println("파랑 선택");
                pen_color = 5;
            }
            else if(((JButton)e.getSource()).getText().equals("💜")){
                System.out.println("보라 선택");
                pen_color = 6;
            }
            else if(((JButton)e.getSource()).getText().equals("🩷")){
                System.out.println("분홍 선택");
                pen_color = 7;
            }
            else if(((JButton)e.getSource()).getText().equals("🖤")){
                System.out.println("검정 선택");
                pen_color = 8;
            }
        }
    }

    class button3 implements ActionListener {
        public void actionPerformed(ActionEvent e){
            filled = false;
            if(((JButton)e.getSource()).getText().equals("_________")){
                System.out.println("straight");
                dash = false;
                
            }
            else if(((JButton)e.getSource()).getText().equals("---------")){
                System.out.println("dash");
                dash = true;
            }
        }
    }

    class button4 implements ActionListener {
        public void actionPerformed(ActionEvent e){
            filled = false;
            if(((JButton)e.getSource()).getText().equals("light")){
                System.out.println("light");
                pen_width = 1;         
            }
            else if(((JButton)e.getSource()).getText().equals("regular")){
                System.out.println("regular");
                pen_width = 2;
            }
            else if(((JButton)e.getSource()).getText().equals("bold")){
                System.out.println("bold");
                pen_width = 3;
            }
        }
    }

    class button5 implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(((JButton)e.getSource()).getText().equals("Fill")){
                System.out.println("fill");
                filled = true;
            }
            else if(((JButton)e.getSource()).getText().equals("Drag")){
                System.out.println("Drag & Drop");
                type = 0;
                moved = true;
            }

            else if(((JButton)e.getSource()).getText().equals("Delete")){
                System.out.println("Delete");
                //filled = true;
                moved = false;
                dlt = true;
                type = 6;
            }

            else if(((JButton)e.getSource()).getText().equals("Clear")){
                System.out.println("Clear");
                filled = false;
                type = 5;
                bucket.clear();
                images.clear();
                panel_brd.repaint();
            }
        }
    }
}

