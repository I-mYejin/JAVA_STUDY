import java.sql.*;
import java.util.ArrayList;


public class Sql {

    UserInfo userInfo = new UserInfo();

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    Connection conn = null;
    int count = 0;

    public Sql() {

        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Loading Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() { // 커넥션 해주는 역할, DB객체

        String server = "localhost";
        String database = "db_project";
        String user_name = "root";
        String password = "0318";

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false", user_name, password);
            System.out.println("Oracle Connection Success!!");
        } catch (SQLException e) {
            System.out.println("DB를 연결하지 못했습니다");
        }
        return con;
    }

    // 추가
    public int insertData(String id, String pwd1, String name,
                          String birth, String work, String married, String gender) {
        // Query문을 준비

        String sql = " INSERT INTO users (id, password, name, birth, work, married, gender) "
                + " VALUES ('" + id + "','" + pwd1 + "','" + name + "','"
                + birth + "','" + work + "','" + married + "','" + gender + " ') ";

        System.out.println(sql);

        // DB Connection
        Connection conn = getConnection();
        Statement state = null;
        int count = 0;

        // DB Processing (처리)
        try {
            state = conn.createStatement();
            count = state.executeUpdate(sql);

            System.out.println("성공적으로 추가되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if (state != null) {
                    state.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return count;
    }

    // 아이디 검색
    public int selectIdData(String id) throws SQLException {
        // Query문을 준비

        try{
            conn = getConnection();
            String sql = " SELECT * FROM " + " users WHERE id LIKE '" + id + "' ";
            pstmt = conn.prepareStatement(sql); // 컴파일 후 시랳ㅇ
            rs = pstmt.executeQuery();

            while(rs.next()){
                String get_id = rs.getString("id");
                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        return count;
    }

    //사용자 정보 객체에 담는 함수
    public UserInfo selectData(String id) throws SQLException {
        //String get_name = "";
        // Query문을 준비
        try{
            conn = getConnection();
            //SELECT EMPNO, ENAME, SAL FROM EMP WHERE ENAME = 'FORD';
            String sql = " SELECT * FROM " + " users WHERE id LIKE '" + id + "' ";
            pstmt = conn.prepareStatement(sql); // 컴파일 후 실행
            rs = pstmt.executeQuery();
            while(rs.next()){
                userInfo.id = rs.getString("id");
                userInfo.password = rs.getString("password");
                userInfo.name = rs.getString("name");
                userInfo.birth = rs.getString("birth");
                userInfo.work = rs.getString("work");
                userInfo.married = rs.getString("married");
                userInfo.gender = rs.getString("gender");
            }
            //System.out.println("get_pwd: " +get_name);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        return userInfo;
    }

    public static ArrayList<UserInfo> users;

    //정보 수정 함수
    public UserInfo updateData(String id, String pwd1, String name,
                               String birth, String work, String married, String gender) throws SQLException {
        try{
            conn = getConnection();
            //UPDATE users SET id = 'jinnaa',password='123123' WHERE id = 'jina';
            String sql2 = " UPDATE users SET name='" + name + "', password = '" + pwd1
                    + "', birth='" + birth + "', work='" + work + "', married='" + married
                    + "', gender='" + gender + "' WHERE id ='" + id + "' ";
            
//            String sql2 = ("UPDATE users SET name= ?, password = ?, birth=?, work=?, married=?, gender=? WHERE id =? ");
//            pstmt = conn.prepareStatement(sql2); // 컴파일 후 실행
//            pstmt.setString(1, name);
//            pstmt.setString(2, pwd1);
//            pstmt.setString(3, birth);
//            pstmt.setString(4, work);
            
                pstmt = conn.prepareStatement(sql2);
                pstmt.executeUpdate();

            String sql3 = " SELECT * FROM " + " users WHERE id LIKE '" + id + "' ";
            pstmt = conn.prepareStatement(sql3);
             rs = pstmt.executeQuery();
             int i=0;
            while(rs.next()){
                userInfo.id = rs.getString("id");
                userInfo.password = rs.getString("password");
                userInfo.name = rs.getString("name");
                userInfo.birth = rs.getString("birth");
                userInfo.work = rs.getString("work");
                userInfo.married = rs.getString("married");
                userInfo.gender = rs.getString("gender");
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        return userInfo;
    }

    public UserInfo allData(){

        users =new ArrayList<>();

        int cnt=0;

        //UserInfo my_user = new UserInfo();
        try{
            conn = getConnection();
            //SELECT * FROM product;
            String sql = " SELECT * FROM users";
            pstmt = conn.prepareStatement(sql); // 컴파일 후 실행
            rs = pstmt.executeQuery();
            while(rs.next()){
                UserInfo my_user = new UserInfo();
                my_user.id = rs.getString("id");
                my_user.password = rs.getString("password");
                my_user.name = rs.getString("name");
                my_user.birth = rs.getString("birth");
                my_user.work = rs.getString("work");
                my_user.married = rs.getString("married");
                my_user.gender = rs.getString("gender");
                users.add(my_user);
                System.out.println("~~~~~id: " + my_user.id);
                cnt++;
            }
            //System.out.println("get_pwd: " +get_name);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        if(cnt == 0){
            return null;
        }
        else return userInfo;
    }

    public int selectFemalData() throws SQLException {
        int count=0;
        // Query문을 준비
        try{
            conn = getConnection();
            String sql = " SELECT * FROM users WHERE gender LIKE '여%' ";
            pstmt = conn.prepareStatement(sql); // 컴파일 후 시랳ㅇ
            rs = pstmt.executeQuery();

            while(rs.next()){
                String get_gen = rs.getString("gender");
                System.out.println("gender femal: " + get_gen);
                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        return count;
    }

    public int selectMaleData() throws SQLException {
        int count=0;
        // Query문을 준비
        try{
            conn = getConnection();
            String sql = " SELECT * FROM users WHERE gender LIKE '남%' ";
            pstmt = conn.prepareStatement(sql); // 컴파일 후 시랳ㅇ
            rs = pstmt.executeQuery();

            while(rs.next()){
                String get_gen = rs.getString("gender");
                System.out.println("gender male: " + get_gen);
                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        System.out.println("!!!male: "+count);
        return count;
    }
}
