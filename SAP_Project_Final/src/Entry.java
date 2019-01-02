import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import interfaces.Commitable;
import interfaces.LibService;

public class Entry implements LibService, Commitable{
	private int id;
	private Book book;
	private User user;
	private String deadline;

	public int getId() {
		return id;
	}

	public Book getBook() {
		return book;
	}

	public User getUser() {
		return user;
	}

	public String getDeadline() {
		return deadline;
	}
	
	public Entry() {
		
	}

	public Entry(Scanner scan, Connection conn) throws SQLException, Exception {
		Book tempBook = new Book(scan, 1);
		tempBook.search(conn, scan, 1);
		scan.nextLine();
		System.out.println("Choose book's id:");
		int id = Integer.parseInt(scan.nextLine());

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from books where id=" + id + ";");
		if (rs.next()) {
			this.book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
					rs.getString("datepublished"), rs.getInt("quantity"));
		} else
			throw new Exception("No such book found");

		User tempUser = new User(scan, 1);
		tempUser.search(conn, scan);
		scan.nextLine();
		System.out.println("Choose user's id:");
		id = Integer.parseInt(scan.nextLine());
		ResultSet rs2 = stmt.executeQuery("select * from users where id=" + id + ";");
		if (rs2.next()) {
			this.user = new User(rs2.getInt("id"), rs2.getString("username"), rs2.getString("phone"));
		} else
			throw new Exception("No such user found");

		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		this.deadline = ft.format(date);
	}

	public Entry(int id, Book book, User user, String deadline) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.deadline = deadline;
	}
	
	public Entry(Scanner scan) throws Exception {
		this.book = new Book(scan, 3);
		this.user = new User(scan, 3);
	}

	public void give(Scanner scan, Connection conn) throws SQLException, Exception {
		conn.setAutoCommit(false);
		int rows = 0;
		Statement stmt = conn.createStatement();
		rows = stmt.executeUpdate("update books set quantity=(quantity-1) where id=" + this.book.getId() + ";");
		if (rows != 1)
			throw new Exception("Writing in database failed!");
		rows = 0;
		System.out.println("Term (Months): ");
		int months = scan.nextInt();
		rows = stmt.executeUpdate(
				"insert into bookstaken (book_id, user_id, deadline) values " + "(" + this.book.getId() + ", "
						+ this.user.getId() + ", '" + this.getDeadline() + "' + interval " + months + " month);");
		if (rows != 1)
			throw new Exception("Writing in database failed!");

		confirm(scan, conn);
	}

	public void search(Scanner scan, Connection conn) throws Exception {
		
		Entry entry;
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(
				"SELECT bookstaken.id, username, phone,  title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
						+ " JOIN books ON books.id = bookstaken.book_id where username like '%" + this.user.getUsername()
						+ "%' and title like '%" + this.book.getTitle() + "%' and phone like '%" + this.user.getPhone() + "%';");
		while (rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), rs.getString("author"));

			entry = new Entry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
			System.out.println(entry);

		}
		System.out.println("End of List");

	}

	public void getBack(Scanner scan, Connection conn) throws SQLException, Exception {
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		System.out.println("Select entry to delete:");
		int id = Integer.parseInt(scan.nextLine());
		Entry entry;
		Book book;
		User user;
		int rows = 0;
		
		ResultSet rs = stmt.executeQuery("SELECT bookstaken.id, username, phone, book_id, title, author, deadline"
				+ " FROM bookstaken  JOIN users ON users.id = bookstaken.user_id "
				+ "JOIN books ON books.id = bookstaken.book_id where bookstaken.id=" + id + ";");
		if(rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), null, 0);

			entry = new Entry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
		}else throw new Exception("No such entry in database!");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date deadline2 = format.parse(entry.getDeadline());
		
		rows = stmt.executeUpdate("delete from bookstaken where id=" + entry.getId() + ";");
		if(rows!=1) throw new Exception("Deleting Entry Failed!");
		
		rows = stmt.executeUpdate("update books set quantity=(quantity+1) where id=" + entry.getBook().getId() + ";");
		if(rows!=1) throw new Exception("Deleting Entry Failed!");

		if (deadline2.before(new Date()))
			System.out.println("DEADLINE EXPIRED ON " + entry.getDeadline() + " TODAY IS " + format.format(new Date()));
		
		System.out.println("Are you sure you want to delete this entry?");
		System.out.println(entry);
		
		System.out.println("To commit press 1");
		if (scan.nextInt() == 1) {
			conn.commit();
			System.out.println("Entry Deletion Successful");
		} else {
			conn.rollback();
			System.out.println("Entry Deletion Rolled Back");
		}
		
	}
	
	public void viewExpired(Connection conn) throws SQLException {
		Entry entry;
		Book book;
		User user;
		
		Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT bookstaken.id, username, phone, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
					+ " JOIN books ON books.id = bookstaken.book_id where (deadline<curdate()) order by username, title;");
		while (rs.next()) {
			user = new User(rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), rs.getString("author"));

			entry = new Entry(rs.getInt("bookstaken.id"), book, user, rs.getString("deadline"));
			System.out.println(entry);
		}
		System.out.println("End of List");
	}
	
	public void viewUsersTaken(Connection conn) throws SQLException{
		Entry entry;
		Book book;
		User user;
		
		Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT username, users.id, phone, title, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id"
							+ " JOIN books ON books.id = bookstaken.book_id order by username, title;");
		while (rs.next()) {
		
			user = new User(rs.getInt("users.id"), rs.getString("username"), rs.getString("phone"));
			book = new Book(rs.getString("title"), null);

			entry = new Entry(0, book, user, rs.getString("deadline"));
			
			System.out.println(entry.getUser().getUsername() + "(id:" + entry.getUser().getId() + ") - " + entry.getUser().getPhone() 
					+ " - " + entry.getBook().getTitle() + " - " + entry.getDeadline());
		}

		System.out.println("End of List");
	}

	@Override
	public String toString() {
		return "Entry: " + id + " - " + book.getTitle() + " (" + book.getAuthor() + ") - " + user.getUsername() + " ("
				+ user.getPhone() + ") - Deadline: " + deadline;
	}
}
