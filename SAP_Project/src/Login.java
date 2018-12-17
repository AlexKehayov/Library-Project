import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "alex", "prileptsi");
			PreparedStatement myStmt = myConn.prepareStatement("SELECT * from staff where username = ? and password=?;");
			Scanner scan = new Scanner(System.in);
			System.out.println("Input username:");
			String username = scan.nextLine();
			System.out.println("Input password:");
			String password = scan.nextLine();
			myStmt.setString(1, username);
			myStmt.setString(2, password);
			ResultSet myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				System.out.println("Welcome " + myRs.getString("username"));
			} else {
				System.out.println("Access denied");
			}
			scan.close();
			
		}catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
