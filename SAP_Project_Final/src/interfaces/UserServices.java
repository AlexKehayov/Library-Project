package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import users.User;

public interface UserServices {
	static void addNewUser(Connection conn, Scanner scan) throws SQLException, Exception {
		User user = new User(scan);
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

	static void search(Connection conn, Scanner scan) throws SQLException, Exception {
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

}
