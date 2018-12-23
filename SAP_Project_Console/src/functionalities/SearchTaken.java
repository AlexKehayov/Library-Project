package functionalities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SearchTaken {

	public static void searchTaken(Scanner scan, Connection myConn, Statement myStmt, ResultSet myRs, Object obj) {
		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.createStatement();

			System.out.println("Enter username:");
			String username = scan.nextLine();

			System.out.println("Enter title:");
			String title = scan.nextLine();
			synchronized (obj) {
				myRs = myStmt.executeQuery(
						"SELECT username, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
								+ " JOIN books ON books.id = bookstaken.book_id where username like '%" + username
								+ "%' and title like '%" + title + "%';");
			}
			while (myRs.next()) {
				System.out.println("Entry: " + myRs.getString("username") + " - " + myRs.getString("title") + " - "
						+ myRs.getString("author") + " - " + myRs.getString("deadline"));
			}

			myConn.commit();

			System.out.println("End of List");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
