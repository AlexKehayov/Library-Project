package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class OverallSearchUser {

	public static void overallSearchUser(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, ResultSet myRs,
			Object obj) {

		try {
			outprint.println("Username:");
			String username = socscan.nextLine();

			outprint.println("Phone:");
			String phone = socscan.nextLine();

			myStmt = myConn.prepareStatement(
					"select * from users where username like '%" + username + "%' and phone like '%" + phone + "%';");
			synchronized (obj) {
				myRs = myStmt.executeQuery();
			}
			while (myRs.next()) {
				outprint.println(myRs.getString("username") + " - " + myRs.getString("phone"));
				socscan.nextLine();
			}

		} catch (Exception e) {
			outprint.println(e.getMessage());
			socscan.nextLine();
		}
	}
}
