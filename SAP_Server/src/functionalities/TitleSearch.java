package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TitleSearch {

	public static void titleSearch(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt,
			ResultSet myRs, Object obj) {

		try {

			outprint.println("Title:");
			String title = socscan.nextLine();
			myStmt = myConn.prepareStatement("select * from books where title like '%" + title + "%';");

			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
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
