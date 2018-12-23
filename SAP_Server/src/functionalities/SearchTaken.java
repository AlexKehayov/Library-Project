package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SearchTaken {

	public static void searchTaken(Scanner socscan, PrintStream outprint, Connection myConn, Statement myStmt, ResultSet myRs, Object obj) {
		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.createStatement();

			outprint.println("Enter username:");
			String username = socscan.nextLine();

			outprint.println("Enter title:");
			String title = socscan.nextLine();
			synchronized (obj) {
				myRs = myStmt.executeQuery(
						"SELECT username, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
								+ " JOIN books ON books.id = bookstaken.book_id where username like '%" + username
								+ "%' and title like '%" + title + "%';");
			}
			while (myRs.next()) {
				outprint.println("Entry: " + myRs.getString("username") + " - " + myRs.getString("title") + " - "
						+ myRs.getString("author") + " - " + myRs.getString("deadline"));
				socscan.nextLine();
			}

			myConn.commit();

			outprint.println("End of List");
			socscan.nextLine();
		} catch (Exception e) {
			outprint.println(e.getMessage());
			socscan.nextLine();
		}
	}
}
