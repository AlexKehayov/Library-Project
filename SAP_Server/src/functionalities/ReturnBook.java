package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ReturnBook {

	public static void returnBook(Scanner socscan, PrintStream outprint, Connection myConn, Statement myStmt, ResultSet myRs, ResultSet myRs2,
			ResultSet myRs3, Object obj) {

		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.createStatement();

			outprint.println("Enter username:");
			String username = socscan.nextLine();

			outprint.println("Enter title:");
			String title = socscan.nextLine();

			synchronized (obj) {
				myRs = myStmt.executeQuery(
						"SELECT bookstaken.id, username, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
								+ " JOIN books ON books.id = bookstaken.book_id where username like '%" + username
								+ "%' and title like '%" + title + "%';");
			}
			while (myRs.next()) {
				outprint.println("Entry " + myRs.getInt("bookstaken.id") + " - " + myRs.getString("username") + " - "
						+ myRs.getString("title") + " - " + myRs.getString("author") + " - "
						+ myRs.getString("deadline"));
				socscan.nextLine();
			}

			outprint.println("Choose entry:");
			int entry = Integer.parseInt(socscan.nextLine());
			synchronized (obj) {
				myRs2 = myStmt.executeQuery("SELECT book_id, deadline FROM bookstaken where id=" + entry + ";");
			}
			int book_id = 0;
			String deadline = null;

			if (myRs2.next()) {
				book_id = myRs2.getInt("book_id");
				deadline = myRs2.getString("deadline");
			} else
				throw new Exception("No such entry!");

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date deadline2 = format.parse(deadline);

			if (deadline2.before(new Date()))
				outprint.println("DEADLINE EXPIRED ON " + deadline + " TODAY IS " + format.format(new Date()));
			socscan.nextLine();
			int rows;
			synchronized (obj) {
				rows = myStmt.executeUpdate("delete from bookstaken where id = " + entry + ";");
			}
			if (rows != 1)
				throw new Exception("Deleting Entry Failed!");
			synchronized (obj) {
				rows = myStmt.executeUpdate("update books set quantity=(quantity+1) where id=" + book_id + ";");
			}
			if (rows != 1)
				throw new Exception("Adding book to Stock Failed!");

			myConn.commit();

			outprint.println("Book returned Successfully");
			socscan.nextLine();

		} catch (Exception e) {
			outprint.println(e.getMessage());
			socscan.nextLine();
		}

	}

}
