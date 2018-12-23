package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBook {

	public static void newBook(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, Object obj) {
		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement(
					"insert into books (title, author, datepublished, quantity) values (?, ?, ?, ?)");
			outprint.println("Title:");
			String title = socscan.nextLine();
			myStmt.setString(1, title);
			outprint.println("Author:");
			String author = socscan.nextLine();
			myStmt.setString(2, author);
			outprint.println("Date Published (yyyy-mm-dd):");
			String date = socscan.nextLine();
			Pattern p1 = Pattern.compile("^[0-9]{4}+[-]{1}+[0-9]{2}+[-]{1}+[0-9]{2}$");
			Matcher m1 = p1.matcher(date);
			if (!m1.matches()) {
				throw new NewBookExceptionDate();
			}
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			Date tempdate = format.parse(date);
			
			if(!format.format(tempdate).equals(date)) throw new NewBookExceptionDate();

			myStmt.setString(3, date);
			outprint.println("Quantity:");
			int quantity = socscan.nextInt();
			myStmt.setInt(4, quantity);
			int rows;
			synchronized(obj) {
			rows = myStmt.executeUpdate();
			}
			if(rows!=1) throw new Exception("Writing in the database failed!");

			myConn.commit();
		} catch (Exception e) {
			outprint.println(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

}
