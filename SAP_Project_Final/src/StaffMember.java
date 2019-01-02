import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import interfaces.Staff;

public class StaffMember implements Staff{
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}
	
	private String getPassword() {
		return password;
	}

	public StaffMember(Scanner scan) {
		System.out.println("Enter username:");
		String username = scan.nextLine();
		System.out.println("Enter password:");
		String password = scan.nextLine();
		this.username = username;
		this.password = password;
	}

	private StaffMember(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public boolean staffVerification(Connection myConn) throws SQLException {

		PreparedStatement myStmt = myConn.prepareStatement("SELECT * from staff where username = ? and password=?;");
		myStmt.setString(1, this.getUsername());
		myStmt.setString(2, this.getPassword());

		ResultSet myRs = myStmt.executeQuery();
		StaffMember temp;
		if (myRs.next()) {
			String username = myRs.getString("username");
			String password = myRs.getString("password");
			temp = new StaffMember(username, password);
		}else return false;
		
		return this.equals(temp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StaffMember other = (StaffMember) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
