import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Home extends JFrame{
    Home_panel panel1 = new Home_panel();
    JButton[][] btn = new JButton[8][8];
    int[][] array = new int[8][8];
    int[][] put_psb = new int[8][8];
    boolean flag = false;
    int rl_j;
    int rl_i;
    int ex_j;
    int ex_i;
    int cnt = 0;
    boolean change = false;
    int currentTurnColor = 1; //처음 검은돌부터 시작
    JLabel blackStone = new JLabel("02");
    JLabel whiteStone = new JLabel("02");
    int stoneB = 0;
    int stoneW = 0;

    int tmp_cnt; //현재 둘 수 있는 곳 있는지 확인
    JLabel gameOver = new JLabel();
    JLabel winner = new JLabel();





    File file = new File("/Users/imagine/Downloads/청판.png");
    BufferedImage image;

    class Home_panel extends JPanel implements ActionListener {
        public void paintComponent(Graphics g) {
            try{
                image = ImageIO.read(file);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(image, 0,0,875, 690, this);


            for (int j = 0; j < 8; j++){
                for (int i = 0; i < 8; i++){
                    if(array[j][i] == 1){
                        g.setColor(Color.BLACK);
                        g.fillOval(32+75*i, 32+75*j, 61, 61);
                    }
                    else if(array[j][i] == 2){
                        g.setColor(Color.white);
                        g.fillOval(33+75*i, 33+75*j, 61, 61);
                    }
                    if(put_psb[j][i] == 1 && flag == false){ // 내 턴인 경우에만 파란점 뜨게!
                        g.setColor(new Color(0, 217, 217));
                        g.fillOval(57+75*i, 57+75*j, 11, 11);
                    }
                }
            }


            /*현재 차례 돌 띄우기*/
            if (currentTurnColor == 1) {
                g.setColor(Color.BLACK);
                g.fillOval(728, 287, 46, 46);
            } else if (currentTurnColor == 2) {
                g.setColor(Color.white);
                g.fillOval(728, 287, 46, 46);
            }


        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("아이고");

            if(flag != false){ //내 턴이 아니면 넘겨
                return;
            }

            for(int j=0; j<8; j++){
                for(int i =0; i<8; i++){
                    if(e.getSource() == btn[j][i]){
                        System.out.println("버튼 ["+j+"]["+i+"] 눌림");

                        if(array[j][i] != 0){
                            System.out.println("[Notice] 이미 돌이 있습니다^^");
                        }
                        else {
                            if(flag == false){ // 핑크 돌 차례일 때
                                /*넣을 수 있는 조건*/
                                rl_j = j;
                                rl_i = i;

                                // 그 방향으로 쭉 갈 때 (x8방향 비교)
                                // 빈 공간이 나올 때 -> 넣고 break;
                                // 내 색의 돌이 나올 때 -> break;
                                // 벽일 때 -> break;`23

                                //둘 수 있는 수가 없는 경우 -> 턴 넘기기
                                int possible = 0;
                                for(int tmp_c = 0; tmp_c < 8; tmp_c++){
                                    for(int tmp_d = 0; tmp_d < 8; tmp_d++){
                                        if(put_psb[tmp_c][tmp_d] == 1){
                                            possible++;
                                        }
                                    }
                                }
                                if(possible == 0){
                                    flag = true;
                                    put_psb = new int[8][8];
                                    currentTurnColor = 2;
                                    // 흰색 돌 놓을 수 있는 위치 표시하기
                                    //다음 턴 해줄 위치 표시하는 함수 호출
                                    for(int tmp_j=0; tmp_j<8; tmp_j++){
                                        for(int tmp_i=0; tmp_i<8; tmp_i++){
                                            if(array[tmp_j][tmp_i] == 2){
                                                stoneB ++;
                                                System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                                                setPossiblePut(tmp_j, tmp_i, 1);
                                            }
                                        }
                                    }
                                    repaint();
                                    return;
                                }

                                if(flag == false && put_psb[j][i] == 1){ // 놓을 수 있는 자리일 때
                                    array[j][i] = 1;
                                    Sender.exest(j, i); // [j, i] 좌표 전달

                                    /*핑크 돌 놓았을 때 뒤집히는 알고리즘*/
                                    for(int k=0; k<8; k++){ // 8방향 계산
                                        ex_j = j;
                                        ex_i = i;
                                        /*방향 0 (<-)*/
                                        if(k == 0){
                                            if(ex_i-1 > 0 &&  array[ex_j][ex_i-1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽에 회색있음");
                                                while(ex_i-1 > 0 && array[ex_j][ex_i-1] == 2){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i--;
                                                    if(ex_i-1 >= 0 && array[ex_j][ex_i-1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j][ex_i+q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 1*/
                                        if(k == 1){
                                            if(ex_i-1 > 0 &&  ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 2){ //왼쪽 위에 회색돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽 위에 회색있음");
                                                while(ex_i-1 > 0 && ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 2){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i--;
                                                    ex_j--;
                                                    if(ex_i-1 >= 0 && ex_j-1 >= 0 && array[ex_j-1][ex_i-1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i+q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 2*/
                                        if(k == 2){
                                            if(ex_j-1 > 0 &&  array[ex_j-1][ex_i] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽에 회색있음");
                                                while(ex_j-1 > 0 && array[ex_j-1][ex_i] == 2){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j--;
                                                    if(ex_j-1 >= 0 && array[ex_j-1][ex_i] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 3*/
                                        if(k == 3){
                                            if(ex_j-1 > 0 &&  ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("오른쪽 위에 회색있음");
                                                while(ex_j-1 > 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j--;
                                                    ex_i++;
                                                    if(ex_j-1 >= 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i-q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 4*/
                                        if(k == 4){
                                            if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("오른쪽에 회색있음");
                                                while(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i++;
                                                    if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j][ex_i-q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 5*/
                                        if(k == 5){
                                            if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("오른쪽에 회색있음");
                                                while(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i++;
                                                    ex_j++;
                                                    if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i-q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 5 끝

                                        /*방향 6*/
                                        if(k == 6){
                                            if(ex_j+1 < 8 &&  array[ex_j+1][ex_i] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("밑에 회색있음");
                                                while(ex_j+1 < 8 && array[ex_j+1][ex_i] == 2){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j++;
                                                    if(ex_j+1 < 8 && array[ex_j+1][ex_i] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 6 끝

                                        /*방향 7*/
                                        if(k == 7){
                                            if(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽 밑에 회색있음");
                                                while(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 2){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j++;
                                                    ex_i--;
                                                    if(ex_j+1 < 8 && ex_i-1 >= 0 && array[ex_j+1][ex_i-1] == 1) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i+q] = 1;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 7 끝

                                    }

                                    put_psb = new int[8][8]; //놓을 수 있는 위치 배열 초기화
                                    flag = true; // 회색돌에게 턴 넘겨줌
                                    currentTurnColor = 2; //현재 순서 -> 흰색으로 넘겨줌

                                    //다음 턴 해줄 위치 표시하는 함수 호출
                                    for(int tmp_j=0; tmp_j<8; tmp_j++){
                                        for(int tmp_i=0; tmp_i<8; tmp_i++){
                                            if(array[tmp_j][tmp_i] == 2){
                                                stoneB ++;
                                                System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                                                setPossiblePut(tmp_j, tmp_i, 1);
                                            }
                                        }
                                    }
                                }

                                //점수판 현재 돌 개수 측정 - 표시
                                stoneB = 0;
                                stoneW = 0;
                                for(int c=0; c<8; c++){
                                    for(int d=0; d<8; d++){
                                        if(array[c][d] == 1){
                                            stoneB++;
                                        }
                                        else if(array[c][d] == 2){
                                            stoneW++;
                                        }
                                    }
                                }

                                if(stoneB < 10){
                                    blackStone.setText("0"+stoneB);
                                }
                                else blackStone.setText(Integer.toString(stoneB));

                                if(stoneW < 10){
                                    whiteStone.setText("0"+stoneW);
                                }
                                else whiteStone.setText(Integer.toString(stoneW));

                            } //분홍색 돌 차례

                            else if(flag == true){
                                /*넣을 수 있는 조건*/
                                rl_j = j;
                                rl_i = i;

                                //둘 수 있는 수가 없는 경우 -> 턴 넘기기
                                int possible = 0;
                                for(int tmp_c = 0; tmp_c < 8; tmp_c++){
                                    for(int tmp_d = 0; tmp_d < 8; tmp_d++){
                                        if(put_psb[tmp_c][tmp_d] == 1){
                                            possible++;
                                        }
                                    }
                                }
                                if(possible == 0){
                                    flag = false;
                                    put_psb = new int[8][8];
                                    currentTurnColor = 1;
                                    //분홍색의 경우 놓을 수 있는 곳 다시 계산해주기
                                    //다음 턴 해줄 위치 표시하는 함수 호출 (분홍 돌의 입장에서 놓을 수 있는 위치 표시)
                                    for(int tmp_j=0; tmp_j<8; tmp_j++){
                                        for(int tmp_i=0; tmp_i<8; tmp_i++){
                                            if(array[tmp_j][tmp_i] == 1){
                                                System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                                                setPossiblePut(tmp_j, tmp_i, 2);
                                            }
                                        }
                                    }
                                    repaint();
                                    return;
                                }

                                if(flag == true && put_psb[j][i] == 1){

                                    array[j][i] = 2; // 회색 돌

                                    /*회색 돌 놓았을 때 뒤집히는 알고리즘*/
                                    for(int k=0; k<8; k++){
                                        ex_j = j;
                                        ex_i = i;

                                        /*방향 0 (<-)*/
                                        if(k == 0){
                                            if(ex_i-1 > 0 &&  array[ex_j][ex_i-1] == 1){ //옆에 분홍 돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽에 회색있음");
                                                while(ex_i-1 > 0 && array[ex_j][ex_i-1] == 1){ //왼쪽이 분홍색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i--;
                                                    if(ex_i-1 >= 0 && array[ex_j][ex_i-1] == 2) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j][ex_i+q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 0

                                        /*방향 1*/
                                        if(k == 1){
                                            if(ex_i-1 > 0 &&  ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 1){ //왼쪽 위에 회색돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽 위에 회색있음");
                                                while(ex_i-1 > 0 && ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 1){ //왼쪽이 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i--;
                                                    ex_j--;
                                                    if(ex_i-1 >= 0 && ex_j-1 >= 0 && array[ex_j-1][ex_i-1] == 2) {
                                                        System.out.println("핑크색 또만남");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i+q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 2*/
                                        if(k == 2){
                                            if(ex_j-1 > 0 &&  array[ex_j-1][ex_i] == 1){ //옆에 분홍돌이 있다면 쭉볼거야
                                                System.out.println("왼쪽에 분홍색 있음");
                                                while(ex_j-1 > 0 && array[ex_j-1][ex_i] == 1){ //왼쪽이 분홍색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j--;
                                                    if(ex_j-1 >= 0 && array[ex_j-1][ex_i] == 2) {
                                                        System.out.println("회색 끝점");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 3*/
                                        if(k == 3){
                                            if(ex_j-1 > 0 &&  ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1){ //옆에 분홍돌이 있다면 쭉볼거야
                                                System.out.println("오른쪽 위에 분홍색있음");
                                                while(ex_j-1 > 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1){ //오른쪽 위가 분홍색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j--;
                                                    ex_i++;
                                                    if(ex_j-1 >= 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2) {
                                                        System.out.println("회색 끝점");
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j+q][ex_i-q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 4*/
                                        if(k == 4){
                                            if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1){
                                                while(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1){
                                                    cnt ++;
                                                    ex_i++;
                                                    if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2) {
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j][ex_i-q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        }

                                        /*방향 5*/
                                        if(k == 5){
                                            if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1){ //옆에 회색돌이 있다면 쭉볼거야
                                                while(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1){ //오른쪽 위가 회색이면
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_i++;
                                                    ex_j++;
                                                    if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2) {
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i-q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 5 끝

                                        /*방향 6*/
                                        if(k == 6){
                                            if(ex_j+1 < 8 &&  array[ex_j+1][ex_i] == 1){
                                                while(ex_j+1 < 8 && array[ex_j+1][ex_i] == 1){
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j++;
                                                    if(ex_j+1 < 8 && array[ex_j+1][ex_i] == 2) {
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 6 끝

                                        /*방향 7*/
                                        if(k == 7){
                                            if(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 1){
                                                while(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 1){
                                                    cnt ++; //먹을 회색돌 개수 측정
                                                    ex_j++;
                                                    ex_i--;
                                                    if(ex_j+1 < 8 && ex_i-1 >= 0 && array[ex_j+1][ex_i-1] == 2) {
                                                        change = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(change == true){
                                                for(int q=0; q<cnt; q++){
                                                    array[ex_j-q][ex_i+q] = 2;
                                                }
                                                change = false;
                                            }
                                            cnt = 0; // 초기화
                                        } //방향 7 끝


                                    }
                                }

                                put_psb = new int[8][8]; //놓을 수 있는 위치 배열 초기화
                                flag = false; // 분홍돌에게 턴 넘겨줌
                                currentTurnColor = 1;

                                //다음 턴 해줄 위치 표시하는 함수 호출 (분홍 돌의 입장에서 놓을 수 있는 위치 표시)
                                for(int tmp_j=0; tmp_j<8; tmp_j++){
                                    for(int tmp_i=0; tmp_i<8; tmp_i++){
                                        if(array[tmp_j][tmp_i] == 1){
                                            System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                                            setPossiblePut(tmp_j, tmp_i, 2);
                                        }
                                    }
                                }
                                //점수판 현재 돌 개수 측정 - 표시
                                stoneB = 0;
                                stoneW = 0;
                                for(int c=0; c<8; c++){
                                    for(int d=0; d<8; d++){
                                        if(array[c][d] == 1){
                                            stoneB++;
                                        }
                                        else if(array[c][d] == 2){
                                            stoneW++;
                                        }
                                    }
                                }

                                if(stoneB < 10){
                                    blackStone.setText("0"+stoneB);
                                }
                                else blackStone.setText(Integer.toString(stoneB));

                                if(stoneW < 10){
                                    whiteStone.setText("0"+stoneW);
                                }
                                else whiteStone.setText(Integer.toString(stoneW));
                            } //회색 돌 차례
                        }

                    }
                }
            }
            repaint();

            /*게임 종료 시*/
            tmp_cnt=0;
            for(int tmp_c=0; tmp_c<8; tmp_c++){
                for(int tmp_d=0; tmp_d<8; tmp_d++){
                    if(array[tmp_c][tmp_d] == 0){
                        tmp_cnt++;
                    }
                }
            }
            System.out.println("[[tmp_cnt]] "+ tmp_cnt);
            if(tmp_cnt < 1){ //판에 돌이 다 찰 때
                System.out.println("종료");
                gameOver.setText("Game Over");

                if(stoneB > stoneW){
                    System.out.println("검은돌 승");
                    winner.setText("검은돌 승");
                }
                else if(stoneW > stoneB){
                    System.out.println("흰돌 승");
                    winner.setText("흰돌 승");
                }
                else {
                    System.out.println("무승부");
                    winner.setText("무승부");
                }
            }

            /*승패 결정*/

        }
    }

    public Home(){
        setSize(875, 690);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        /*PANEL*/
        panel1.setBounds(0,0,875,690);
        panel1.setLayout(null);

        array[3][4] = 1;
        array[4][3] = 1;
        array[3][3] = 2;
        array[4][4] = 2;

        put_psb[3][2] = 1;
        put_psb[2][3] = 1;
        put_psb[5][4] = 1;
        put_psb[4][5] = 1;

        /*SCORE BOARD*/
        blackStone.setBounds(692, 103, 50, 42);
        blackStone.setFont(new Font("Arial", Font.PLAIN, 35));
        whiteStone.setBounds(765, 103, 50, 42);
        whiteStone.setFont(new Font("Arial", Font.PLAIN, 35));
        whiteStone.setForeground(Color.white);

        panel1.add(blackStone);
        panel1.add(whiteStone);

        /*게임 종료 시 화면*/
        gameOver.setBounds(125, 176, 499, 80);
        gameOver.setFont(new Font("Arial", Font.BOLD, 68));
        gameOver.setForeground(Color.red);
        panel1.add(gameOver);

        winner.setBounds(253, 297, 160, 50);
        winner.setFont(new Font("Arial", Font.PLAIN, 40));
        winner.setForeground(new Color(0, 156, 190));
        panel1.add(winner);


        /*BUTTON*/
        for(int j=0; j<8; j++){
            for(int i=0; i<8; i++){
                btn[j][i] = new JButton();
                btn[j][i].addActionListener(panel1);
                btn[j][i].setBounds(33+75*i,33+75*j,60,60);
                btn[j][i].setActionCommand(j+"/"+i); //string
                panel1.add(btn[j][i]);
                btn[j][i].setContentAreaFilled(false);
                btn[j][i].setFocusPainted(false);
                btn[j][i].setBorderPainted(false);
            }
        }



        add(panel1);
        setVisible(true);
    }

    public void setPossiblePut(int j, int i, int turn){
        int a = turn;
        int b;
        if(a == 1) b = 2;
        else b = 1;



        /*놓을 수 있는 위치 확인*/
        for(int k=0; k<8; k++){
            int tmp_j = j;
            int tmp_i = i;
            /*방향 0 (<-)*/
            if(k == 0){
                if(tmp_i-1 > 0 &&  array[tmp_j][tmp_i-1] == a){ //옆에 분홍색이 있다면 쭉볼거야
                    System.out.println("[가능한 위치 찾기] 왼쪽에 분홍색있음");
                    while(tmp_i-1 > 0 && array[tmp_j][tmp_i-1] == a){ //왼쪽이 분홍색이면
                        System.out.println("들어와ㅏㅆ냐");
                        tmp_i--;
                        if(tmp_i-1 < 0 || array[tmp_j][tmp_i-1] == b){
                            System.out.println("넣을 곳 없다!!");
                            break;
                        }
                        if(array[tmp_j][tmp_i-1] == 0) {
                            System.out.println("넣을 곳 찾음!");
                            put_psb[tmp_j][tmp_i-1] = 1; // 0: 불가능, 1: 가능한 위치
                            break;
                        }
                    }
                }
            } //0번 방향 - 회색이 놓을 수 있는 위치 확인

            /*방향 1*/
            if(k == 1){
                if(tmp_i-1 > 0 &&  tmp_j-1 > 0 && array[tmp_j-1][tmp_i-1] == a){
                    System.out.println("빙고!");//왼쪽 위에 분홍색 돌이 있다면 쭉볼거야
                    System.out.println("[가능한 위치 찾기] 왼쪽 위에 분홍색 있음");
                    while(tmp_i-1 > 0 && tmp_j-1 > 0 && array[tmp_j-1][tmp_i-1] == a){ //왼쪽 위에 분홍색 돌이면
                        tmp_i--;
                        tmp_j--;
                        if(tmp_i-1 < 0 || tmp_j-1 < 0 || array[tmp_j-1][tmp_i-1] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j-1][tmp_i-1] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j-1][tmp_i-1] = 1;
                            break;
                        }
                    }
                }
            } //1번 방향 놓을 수 있는 위치 확인

            /*방향 2*/
            if(k == 2){
                if(tmp_j-1 > 0 &&  array[tmp_j-1][tmp_i] == a){ //옆에 분홍색 돌이 있다면 쭉볼거야
                    System.out.println("[가능한 위치 찾기] 위에 분홍색 있음");
                    while(tmp_j-1 > 0 && array[tmp_j-1][tmp_i] == a){ //왼쪽이 분홍색이면
                        tmp_j--;
                        if(tmp_j-1 < 0 || array[tmp_j-1][tmp_i] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j-1][tmp_i] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j-1][tmp_i] = 1;
                            break;
                        }
                    }
                }
            }

            /*방향 3*/
            if(k == 3){
                if(tmp_j-1 > 0 &&  tmp_i+1 < 8 && array[tmp_j-1][tmp_i+1] == a){ //옆에 분홍돌이 있다면 쭉볼거야
                    System.out.println("[가능한 위치 찾기] 오른쪽 위에 분홍색있음");
                    while(tmp_j-1 > 0 && tmp_i+1 < 8 && array[tmp_j-1][tmp_i+1] == a){ //오른쪽 위가 분홍색이면
                        tmp_j--;
                        tmp_i++;
                        if(tmp_j-1 < 0 || tmp_i+1 > 7 || array[tmp_j-1][tmp_i+1] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j-1][tmp_i+1] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j-1][tmp_i+1] = 1;
                            break;
                        }
                    }
                }
            }

            /*방향 4*/
            if(k == 4){
                if(tmp_i+1 < 8 && array[tmp_j][tmp_i+1] == a){
                    while(tmp_i+1 < 8 && array[tmp_j][tmp_i+1] == a){
                        tmp_i++;
                        if(tmp_i+1 > 7 || array[tmp_j][tmp_i+1] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j][tmp_i+1] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j][tmp_i+1] = 1;
                            break;
                        }
                    }
                }
            }

            /*방향 5*/
            if(k == 5){
                if(tmp_j+1 < 8 && tmp_i+1 < 8 && array[tmp_j+1][tmp_i+1] == a){
                    System.out.println("빙고!~~~~~");//옆에 분홍색 돌이 있다면 쭉볼거야
                    while(tmp_j+1 < 8 && tmp_i+1 < 8 && array[tmp_j+1][tmp_i+1] == a){ //오른쪽 위가 분홍색이면
                        tmp_i++;
                        tmp_j++;
                        if(tmp_i+1 > 7 || tmp_j+1 > 7 || array[tmp_j][tmp_i+1] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j+1][tmp_i+1] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j+1][tmp_i+1] = 1;
                            break;
                        }
                    }
                }
            } //방향 5 끝

            /*방향 6*/
            if(k == 6){
                if(tmp_j+1 < 8 &&  array[tmp_j+1][tmp_i] == a){
                    while(tmp_j+1 < 8 && array[tmp_j+1][tmp_i] == a){
                        tmp_j++;
                        if(tmp_j+1 > 7 || array[tmp_j+1][tmp_i] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j+1][tmp_i] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j+1][tmp_i] = 1;
                            break;
                        }
                    }
                }
            } //방향 6 끝

            /*방향 7*/
            if(k == 7){
                if(tmp_j+1 < 8 &&  tmp_i-1 > 0 && array[tmp_j+1][tmp_i-1] == a){
                    while(tmp_j+1 < 8 &&  tmp_i-1 > 0 && array[tmp_j+1][tmp_i-1] == a){
                        tmp_j++;
                        tmp_i--;
                        if(tmp_j+1 > 7 || tmp_i-1 < 0 || array[tmp_j+1][tmp_i-1] == b){ // 같은 색(분홍)을 만나거나, 벽을 만나면
                            break;
                        }
                        if(array[tmp_j+1][tmp_i-1] == 0) { //공백을 만나면
                            System.out.println("넣을 곳  찾음!");
                            put_psb[tmp_j+1][tmp_i-1] = 1;
                            break;
                        }
                    }
                }
            } //방향 7 끝

        }


    }

    public void setOtherStone(int j, int i){
        if(flag == false){ // 핑크 돌 차례일 때
            /*넣을 수 있는 조건*/
            rl_j = j;
            rl_i = i;

            // 그 방향으로 쭉 갈 때 (x8방향 비교)
            // 빈 공간이 나올 때 -> 넣고 break;
            // 내 색의 돌이 나올 때 -> break;
            // 벽일 때 -> break;`23

            //둘 수 있는 수가 없는 경우 -> 턴 넘기기
            int possible = 0;
            for(int tmp_c = 0; tmp_c < 8; tmp_c++){
                for(int tmp_d = 0; tmp_d < 8; tmp_d++){
                    if(put_psb[tmp_c][tmp_d] == 1){
                        possible++;
                    }
                }
            }
            if(possible == 0){
                flag = true;
                put_psb = new int[8][8];
                currentTurnColor = 2;
                // 흰색 돌 놓을 수 있는 위치 표시하기
                //다음 턴 해줄 위치 표시하는 함수 호출
                for(int tmp_j=0; tmp_j<8; tmp_j++){
                    for(int tmp_i=0; tmp_i<8; tmp_i++){
                        if(array[tmp_j][tmp_i] == 2){
                            //stoneB ++;
                            System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                            setPossiblePut(tmp_j, tmp_i, 1);
                        }
                    }
                }
                repaint();
                return;
            }

            if(flag == false && put_psb[j][i] == 1){ // 놓을 수 있는 자리일 때
                array[j][i] = 1;

                /*핑크 돌 놓았을 때 뒤집히는 알고리즘*/
                for(int k=0; k<8; k++){ // 8방향 계산
                    ex_j = j;
                    ex_i = i;
                    /*방향 0 (<-)*/
                    if(k == 0){
                        if(ex_i-1 > 0 &&  array[ex_j][ex_i-1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("왼쪽에 회색있음");
                            while(ex_i-1 > 0 && array[ex_j][ex_i-1] == 2){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i--;
                                if(ex_i-1 >= 0 && array[ex_j][ex_i-1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j][ex_i+q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 1*/
                    if(k == 1){
                        if(ex_i-1 > 0 &&  ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 2){ //왼쪽 위에 회색돌이 있다면 쭉볼거야
                            System.out.println("왼쪽 위에 회색있음");
                            while(ex_i-1 > 0 && ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 2){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i--;
                                ex_j--;
                                if(ex_i-1 >= 0 && ex_j-1 >= 0 && array[ex_j-1][ex_i-1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i+q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 2*/
                    if(k == 2){
                        if(ex_j-1 > 0 &&  array[ex_j-1][ex_i] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("왼쪽에 회색있음");
                            while(ex_j-1 > 0 && array[ex_j-1][ex_i] == 2){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j--;
                                if(ex_j-1 >= 0 && array[ex_j-1][ex_i] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 3*/
                    if(k == 3){
                        if(ex_j-1 > 0 &&  ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("오른쪽 위에 회색있음");
                            while(ex_j-1 > 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j--;
                                ex_i++;
                                if(ex_j-1 >= 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i-q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 4*/
                    if(k == 4){
                        if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("오른쪽에 회색있음");
                            while(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i++;
                                if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j][ex_i-q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 5*/
                    if(k == 5){
                        if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("오른쪽에 회색있음");
                            while(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2){ //오른쪽 위가 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i++;
                                ex_j++;
                                if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i-q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 5 끝

                    /*방향 6*/
                    if(k == 6){
                        if(ex_j+1 < 8 &&  array[ex_j+1][ex_i] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("밑에 회색있음");
                            while(ex_j+1 < 8 && array[ex_j+1][ex_i] == 2){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j++;
                                if(ex_j+1 < 8 && array[ex_j+1][ex_i] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 6 끝

                    /*방향 7*/
                    if(k == 7){
                        if(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 2){ //옆에 회색돌이 있다면 쭉볼거야
                            System.out.println("왼쪽 밑에 회색있음");
                            while(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 2){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j++;
                                ex_i--;
                                if(ex_j+1 < 8 && ex_i-1 >= 0 && array[ex_j+1][ex_i-1] == 1) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i+q] = 1;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 7 끝

                }

                put_psb = new int[8][8]; //놓을 수 있는 위치 배열 초기화
                flag = true; // 회색돌에게 턴 넘겨줌
                currentTurnColor = 2; //현재 순서 -> 흰색으로 넘겨줌

                //다음 턴 해줄 위치 표시하는 함수 호출
                for(int tmp_j=0; tmp_j<8; tmp_j++){
                    for(int tmp_i=0; tmp_i<8; tmp_i++){
                        if(array[tmp_j][tmp_i] == 2){
                            stoneB ++;
                            System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                            setPossiblePut(tmp_j, tmp_i, 1);
                        }
                    }
                }
            }

            //점수판 현재 돌 개수 측정 - 표시
            stoneB = 0;
            stoneW = 0;
            for(int c=0; c<8; c++){
                for(int d=0; d<8; d++){
                    if(array[c][d] == 1){
                        stoneB++;
                    }
                    else if(array[c][d] == 2){
                        stoneW++;
                    }
                }
            }

            if(stoneB < 10){
                blackStone.setText("0"+stoneB);
            }
            else blackStone.setText(Integer.toString(stoneB));

            if(stoneW < 10){
                whiteStone.setText("0"+stoneW);
            }
            else whiteStone.setText(Integer.toString(stoneW));

        } //분홍색 돌 차례

        else if(flag == true){
            /*넣을 수 있는 조건*/
            rl_j = j;
            rl_i = i;

            //둘 수 있는 수가 없는 경우 -> 턴 넘기기
            int possible = 0;
            for(int tmp_c = 0; tmp_c < 8; tmp_c++){
                for(int tmp_d = 0; tmp_d < 8; tmp_d++){
                    if(put_psb[tmp_c][tmp_d] == 1){
                        possible++;
                    }
                }
            }
            if(possible == 0){
                flag = false;
                put_psb = new int[8][8];
                currentTurnColor = 1;
                //분홍색의 경우 놓을 수 있는 곳 다시 계산해주기
                //다음 턴 해줄 위치 표시하는 함수 호출 (분홍 돌의 입장에서 놓을 수 있는 위치 표시)
                for(int tmp_j=0; tmp_j<8; tmp_j++){
                    for(int tmp_i=0; tmp_i<8; tmp_i++){
                        if(array[tmp_j][tmp_i] == 1){
                            System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                            setPossiblePut(tmp_j, tmp_i, 2);
                        }
                    }
                }
                repaint();
                return;
            }

            if(flag == true && put_psb[j][i] == 1){

                array[j][i] = 2; // 회색 돌

                /*회색 돌 놓았을 때 뒤집히는 알고리즘*/
                for(int k=0; k<8; k++){
                    ex_j = j;
                    ex_i = i;

                    /*방향 0 (<-)*/
                    if(k == 0){
                        if(ex_i-1 > 0 &&  array[ex_j][ex_i-1] == 1){ //옆에 분홍 돌이 있다면 쭉볼거야
                            System.out.println("왼쪽에 회색있음");
                            while(ex_i-1 > 0 && array[ex_j][ex_i-1] == 1){ //왼쪽이 분홍색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i--;
                                if(ex_i-1 >= 0 && array[ex_j][ex_i-1] == 2) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j][ex_i+q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 0

                    /*방향 1*/
                    if(k == 1){
                        if(ex_i-1 > 0 &&  ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 1){ //왼쪽 위에 회색돌이 있다면 쭉볼거야
                            System.out.println("왼쪽 위에 회색있음");
                            while(ex_i-1 > 0 && ex_j-1 > 0 && array[ex_j-1][ex_i-1] == 1){ //왼쪽이 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i--;
                                ex_j--;
                                if(ex_i-1 >= 0 && ex_j-1 >= 0 && array[ex_j-1][ex_i-1] == 2) {
                                    System.out.println("핑크색 또만남");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i+q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 2*/
                    if(k == 2){
                        if(ex_j-1 > 0 &&  array[ex_j-1][ex_i] == 1){ //옆에 분홍돌이 있다면 쭉볼거야
                            System.out.println("왼쪽에 분홍색 있음");
                            while(ex_j-1 > 0 && array[ex_j-1][ex_i] == 1){ //왼쪽이 분홍색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j--;
                                if(ex_j-1 >= 0 && array[ex_j-1][ex_i] == 2) {
                                    System.out.println("회색 끝점");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 3*/
                    if(k == 3){
                        if(ex_j-1 > 0 &&  ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1){ //옆에 분홍돌이 있다면 쭉볼거야
                            System.out.println("오른쪽 위에 분홍색있음");
                            while(ex_j-1 > 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 1){ //오른쪽 위가 분홍색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j--;
                                ex_i++;
                                if(ex_j-1 >= 0 && ex_i+1 < 8 && array[ex_j-1][ex_i+1] == 2) {
                                    System.out.println("회색 끝점");
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j+q][ex_i-q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 4*/
                    if(k == 4){
                        if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1){
                            while(ex_i+1 < 8 && array[ex_j][ex_i+1] == 1){
                                cnt ++;
                                ex_i++;
                                if(ex_i+1 < 8 && array[ex_j][ex_i+1] == 2) {
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j][ex_i-q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    }

                    /*방향 5*/
                    if(k == 5){
                        if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1){ //옆에 회색돌이 있다면 쭉볼거야
                            while(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 1){ //오른쪽 위가 회색이면
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_i++;
                                ex_j++;
                                if(ex_j+1 < 8 && ex_i+1 < 8 && array[ex_j+1][ex_i+1] == 2) {
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i-q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 5 끝

                    /*방향 6*/
                    if(k == 6){
                        if(ex_j+1 < 8 &&  array[ex_j+1][ex_i] == 1){
                            while(ex_j+1 < 8 && array[ex_j+1][ex_i] == 1){
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j++;
                                if(ex_j+1 < 8 && array[ex_j+1][ex_i] == 2) {
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 6 끝

                    /*방향 7*/
                    if(k == 7){
                        if(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 1){
                            while(ex_j+1 < 8 &&  ex_i-1 > 0 && array[ex_j+1][ex_i-1] == 1){
                                cnt ++; //먹을 회색돌 개수 측정
                                ex_j++;
                                ex_i--;
                                if(ex_j+1 < 8 && ex_i-1 >= 0 && array[ex_j+1][ex_i-1] == 2) {
                                    change = true;
                                    break;
                                }
                            }
                        }
                        if(change == true){
                            for(int q=0; q<cnt; q++){
                                array[ex_j-q][ex_i+q] = 2;
                            }
                            change = false;
                        }
                        cnt = 0; // 초기화
                    } //방향 7 끝


                }
            }

            put_psb = new int[8][8]; //놓을 수 있는 위치 배열 초기화
            flag = false; // 분홍돌에게 턴 넘겨줌
            currentTurnColor = 1;

            //다음 턴 해줄 위치 표시하는 함수 호출 (분홍 돌의 입장에서 놓을 수 있는 위치 표시)
            for(int tmp_j=0; tmp_j<8; tmp_j++){
                for(int tmp_i=0; tmp_i<8; tmp_i++){
                    if(array[tmp_j][tmp_i] == 1){
                        System.out.println("grey x,y: "+tmp_j+", "+tmp_i);
                        setPossiblePut(tmp_j, tmp_i, 2);
                    }
                }
            }
            //점수판 현재 돌 개수 측정 - 표시
            stoneB = 0;
            stoneW = 0;
            for(int c=0; c<8; c++){
                for(int d=0; d<8; d++){
                    if(array[c][d] == 1){
                        stoneB++;
                    }
                    else if(array[c][d] == 2){
                        stoneW++;
                    }
                }
            }

            if(stoneB < 10){
                blackStone.setText("0"+stoneB);
            }
            else blackStone.setText(Integer.toString(stoneB));

            if(stoneW < 10){
                whiteStone.setText("0"+stoneW);
            }
            else whiteStone.setText(Integer.toString(stoneW));
        } //회색 돌 차례

        /*게임 종료 시*/
        tmp_cnt=0;
        for(int tmp_c=0; tmp_c<8; tmp_c++){
            for(int tmp_d=0; tmp_d<8; tmp_d++){
                if(array[tmp_c][tmp_d] == 0){
                    tmp_cnt++;
                }
            }
        }
        System.out.println("[[tmp_cnt]] "+ tmp_cnt);
        if(tmp_cnt < 1){ //판에 돌이 다 찰 때
            System.out.println("종료");
            gameOver.setText("Game Over");

            if(stoneB > stoneW){
                System.out.println("검은돌 승");
                winner.setText("검은돌 승");
            }
            else if(stoneW > stoneB){
                System.out.println("흰돌 승");
                winner.setText("흰돌 승");
            }
            else {
                System.out.println("무승부");
                winner.setText("무승부");
            }
        }
        repaint();
    }
}
