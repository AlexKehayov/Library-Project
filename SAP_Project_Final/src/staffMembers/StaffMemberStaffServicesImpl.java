package staffMembers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import interfaces.StaffServices;

public class StaffMemberStaffServicesImpl implements StaffServices {
	public boolean staffVerification(Connection myConn, Scanner scan) throws SQLException, Exception {
		StaffMember sm1 = StaffMemberCreator(scan);
		PreparedStatement myStmt = myConn.prepareStatement(StaffMember.SQL_STAFFVERIFICATION_SELECT);
		myStmt.setString(1, sm1.getUsername());
		myStmt.setString(2, sm1.getPassword());

		ResultSet myRs = myStmt.executeQuery();
		StaffMember temp;
		if (myRs.next()) {
			temp = new StaffMember(myRs.getString("username"), myRs.getString("password"));
			System.out.println("Welcome, " + sm1.getUsername());
		} else
			return false;

		return sm1.equals(temp);
	}

	 public StaffMember StaffMemberCreator(Scanner scan) {
		System.out.println("Enter username:");
		String username = scan.nextLine();
		System.out.println("Enter password:");
		String password = scan.nextLine();
		return new StaffMember(username, password);
	}

}
