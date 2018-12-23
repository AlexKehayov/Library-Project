package functionalities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TitleSearch {

	public static void titleSearch(Scanner scan, Connection myConn, PreparedStatement myStmt,
			ResultSet myRs, Object obj) {

		try {

			System.out.println("Title:");
			String title = scan.nextLine();
			myStmt = myConn.prepareStatement("select * from books where title like '%" + title + "%';");

			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
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
