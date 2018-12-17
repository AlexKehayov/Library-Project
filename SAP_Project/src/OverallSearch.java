import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OverallSearch {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");

			System.out.println("Title:");
			String title = scan.nextLine();
			
			System.out.println("Author:");
			String author = scan.nextLine();
			
			myStmt = myConn.prepareStatement("select * from books where title like '%" + title + "%' and author like '%" + author + "%';");

			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				System.out
						.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author")
								+ " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (scan != null)
				scan.close();
			try {
				if (myRs != null)
					myRs.close();
				if (myConn != null)
					myConn.close();
				if (myStmt != null)
					myStmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}


	}

}