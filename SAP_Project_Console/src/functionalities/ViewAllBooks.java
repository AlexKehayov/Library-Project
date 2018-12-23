package functionalities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ViewAllBooks {

	public static void viewAllBooks(Scanner scan, Connection myConn, PreparedStatement myStmt, ResultSet myRs,
			Object obj) {

		try {

			myStmt = myConn.prepareStatement("select * from books;");
			synchronized (obj) {
				myRs = myStmt.executeQuery();
			}
			System.out.printf("%s - %s - %s - %s - %s\n", "ID", "Title", "Author", "Published", "Quantity");
			while (myRs.next()) {
				System.out
						.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author")
								+ " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
