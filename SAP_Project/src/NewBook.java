import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBook {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement(
					"insert into books (title, author, datepublished, quantity) values" + "(?, ?, ?, ?)");
			System.out.println("Title:");
			String title = scan.nextLine();
			myStmt.setString(1, title);
			System.out.println("Author:");
			String author = scan.nextLine();
			myStmt.setString(2, author);
			System.out.println("Date Published (yyyy-mm-dd):");
			String date = scan.nextLine();
			Pattern p1 = Pattern.compile("^[0-9]{4}+[-]{1}+[0-9]{2}+[-]{1}+[0-9]{2}$");
			Matcher m1 = p1.matcher(date);
			if (!m1.matches()) {
				throw new NewBookExceptionDate();
			}
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			Date tempdate = format.parse(date);
			
			if(!format.format(tempdate).equals(date)) throw new NewBookExceptionDate();

			myStmt.setString(3, date);
			System.out.println("Quantity:");
			int quantity = scan.nextInt();
			myStmt.setInt(4, quantity);
			
			int rows = myStmt.executeUpdate();
			if(rows!=1) throw new Exception("Writing in the database failed!");

			myConn.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(scan!=null) scan.close();
				try {
					if(myConn!=null) myConn.close();
					if(myStmt!=null) myStmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		}
	}

}
