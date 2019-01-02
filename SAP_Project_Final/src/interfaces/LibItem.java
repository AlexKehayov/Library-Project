package interfaces;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface LibItem {
	public void addNew(Connection conn, Scanner scan) throws SQLException, Exception;
	public void search(Connection conn, Scanner scan, int i) throws SQLException, Exception;
	public void delete(Connection conn, Scanner scan) throws SQLException, Exception;

}
