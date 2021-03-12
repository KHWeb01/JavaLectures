import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDBTest {
	public static Connection makeConnection () {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "web";
		String password = "web";
		Connection con = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공");
			
			con = DriverManager.getConnection(url, id, password);
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없다.");
		} catch (SQLException e) {
			System.out.println("연결 실패!");
		}
		
		return con;
	}
	
	private static void addBoard(String name, String pw, 
			String title, String email, String year, String contents) {
		
		Connection con = makeConnection();
		
		try {
			Statement stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			
			sb.append("insert into jsp_board (no, name, passwd, "
					+ "title, email, regdate, content) values ("
					+ "jsp_board_seq.nextval, '" + name + "', "
					+ "'" + pw + "', " + "'" + title + "', "
					+ "'" + email + "', " + "'" + year + "', "
					+ "'" + contents + "'" + ")");
			
			System.out.println(sb);
			
			int i = stmt.executeUpdate(sb.toString());
			
			if (i == 1) {
				System.out.println("입력 성공");
			} else {
				System.out.println("입력 실패");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		addBoard("안녕", "유물", "환장", "test@test.com", "2021/03/12", "유물 만세");
		
		Connection con = makeConnection();
		System.out.println("처리 성공!");
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select no, name from jsp_board");
		
		while(rs.next()) {
			int no = rs.getInt("no");
			String name = rs.getString("name");
			
			System.out.println("번호: " + no + ", 이름: "  + name);
		}
	}
}
