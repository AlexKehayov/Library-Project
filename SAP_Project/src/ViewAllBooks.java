import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewAllBooks {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			myStmt = myConn.prepareStatement("select * from books;");

			myRs = myStmt.executeQuery();
			System.out.printf("%s - %s - %s - %s - %s\n", "ID", "Title", "Author", "Published", "Quantity");
			while (myRs.next()) {
				System.out.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author") + " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));
				
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
