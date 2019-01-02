import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.Commitable;

public class User implements Commitable{
	private int id;
	private String username;
	private String phone;

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public User(int id, String username, String phone) {
		this.id = id;
		this.username = username;
		this.phone = phone;
	}

	public User(String username, String phone) {
		this.username = username;
		this.phone = phone;
	}

	public User(Scanner scan) throws Exception {
		System.out.println("Username:");
		String username = scan.nextLine();
		System.out.println("Phone:");
		String phone = scan.nextLine();

		Pattern p1 = Pattern.compile("^[0-9]{10}$");
		Matcher m1 = p1.matcher(phone);
		if (!m1.matches()) {
			throw new Exception("Invlaid phone number");
		}
		this.username = username;
		this.phone = phone;
	}

	public User(Scanner scan, int i) throws Exception {
		System.out.println("Username:");
		String username = scan.nextLine();
		System.out.println("Phone:");
		String phone = scan.nextLine();

		this.username = username;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", phone=" + phone + "]";
	}

	public void addNewUser(Connection conn, Scanner scan) throws SQLException, Exception {
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement("insert into users (username, phone) values (?, ?);");

		stmt.setString(1, this.getUsername());
		stmt.setString(2, this.getPhone());

		int rows;
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in the database failed!");

		System.out.println("Username: " + this.getUsername() + ", Phone: " + this.getPhone() + ";");

		confirm(scan, conn);
	}

	public void search(Connection conn, Scanner scan) throws SQLException, Exception {
		Statement myStmt = conn.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from users where username like '%" + this.getUsername() + "%'"
				+ " and phone like '%" + this.getPhone() + "%';");

		User temp;
		while (myRs.next()) {
			temp = new User(myRs.getInt("id"), myRs.getString("username"), myRs.getString("phone"));
			System.out.println(temp.toString());
		}

	}
}
