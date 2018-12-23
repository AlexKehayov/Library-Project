package functionalities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class DeleteBook {

	public static void deleteBook(Scanner socscan, PrintStream outprint, Connection myConn, PreparedStatement myStmt, 
			PreparedStatement myStmt2, ResultSet myRs, Object obj) {

		try {
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareStatement("select * from books where id=?");
			myStmt2 = myConn.prepareStatement("delete from books where id=?");
			
			outprint.println("Enter id of book to be deleted:");
			int id = socscan.nextInt();
			myStmt.setInt(1, id);
			myStmt2.setInt(1, id);
			synchronized(obj) {
			myRs = myStmt.executeQuery();
			}
			if(myRs.next()) {
				outprint.println("ID: " + myRs.getInt("id")+ "; Title: " + myRs.getString("title"));
				socscan.nextLine();
			} else throw new Exception("No such book found!");
			
			outprint.println("To confirm press 1, else press anything else");
			int confirmation = socscan.nextInt();
			
			if(confirmation==1) {
				int rows;
				synchronized(obj) {
				rows = myStmt2.executeUpdate();
				}
				if(rows!=1) throw new Exception("Writing in the database failed!");
				else {
					outprint.println("Deletion completed");
					socscan.nextLine();
				}
			} else outprint.println("Deletion rolled back");
			
			myConn.commit();
		} catch (Exception e) {
			outprint.println(e.getMessage() + ": Something went wrong, check your input...");
			socscan.nextLine();
			e.printStackTrace();
		}
		
	}

}
