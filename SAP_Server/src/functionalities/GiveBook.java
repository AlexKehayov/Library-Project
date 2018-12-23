package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GiveBook {

	public static void giveBook(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, Statement myStmt2,
			ResultSet myRs, ResultSet myRs2, ResultSet myRs3, Object obj) {

		try {
			myConn.setAutoCommit(false);

			outprint.println("Title:");
			String title = socscan.nextLine();

			outprint.println("Author:");
			String author = socscan.nextLine();

			myStmt = myConn.prepareStatement(
					"select * from books where title like '%" + title + "%' and author like '%" + author + "%';");
			synchronized (obj) {
				myRs = myStmt.executeQuery();
			}
			while (myRs.next()) {
				outprint.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author")
								+ " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));
				socscan.nextLine();

			}
			outprint.println("End of Book List");
			socscan.nextLine();

			outprint.println("Enter id of book to be given:");
			int id = Integer.parseInt(socscan.nextLine());

			myStmt2 = myConn.createStatement();
			synchronized (obj) {
				myRs2 = myStmt2.executeQuery("select quantity from books where id=" + id + ";");
			}
			int quantity = 0;
			if (myRs2.next()) {
				quantity = myRs2.getInt("quantity");
			} else
				throw new Exception("Wrong input");

			int rows = 0;
			if (quantity > 0) {
				synchronized (obj) {
					rows = myStmt2.executeUpdate("update books set quantity=(quantity-1) where id=" + id + ";");
				}
			} else
				throw new Exception("Book out of Stock!");
			if (rows != 1)
				throw new Exception("Database error occured, please try again");

			//////////////////////////////////////
			outprint.println("Username:");
			String username = socscan.nextLine();

			outprint.println("Phone:");
			String phone = socscan.nextLine();

			synchronized (obj) {
				myRs3 = myStmt2.executeQuery("select * from users where username like '%" + username
						+ "%' and phone like '%" + phone + "%';");
			}
			while (myRs3.next()) {
				outprint.println(
						myRs3.getInt("id") + " - " + myRs3.getString("username") + " - " + myRs3.getString("phone"));
				socscan.nextLine();
			}
			outprint.println("End of User List\n");
			socscan.nextLine();

			outprint.println("Input id of the user who takes the book");
			int user_id = socscan.nextInt();
			////////////////////////////////////

			outprint.println("Term (Months): ");
			int months = socscan.nextInt();
			if (months < 1)
				throw new Exception("Illegal input for Term!");

			Date date = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			synchronized (obj) {
				rows = myStmt.executeUpdate("insert into bookstaken (book_id, user_id, deadline) values " + "(" + id
						+ ", " + user_id + ", '" + ft.format(date) + "' + interval " + months + " month);");
			}
			if (rows != 1)
				throw new Exception("Database error occured, please try again");

			myConn.commit();
		} catch (Exception e) {
			outprint.println("A problem has occured. Check your input.");
			socscan.nextLine();
		}

	}

}
