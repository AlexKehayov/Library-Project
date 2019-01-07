package entries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import interfaces.Commitable;
import interfaces.EntryServices;
import lib_items.Book;
import lib_items.BookLibItemServicesImpl;
import users.User;
import users.UserUserServiceImpl;

public class BookEntryServicesImpl extends BookEntry implements EntryServices{
	
	public void give(Scanner scan, Connection conn) throws SQLException, Exception {
		BookEntry entry = BookEntryCreator(scan, conn);
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

	public void search(Scanner scan, Connection conn) throws Exception {
		BookEntry entry = BookEntryCreator2(scan);
		BookEntry temp;
		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_SEARCH_SELECT);

		stmt.setString(1, "%" + entry.getUser().getUsername() + "%");
		stmt.setString(2, "%" + entry.getBook().getTitle() + "%");
		stmt.setString(3, "%" + entry.getUser().getPhone() + "%");
		stmt.setString(4, "%" + entry.getBook().getAuthor() + "%");

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			
			temp = new BookEntry(rs.getInt("bookstaken.id"), rs.getString("deadline"), 0, 
					0, rs.getString("title"), rs.getString("author"), null, 0,
					0, rs.getString("username"), rs.getString("phone"));
			
			System.out.println(temp);

		}
		System.out.println("End of List");
	}

	public void getBack(Scanner scan, Connection conn) throws SQLException, Exception {
		this.search(scan, conn);
		conn.setAutoCommit(false);
		System.out.println("Select entry to delete:");
		int id = Integer.parseInt(scan.nextLine());
		BookEntry temp;
		int rows = 0;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_SELECT);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			
			temp = new BookEntry(rs.getInt("bookstaken.id"), rs.getString("deadline"), 0, 
					rs.getInt("book_id"),  rs.getString("title"), rs.getString("author"), null, 0,
					0, rs.getString("username"), rs.getString("phone"));
		} else
			throw new Exception("No such entry in database!");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date deadline2 = format.parse(temp.getDateGiven());

		stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_DELETE);
		stmt.setInt(1, temp.getId());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deleting Entry Failed!");

		stmt = conn.prepareStatement(BookEntry.SQL_GETBACK_UPDATE);
		stmt.setInt(1, temp.getBook().getId());
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deleting Entry Failed!");

		if (deadline2.before(new Date()))
			System.out
					.println("DEADLINE EXPIRED ON " + temp.getDateGiven() + " TODAY IS " + format.format(new Date()));

		System.out.println("Are you sure you want to delete this entry?");
		System.out.println(temp);
		Commitable.confirm1(scan, conn);
	}

	public void viewExpired(Connection conn) throws Exception {
		BookEntry temp;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_VIEWEXPIRED_SELECT);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			
			temp = new BookEntry(rs.getInt("bookstaken.id"), rs.getString("deadline"), 0, 
					0, rs.getString("title"), rs.getString("author"), null, 0,
					0, rs.getString("username"), rs.getString("phone"));
			
			System.out.println(temp);
		}
		System.out.println("End of List");
	}

	public void viewUsersTaken(Connection conn) throws Exception {
		BookEntry temp;

		PreparedStatement stmt = conn.prepareStatement(BookEntry.SQL_VIEWUSERSTAKEN_SELECT);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {

			temp = new BookEntry(0, rs.getString("deadline"), 0, 
					0, rs.getString("title"), null, null, 0,
					rs.getInt("users.id"), rs.getString("username"), rs.getString("phone"));
			
			System.out.println(temp.getUser().getUsername() + "(id:" + temp.getUser().getId() + ") - "
					+ temp.getUser().getPhone() + " - " + temp.getBook().getTitle() + " - " + temp.getDateGiven());
		}

		System.out.println("End of List");
	}
	
	public BookEntry BookEntryCreator(Scanner scan, Connection conn) throws SQLException, Exception {
		Book book;
		User user;
		new BookLibItemServicesImpl().search(conn, scan, 1);
		scan.nextLine();
		System.out.println("Choose book's id:");
		int id = Integer.parseInt(scan.nextLine());

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from books where id=" + id + ";");
		if (rs.next()) {
			int quantity = rs.getInt("quantity");
			if(quantity<=0) throw new Exception("This book is out of stock!");
			book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
					rs.getString("datepublished"), quantity);
		} else
			throw new Exception("No such book found");

		new UserUserServiceImpl().search(conn, scan);
		scan.nextLine();
		System.out.println("Choose user's id:");
		id = Integer.parseInt(scan.nextLine());
		ResultSet rs2 = stmt.executeQuery("select * from users where id=" + id + ";");
		if (rs2.next()) {
			user = new User(rs2.getInt("id"), rs2.getString("username"), rs2.getString("phone"));
		} else
			throw new Exception("No such user found");

		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("Term (Months): ");
		int termMonths = Integer.parseInt(scan.nextLine());
		
		return new BookEntry(0, book, user, ft.format(date), termMonths);
	}
	
	public BookEntry BookEntryCreator2(Scanner scan) throws Exception {
		Book book = new Book(scan, 3);
		User user = new User(scan, 3);
		return new BookEntry(0, book, user, null, 0);
	}

}
