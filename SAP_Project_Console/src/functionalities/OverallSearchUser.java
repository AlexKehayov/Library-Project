package functionalities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class OverallSearchUser {

	public static void overallSearchUser(Scanner scan, Connection myConn, PreparedStatement myStmt, ResultSet myRs,
			Object obj) {

		try {
			System.out.println("Username:");
			String username = scan.nextLine();

			System.out.println("Phone:");
			String phone = scan.nextLine();

			myStmt = myConn.prepareStatement(
					"select * from users where username like '%" + username + "%' and phone like '%" + phone + "%';");
			synchronized (obj) {
				myRs = myStmt.executeQuery();
			}
			while (myRs.next()) {
				System.out.println(myRs.getString("username") + " - " + myRs.getString("phone"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
