package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import users.User;

public interface UserServices {
	
	void addNewUser(Connection conn, Scanner scan) throws SQLException, Exception;
	void search(Connection conn, Scanner scan) throws SQLException, Exception;
	User UserCreator(Scanner scan) throws Exception;
	
}
