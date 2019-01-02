package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface LibService {
	public void give(Scanner scan, Connection conn) throws SQLException, Exception;
	public void search(Scanner scan, Connection conn) throws Exception;
	public void getBack(Scanner scan, Connection conn) throws SQLException, Exception;
	public void viewExpired(Connection conn) throws SQLException;
	public void viewUsersTaken(Connection conn) throws SQLException;
	
}
