package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser {

	public static void newUser(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, Object obj) {

		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement("insert into users (username, phone) values (?, ?);");
			outprint.println("Username:");
			String username = socscan.nextLine();
			myStmt.setString(1, username);
			outprint.println("Phone:");
			String phone = socscan.nextLine();
			myStmt.setString(2, phone);

			Pattern p1 = Pattern.compile("^[0-9]{10}$");
			Matcher m1 = p1.matcher(phone);
			if (!m1.matches()) {
				throw new Exception("Invlaid phone number");
			}
			int rows;
			synchronized (obj) {
				rows = myStmt.executeUpdate();
			}
			if (rows != 1)
				throw new Exception("Writing in the database failed!");
			outprint.println("New User added successfully");
			socscan.nextLine();

			myConn.commit();
		} catch (Exception e) {
			outprint.println(e.getMessage());
			socscan.nextLine();
		}
	}
}
