package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import lib_items.Book;

public interface LibItemServices {
	
	void addNew(Connection conn, Scanner scan) throws SQLException, Exception;
	void search(Connection conn, Scanner scan, int i) throws SQLException, Exception;
	void delete(Connection conn, Scanner scan) throws SQLException, Exception;
	Book BookCreator(Scanner scan) throws Exception;

}
