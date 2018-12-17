import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		
		// for bookstaken!!!
		/*
		 * myStmt.executeUpdate("insert into bookstaken (book_id, user_id, deadline) values " + 
							"(2, 2, '" + ft.format(date) +"' + interval 1 month);");
							
							
		   	ResultSet myRs = 
					myStmt.executeQuery("SELECT username, phone, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id" + 
							" JOIN books ON books.id = bookstaken.book_id;");
			while(myRs.next()) {
				System.out.println(myRs.getString("username") + " " + myRs.getString("phone") 
				+ " " + myRs.getString("title") + " " + myRs.getString("author") + " " + myRs.getString("deadline"));
			}
		 *
		 * */
		 
		
		//===========================================================
				Date date = new Date();
				System.out.println(date);
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

					      System.out.println("Current Date: " + ft.format(date));

					      
		
//		try {
//			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "alex", "prileptsi");
//			Statement myStmt = myConn.createStatement();
//			ResultSet myRs = 
//					myStmt.executeQuery("SELECT username, phone, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id" + 
//							" JOIN books ON books.id = bookstaken.book_id;");
//			while(myRs.next()) {
//				System.out.println(myRs.getString("username") + " " + myRs.getString("phone") 
//				+ " " + myRs.getString("title") + " " + myRs.getString("author") + " " + myRs.getString("deadline"));
//			}
			
		
		
		
		
		
		
		
	}

}
