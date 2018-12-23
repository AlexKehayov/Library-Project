package functionalities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ViewUsersTaken {

	public static void viewUsersTaken(Scanner scan, Connection myConn, Statement myStmt, ResultSet myRs, Object obj) {

		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.createStatement();
			synchronized (obj) {
				myRs = myStmt.executeQuery(
						"SELECT username, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
								+ " JOIN books ON books.id = bookstaken.book_id order by username, title;");
			}
			while (myRs.next()) {
				System.out.println(myRs.getString("username") + " - " + myRs.getString("title") + " - "
						+ myRs.getString("author") + " - " + myRs.getString("deadline"));
			}

			myConn.commit();
			System.out.println("End of List");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
