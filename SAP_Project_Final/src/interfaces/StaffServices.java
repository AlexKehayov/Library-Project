package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import staffMembers.StaffMember;

public interface StaffServices {
	
	boolean staffVerification(Connection myConn, Scanner scan) throws SQLException, Exception;
	public StaffMember StaffMemberCreator(Scanner scan);
	
}
