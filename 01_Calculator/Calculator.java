import javax.swing.*;

import java.util.*;
import java.util.Queue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calculator extends JFrame{

    JButton btn1, btn2, btn3, btnAdd, btnSub, btnMul, btnDiv, btnResult;
    JFrame f = new JFrame();
    Queue<String> infix = new LinkedList<>();
    Stack<String> st = new Stack<>();
    String output = ""; //postfix 출력
    JLabel label = new JLabel("0");

    String number = ""; //숫자 한 개 ("1, 12, 132 ...")
    String text = "";
    double st_number;
    double n1, n2, result;
    char op1=1, op2;
    boolean op_exist = false;
    boolean dot_exist = false;
    boolean color_changed = false;
    boolean dark_mode = false;

    // . 패턴 : 소수점 표시
    DecimalFormat df = new DecimalFormat("#########.########");
    //System.out.println(df.format(n)); // 출력값 : 123.456

    ActionListener window = new ActionListener() {
        public void actionPerformed(ActionEvent e){
            JButton targetBtn = (JButton) e.getSource();
            if(e.getSource() == targetBtn){
                text += targetBtn.getText();
                label.setText(text);
                op_exist = false;
            }
        }
    };

    ActionListener dot_action = new ActionListener() {
        public void actionPerformed(ActionEvent e){
            if(dot_exist == false){
                number += ((JButton)e.getSource()).getText();
                System.out.println(number);


                JButton targetBtn = (JButton) e.getSource();
                if(e.getSource() == targetBtn){
                    text += targetBtn.getText();
                    label.setText(text);
                }
                dot_exist = true;
            } else {
                label.setText("Error ⍢");
            }
            
        }
    };

    class numberAction implements ActionListener {
        public void actionPerformed(ActionEvent e){
            number += ((JButton)e.getSource()).getText();
            System.out.println(number);
        }
    }

    public Calculator(){
        
        f.setSize(300, 450);
        f.setTitle("calculator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X btn -> close
        f.setLocationRelativeTo(null);
        f.getContentPane().setLayout(null);
        label.setHorizontalAlignment(JLabel.RIGHT);

        numberAction act = new numberAction();
        JPanel[] p = new JPanel[2];
        p[0] = new JPanel();
        p[0].setLayout(new BorderLayout());
    
        p[1] = new JPanel();
        p[0].setBounds(10,0,280,100);
        p[1].setBounds(0,100,300,300);
        p[0].add(BorderLayout.EAST, label);
        label.setFont(new Font("Arial", Font.PLAIN, 30));

        p[1].setLayout(new GridLayout(5,4));
        f.add(p[0]);
        f.add(p[1]);


        //숫자 버튼 생성+추가
        JButton[] btn= new JButton[10];
        for(int i=0; i<10; i++){
            btn[i] = new JButton(String.valueOf(i));
            btn[i].addActionListener(act);
            btn[i].addActionListener(window);
        }


        // 공백 버튼 --^↩
        JButton space_btn1 = new JButton("");
        space_btn1.setFont(new Font("Arial", Font.PLAIN, 17));
        space_btn1.setForeground(Color.LIGHT_GRAY);
        JButton space_btn2 = new JButton("💭");
        space_btn2.setFont(new Font("Arial", Font.PLAIN, 17));
        space_btn2.setForeground(Color.LIGHT_GRAY);
        JButton space_btn3 = new JButton("🔎");
        space_btn3.setForeground(Color.GRAY);

        JButton btnDot = new JButton(".");

        ///위치 옮기기~~~~~~!~!~!~!!~!~!~!~!!~~~~~~~~~~~~~~~~!!!!!!!

        //계산기 이름 바꾸기
        ActionListener space_btn1_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String answer = JOptionPane.showInputDialog("바꿔줘~ 나만의 Calculator!");
                f.setTitle(answer);
            }
        };
        space_btn1.addActionListener(space_btn1_action);
        
        //! 기능 추천받기~~~
        ActionListener space_btn2_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String answer = JOptionPane.showInputDialog("2.0ver 에 넣을 기능을 추천받아요 >< ~ \n (이 위치에 들어갈 기능이 뭐가 좋을까?)");
                System.out.println(answer);
            }
        };
        space_btn2.addActionListener(space_btn2_action);

        //btnDot.addActionListener(act);
        btnDot.addActionListener(dot_action);


        JButton btnAdd = new JButton("+");
        JButton btnSub = new JButton("ㅡ");
        JButton btnMul = new JButton("✕");
        JButton btnDiv = new JButton("÷");

        JButton btnInit = new JButton("AC");
        JButton btnResult = new JButton("=");

        //첫번째 라인
        p[1].add(btnInit);
        //두 칸 띄우고
        p[1].add(space_btn1);
        p[1].add(space_btn2);
        p[1].add(btnDiv);

        //두번째 라인
        p[1].add(btn[1]);
        p[1].add(btn[2]);
        p[1].add(btn[3]);
        p[1].add(btnMul);

        //세번째 줄
        p[1].add(btn[4]);
        p[1].add(btn[5]);
        p[1].add(btn[6]);
        p[1].add(btnSub);

        //네번째 줄
        p[1].add(btn[7]);
        p[1].add(btn[8]);
        p[1].add(btn[9]);
        p[1].add(btnAdd);

        p[1].add(space_btn3);
        p[1].add(btn[0]); //길이 늘리기
        p[1].add(btnDot);
        p[1].add(btnResult);


        // [+] 버튼 누르면...
        ActionListener btnAdd_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.add(number); //숫자 1개 -> queue에 넣기
                number = ""; //
                text = "";
                dot_exist = false;

                if(op_exist == true){
                    label.setText("Error : 뚠-!");
                } else {
                    infix.add("+");
                    // System.out.println("+");
                    op_exist = true;
                } 
            }
        };
        btnAdd.addActionListener(btnAdd_action);
        //btnAdd.addActionListener(window);

        // [-] 버튼 누르면...
        ActionListener btnSub_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.add(number); //숫자 1개 -> queue에 넣기
                number = ""; //
                text = "";
                dot_exist = false;
                if(op_exist == true){
                    label.setText("Error : 연산자 중복^^;");
                } else {
                    infix.add("-");
                    // System.out.println("-");
                    op_exist = true;
                }
            }
        };
        btnSub.addActionListener(btnSub_action);
        //btnSub.addActionListener(window);

        ////button *
        ActionListener btnMul_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.add(number);
                number = "";
                text = "";
                dot_exist = false;
                if(op_exist == true){
                    label.setText("Error : 에로로롱~;");
                } else {
                    infix.add("*");
                    // System.out.println("*");
                    op_exist = true;
                }
            }
        };
        btnMul.addActionListener(btnMul_action);
        //btnMul.addActionListener(window);

        // [/] 버튼 누르면...
        ActionListener btnDiv_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.add(number); //숫자 1개 -> queue에 넣기
                number = ""; //
                text = "";
                dot_exist = false;
                if(op_exist == true){
                    label.setText("Error : 연산자 중복!");
                } else {
                    infix.add("/");
                    // System.out.println("/");
                    op_exist = true;
                }
            }
        };
        btnDiv.addActionListener(btnDiv_action);
        //btnDiv.addActionListener(window);

        // [\O/] 버튼 누르면...
        ActionListener space_btn3_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(color_changed == false){
                    for(int i=0; i<10; i++){
                        btn[i].setFont(new Font("Arial", Font.ITALIC, 22));
                    }
                    btnDot.setFont(new Font("Arial", Font.BOLD, 20));
                    btnAdd.setForeground(new Color(107, 189, 215));
                    btnAdd.setFont(new Font("Arial", Font.BOLD, 22));
                    btnSub.setForeground(new Color(107, 189, 215));
                    btnSub.setFont(new Font("Arial", Font.BOLD, 20));
                    btnMul.setForeground(new Color(107, 189, 215));
                    btnMul.setFont(new Font("Arial", Font.BOLD, 20));
                    btnDiv.setForeground(new Color(107, 189, 215));
                    btnDiv.setFont(new Font("Arial", Font.BOLD, 25));
                    btnInit.setForeground(new Color(70, 130, 180));
                    btnInit.setFont(new Font("Arial", Font.BOLD, 20));
                    btnResult.setFont(new Font("Arial", Font.BOLD, 20));
                    space_btn3.setForeground(Color.GRAY);
                    space_btn3.setFont(new Font("Arial", Font.BOLD, 15));
                    space_btn1.setFont(new Font("Arial", Font.PLAIN, 20));
                    space_btn1.setForeground(Color.GRAY);
                    space_btn2.setFont(new Font("Arial", Font.PLAIN, 20));
                    space_btn2.setForeground(Color.LIGHT_GRAY);
                    color_changed = true;
                } 
                else {
                    for(int i=0; i<10; i++){
                        btn[i].setFont(new Font("Arial", Font.PLAIN, 14));
                    }
                    btnDot.setFont(new Font("Arial", Font.PLAIN, 14));
                    btnAdd.setForeground(Color.BLACK);
                    btnAdd.setFont(new Font("Arial", Font.PLAIN, 14));
                    btnSub.setForeground(Color.BLACK);
                    btnSub.setFont(new Font("Arial", Font.PLAIN, 13));
                    btnMul.setForeground(Color.BLACK);
                    btnMul.setFont(new Font("Arial", Font.PLAIN, 12));
                    btnDiv.setForeground(Color.BLACK);
                    btnDiv.setFont(new Font("Arial", Font.PLAIN, 19));
                    btnInit.setForeground(Color.BLACK);
                    btnInit.setFont(new Font("Arial", Font.PLAIN, 14));
                    btnResult.setFont(new Font("Arial", Font.PLAIN, 14));
                    space_btn3.setForeground(Color.GRAY);
                    space_btn3.setFont(new Font("Arial", Font.PLAIN, 14));
                    space_btn1.setFont(new Font("Arial", Font.PLAIN, 17));
                    space_btn1.setForeground(Color.LIGHT_GRAY);
                    space_btn2.setFont(new Font("Arial", Font.PLAIN, 17));
                    space_btn2.setForeground(Color.LIGHT_GRAY);
                    color_changed = false;
                }
                
            }
        };
        space_btn3.addActionListener(space_btn3_action);


        //[AC] 버튼
        ActionListener btnInit_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.clear();
                st.clear();
                number = "";
                text = "";
                op_exist = false;
                dot_exist = false;
                label.setText("0"); // "=" 눌렀을때만 결과값 나오도록 수정할 거!!!
                System.out.println("AC");
            }
        };
        btnInit.addActionListener(btnInit_action);

        // [=] 버튼
        ActionListener btnResult_action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                infix.add(number);
                number = "";
                text = "";
                System.out.print("Infix: ");
                for(String i : infix){
                    System.out.print(i + " ");
                }
                System.out.println();
                while(!infix.isEmpty()){
                    if(!infix.peek().equals("+") && !infix.peek().equals("-") && !infix.peek().equals("*") && !infix.peek().equals("/")){
                        output += infix.remove();
                        output += " ";
                    } 
                    else if(st.empty()){
                        st.push(infix.remove());
                    } 
                    else if(infix.peek() == st.peek()){
                        st.push(infix.remove());
                    }
                    else if(infix.peek()=="*" || infix.peek()=="/"){
                        st.push(infix.remove());
                    } 

                    else {
                        output += st.peek() + " ";
                        st.pop();
                        st.push(infix.remove());
                    }
                }

                while(!st.empty()){
                    output += st.peek() + " ";
                    st.pop();
                }
                System.out.println("postfix : " +output);





                //output -> 숫자면 스택, 연산자면 스택에서 숫자 2개 꺼내기
                String[] array = output.split(" ");

                for(int i=0; i<array.length; i++){
                    if(array[i].equals("")) {
                        continue;
                    } 
                    if(!array[i].equals("+") && !array[i].equals("-") && !array[i].equals("*") && !array[i].equals("/")){
                        st.push(array[i]);
                    } 
                    else {
                        n2 = Double.parseDouble(st.peek());
                        st.pop();
                        n1 = Double.parseDouble(st.peek());
                        st.pop();

                        if(array[i].equals("+")){
                            result = n1 + n2;
                            st.push(Double.toString(result));
                        } 
                        else if(array[i].equals("-")){
                            result = n1 - n2;
                            st.push(Double.toString(result));
                        } 
                        else if(array[i].equals("*")){
                            result = n1 * n2;
                            st.push(Double.toString(result));
                        } 
                        else if(array[i].equals("/")){
                            result = n1 / n2;
                            st.push(Double.toString(result));
                        }

                    }
                }

                if(result%1 == 0){
                    long temp =(long)result;
                    String s="";
                    s+=temp;
                    label.setText(s);
                }
                else{
                    label.setText(df.format(result)); //콘솔에 결과값 출력
                }
                
                output = "";
                st.clear();
                op_exist = false;
                infix.add(Double.toString(result));
            }
        };
        btnResult.addActionListener(btnResult_action);


        f.setVisible(true);
    }

    public static void main(String args[]) {
        Calculator frame = new Calculator();
    }
}