import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement(
					"insert into users (username, phone) values (?, ?);");
			System.out.println("Username:");
			String username = scan.nextLine();
			myStmt.setString(1, username);
			System.out.println("Phone:");
			String phone = scan.nextLine();
			myStmt.setString(2, phone);
			
			Pattern p1 = Pattern.compile("^[0-9]{10}$");
			Matcher m1 = p1.matcher(phone);
			if (!m1.matches()) {
				throw new Exception("Invlaid phone number");
			}
			
			int rows = myStmt.executeUpdate();
			if(rows!=1) throw new Exception("Writing in the database failed!");
			System.out.println("New User added successfully");

			myConn.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(scan!=null) scan.close();
				try {
					if(myConn!=null) myConn.close();
					if(myStmt!=null) myStmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		}

	}

}
