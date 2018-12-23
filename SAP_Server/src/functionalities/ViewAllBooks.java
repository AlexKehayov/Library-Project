package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ViewAllBooks {

	public static void viewAllBooks(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, ResultSet myRs,
			Object obj) {

		try {

			myStmt = myConn.prepareStatement("select * from books;");
			synchronized (obj) {
				myRs = myStmt.executeQuery();
			}
			outprint.printf("%s - %s - %s - %s - %s\n", "ID", "Title", "Author", "Published", "Quantity");
			socscan.nextLine();
			while (myRs.next()) {
				outprint.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author")
								+ " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));
				socscan.nextLine();
			}

		} catch (Exception e) {
			outprint.println(e.getMessage());
			socscan.nextLine();
		}
	}

}
