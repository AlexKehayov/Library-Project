import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GiveBook {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		Statement myStmt2 = null;
		ResultSet myRs = null;
		ResultSet myRs2 = null;
		ResultSet myRs3 = null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			myConn.setAutoCommit(false);

			System.out.println("Title:");
			String title = scan.nextLine();

			System.out.println("Author:");
			String author = scan.nextLine();

			myStmt = myConn.prepareStatement(
					"select * from books where title like '%" + title + "%' and author like '%" + author + "%';");

			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				System.out
						.println(myRs.getInt("id") + " - " + myRs.getString("title") + " - " + myRs.getString("author")
								+ " - " + myRs.getString("datePublished") + " - " + myRs.getInt("quantity"));

			}
			System.out.println("End of Book List\n");

			System.out.println("Enter id of book to be given:");
			int id = Integer.parseInt(scan.nextLine());
			
			myStmt2 = myConn.createStatement();

			myRs2 = myStmt2.executeQuery("select quantity from books where id=" + id + ";");
			int quantity = 0;
			if (myRs2.next()) {
				quantity = myRs2.getInt("quantity");
			} else
				throw new Exception("Wrong input");

			int rows = 0;
			if (quantity > 0) {
				rows = myStmt2.executeUpdate("update books set quantity=(quantity-1) where id=" + id + ";");
			} else throw new Exception("Book out of Stock!");
			if(rows!=1) throw new Exception("Database error occured, please try again");
			
			//////////////////////////////////////
			System.out.println("Username:");
			String username = scan.nextLine();
			
			System.out.println("Phone:");
			String phone = scan.nextLine();
			
			myRs3 = myStmt2.executeQuery("select * from users where username like '%" + username + "%' and phone like '%" + phone + "%';");
			while (myRs3.next()) {
				System.out.println(myRs3.getInt("id") + " - " + myRs3.getString("username") + " - " + myRs3.getString("phone"));
			}
			System.out.println("End of User List\n");
			
			System.out.println("Input id of the user who takes the book");
			int user_id=scan.nextInt();
			////////////////////////////////////
			
			System.out.println("Term (Months): ");
			int months = scan.nextInt();
			if(months<1) throw new Exception("Illegal input for Term!");
			
			Date date = new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			rows = myStmt.executeUpdate("insert into bookstaken (book_id, user_id, deadline) values " + 
						"(" + id + ", " + user_id + ", '" + ft.format(date) +"' + interval " + months + " month);");
			if(rows!=1) throw new Exception("Database error occured, please try again");

			myConn.commit();
		} catch (Exception e) {
			System.out.println("A problem has occured. Check your input.");
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
				if (myStmt2 != null)
					myStmt2.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
