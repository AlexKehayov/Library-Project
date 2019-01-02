package interfaces;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface Commitable {
	default void confirm(Scanner scan, Connection conn) throws SQLException {
		System.out.println("To commit press 1");
		if (scan.nextInt() == 1) {
			conn.commit();
			System.out.println("Commit Successful");
		} else {
			conn.rollback();
			System.out.println("Commit Rolled Back");
		}
	}

}
