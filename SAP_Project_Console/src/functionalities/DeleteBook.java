package functionalities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class DeleteBook {

	public static void deleteBook(Scanner scan, Connection myConn, PreparedStatement myStmt, 
			PreparedStatement myStmt2, ResultSet myRs, Object obj) {

		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement("select * from books where id=?");
			myStmt2 = myConn.prepareStatement("delete from books where id=?");
			
			System.out.println("Enter id of book to be deleted:");
			int id = scan.nextInt();
			myStmt.setInt(1, id);
			myStmt2.setInt(1, id);
			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
			if(myRs.next()) {
				System.out.println("ID: " + myRs.getInt("id")+ "; Title: " + myRs.getString("title"));
			} else throw new Exception("No such book found!");
			
			System.out.println("To confirm press 1, else press anything else");
			int confirmation = scan.nextInt();
			
			if(confirmation==1) {
				int rows;
				synchronized(obj) {
				rows = myStmt2.executeUpdate();
				}
				if(rows!=1) throw new Exception("Writing in the database failed!");
				else System.out.println("Deletion completed");
			} else System.out.println("Deletion rolled back");
			
			myConn.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Something went wrong, check your input...");
		}
		
	}

}
