package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entries.BookEntry;
import lib_items.Book;
import users.User;

public interface EntryServices extends Commitable {
	
	static void give(Scanner scan, Connection conn) throws SQLException, Exception {
		BookEntry entry = new BookEntry(scan, conn);
		conn.setAutoCommit(false);
		int rows = 0;
		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_GIVE_UPDATE);
		stmt.setInt(1, entry.getBook().getId());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in database failed!");
		rows = 0;
		stmt = conn.prepareStatement(BookEntry.SQL_GIVE_INSERT);
		stmt.setInt(1, entry.getBook().getId());
		stmt.setInt(2, entry.getUser().getId());
		stmt.setString(3, entry.getDateGiven());
		stmt.setInt(4, entry.getTermMonths());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in database failed!");

		Commitable.confirm1(scan, conn);
	}

	static void search(Scanner scan, Connection conn) throws Exception {
		BookEntry entry = new BookEntry(scan);
		User user;
		Book book;
		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_SEARCH_SELECT);

		stmt.setString(1, "%" + entry.getUser().getUsername() + "%");
		stmt.setString(2, "%" + entry.getBook().getTitle() + "%");
		stmt.setString(3, "%" + entry.getUser().getPhone() + "%");
		stmt.setString(4, "%" + entry.getBook().getAuthor() + "%");

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), rs.getString("author"));

			entry = new BookEntry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
			System.out.println(entry);

		}
		System.out.println("End of List");
	}

	static void getBack(Scanner scan, Connection conn) throws SQLException, Exception {
		EntryServices.search(scan, conn);
		conn.setAutoCommit(false);
		System.out.println("Select entry to delete:");
		int id = Integer.parseInt(scan.nextLine());
		BookEntry entry;
		Book book;
		User user;
		int rows = 0;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_SELECT);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), null, 0);

			entry = new BookEntry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
		} else
			throw new Exception("No such entry in database!");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date deadline2 = format.parse(entry.getDateGiven());

		stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_DELETE);
		stmt.setInt(1, entry.getId());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deleting Entry Failed!");

		stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_UPDATE);
		stmt.setInt(1, entry.getBook().getId());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deleting Entry Failed!");

		if (deadline2.before(new Date()))
			System.out
					.println("DEADLINE EXPIRED ON " + entry.getDateGiven() + " TODAY IS " + format.format(new Date()));

		System.out.println("Are you sure you want to delete this entry?");
		System.out.println(entry);
		Commitable.confirm1(scan, conn);
	}

	static void viewExpired(Connection conn) throws SQLException {
		BookEntry entry;
		Book book;
		User user;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_VIEWEXPIRED_SELECT);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), rs.getString("author"));

			entry = new BookEntry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
			System.out.println(entry);
		}
		System.out.println("End of List");
	}

	static void viewUsersTaken(Connection conn) throws SQLException {
		BookEntry entry;
		Book book;
		User user;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_VIEWUSERSTAKEN_SELECT);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {

			user = new User(rs.getInt("users.id"), rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), null);

			entry = new BookEntry(0, book, user, rs.getString("deadline"));

			System.out.println(entry.getUser().getUsername() + "(id:" + entry.getUser().getId() + ") - "
					+ entry.getUser().getPhone() + " - " + entry.getBook().getTitle() + " - " + entry.getDateGiven());
		}

		System.out.println("End of List");
	}

}
