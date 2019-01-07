package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import entries.BookEntry;

public interface EntryServices extends Commitable {
	
	void give(Scanner scan, Connection conn) throws SQLException, Exception;
	void search(Scanner scan, Connection conn) throws Exception;
	void getBack(Scanner scan, Connection conn) throws SQLException, Exception;
	void viewExpired(Connection conn) throws SQLException, Exception;
	void viewUsersTaken(Connection conn) throws SQLException, Exception;
	public BookEntry BookEntryCreator(Scanner scan, Connection conn) throws SQLException, Exception;
	
}
