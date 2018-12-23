package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

	public static void login(Connection myConn, PreparedStatement myStmt, 
			Scanner socscan, PrintStream outprint, ResultSet myRs, Object obj) throws AccessExc {
		
		
		try {
			
			myStmt = myConn.prepareStatement("SELECT * from staff where username = ? and password=?;");
			
			String username = socscan.nextLine();
			outprint.println("Input password:");
			String password = socscan.nextLine();
			myStmt.setString(1, username);
			myStmt.setString(2, password);
			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
			if(myRs.next()) {
				outprint.println("Welcome " + myRs.getString("username"));
				socscan.nextLine();
			} else {
				outprint.println("Access denied");
				throw new AccessExc();
			}
			
		}catch (SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
