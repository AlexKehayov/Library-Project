package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import staffMembers.StaffMember;

public interface StaffServices {
	static boolean staffVerification(Connection myConn, Scanner scan) throws SQLException, Exception {
		StaffMember sm1 = new StaffMember(scan);
		PreparedStatement myStmt = myConn.prepareStatement(StaffMember.SQL_STAFFVERIFICATION_SELECT);
		myStmt.setString(1, sm1.getUsername());
		myStmt.setString(2, sm1.getPassword());

		ResultSet myRs = myStmt.executeQuery();
		StaffMember temp;
		if (myRs.next()) {
			String username = myRs.getString("username");
			String password = myRs.getString("password");
			temp = new StaffMember(username, password);
			System.out.println("Welcome, " + sm1.getUsername());
		} else
			return false;

		return sm1.equals(temp);
	}

	boolean equals(Object obj);

	int hashCode();
}
