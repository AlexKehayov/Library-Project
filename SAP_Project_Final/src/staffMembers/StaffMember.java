package staffMembers;
import java.util.Scanner;

public class StaffMember extends AbsStaffMember{

	public final static String SQL_STAFFVERIFICATION_SELECT = "SELECT * from staff where username = ? and password=?;";
	
	private String username;
	private String password;


	public StaffMember(Scanner scan) {
		System.out.println("Enter username:");
		String username = scan.nextLine();
		System.out.println("Enter password:");
		String password = scan.nextLine();
		this.username = username;
		this.password = password;
	}

	public StaffMember(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
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