import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteBook {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Connection myConn=null;
		PreparedStatement myStmt=null;
		PreparedStatement myStmt2=null;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"alex", "prileptsi");
			
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement("select * from books where id=?");
			myStmt2 = myConn.prepareStatement("delete from books where id=?");
			
			System.out.println("Enter id of book to be deleted:");
			int id = scan.nextInt();
			myStmt.setInt(1, id);
			myStmt2.setInt(1, id);
			ResultSet myRs = myStmt.executeQuery();
			if(myRs.next()) {
				System.out.println("ID: " + myRs.getInt("id")+ "; Title: " + myRs.getString("title"));
			} else throw new Exception("No such book found!");
			
			System.out.println("To confirm press 1, else press anything else");
			int confirmation = scan.nextInt();
			
			if(confirmation==1) {
				
				int rows = myStmt2.executeUpdate();
				if(rows!=1) throw new Exception("Writing in the database failed!");
				else System.out.println("Deletion completed");
			} else System.out.println("Deletion rolled back");
			
			myConn.commit();
		} catch (Exception e) {
			System.out.println("Something went wrong, check your input...");
		}finally {
			if(scan!=null) scan.close();
				try {
					if(myConn!=null) myConn.close();
					if(myStmt!=null) myStmt.close();
					if(myStmt2!=null) myStmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		}

		
	}

}
