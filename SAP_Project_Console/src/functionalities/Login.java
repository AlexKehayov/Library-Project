package functionalities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

	public static void login(Connection myConn, PreparedStatement myStmt, Scanner scan, ResultSet myRs, Object obj) throws AccessExc {
		try {
			myStmt = myConn.prepareStatement("SELECT * from staff where username = ? and password=?;");
			
			System.out.println("Input username:");
			String username = scan.nextLine();
			System.out.println("Input password:");
			String password = scan.nextLine();
			myStmt.setString(1, username);
			myStmt.setString(2, password);
			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
			if(myRs.next()) {
				System.out.println("Welcome " + myRs.getString("username"));
			} else {
				System.out.println("Access denied");
				throw new AccessExc();
			}
			
		}catch (SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
