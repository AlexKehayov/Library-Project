import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ReturnBook {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		ResultSet myRs2 = null;
		ResultSet myRs3 = null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			myConn.setAutoCommit(false);

			myStmt = myConn.createStatement();

			System.out.println("Enter username:");
			String username = scan.nextLine();

			System.out.println("Enter title:");
			String title = scan.nextLine();

			myRs = myStmt.executeQuery(
					"SELECT bookstaken.id, username, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
							+ " JOIN books ON books.id = bookstaken.book_id where username like '%" + username
							+ "%' and title like '%" + title + "%';");
			
			while (myRs.next()) {
				System.out.println("Entry " + myRs.getInt("bookstaken.id") + " - " + myRs.getString("username") + " - "
						+ myRs.getString("title") + " - " + myRs.getString("author") + " - "
						+ myRs.getString("deadline"));
			}

			System.out.println("Choose entry:");
			int entry = Integer.parseInt(scan.nextLine());

			myRs2 = myStmt.executeQuery("SELECT book_id, deadline FROM bookstaken where id=" + entry + ";");
			
			int book_id = 0;
			String deadline=null;
			
			if (myRs2.next()) {
				book_id = myRs2.getInt("book_id");
				deadline = myRs2.getString("deadline");
			}
			else throw new Exception("No such entry!");
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date deadline2 = format.parse(deadline);
							
			if(deadline2.before(new Date())) System.out.println("DEADLINE EXPIRED ON " + deadline + " TODAY IS " + format.format(new Date()));
			
			int rows = myStmt.executeUpdate("delete from bookstaken where id = " + entry +";");
			if(rows!=1) throw new Exception("Deleting Entry Failed!");
			
			rows = myStmt.executeUpdate("update books set quantity=(quantity+1) where id=" + book_id + ";");
			if(rows!=1) throw new Exception("Adding book to Stock Failed!");

			myConn.commit();
			
			System.out.println("Book returned Successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (scan != null)
				scan.close();
			try {
				if (myRs != null)
					myRs.close();
				if (myRs2 != null)
					myRs2.close();
				if (myRs3 != null)
					myRs3.close();
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
