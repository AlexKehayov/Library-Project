package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.Commitable;
import interfaces.UserServices;

public class UserUserServiceImpl implements UserServices {
	public void addNewUser(Connection conn, Scanner scan) throws SQLException, Exception {
		User user = this.UserCreator(scan);
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement(User.SQL_ADDNEWUSER_INSERT);

		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPhone());

		int rows;
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in the database failed!");

		System.out.println("Username: " + user.getUsername() + ", Phone: " + user.getPhone() + ";");

		Commitable.confirm1(scan, conn);
	}

	public void search(Connection conn, Scanner scan) throws SQLException, Exception {
		User user = new User(scan, 1);
		PreparedStatement stmt = conn.prepareStatement(User.SQL_SEARCH_SELECT);
		stmt.setString(1, "%" + user.getUsername() + "%");
		stmt.setString(2, "%" + user.getPhone() + "%");
		ResultSet myRs = stmt.executeQuery();

		User temp;
		while (myRs.next()) {
			temp = new User(myRs.getInt("id"), myRs.getString("username"), myRs.getString("phone"));
			System.out.println(temp.toString());
		}
	}

	public User UserCreator(Scanner scan) throws Exception {
		System.out.println("Username:");
		String username = scan.nextLine();
		System.out.println("Phone:");
		String phone = scan.nextLine();

		Pattern p1 = Pattern.compile("^[0-9]{10}$");
		Matcher m1 = p1.matcher(phone);
		if (!m1.matches()) {
			throw new Exception("Invlaid phone number");
		}

		return new User(username, phone);
	}
}
