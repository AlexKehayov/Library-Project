package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import interfaces.EntryServices;
import interfaces.LibItemServices;
import interfaces.UserServices;
import lib_items.Book;
import users.User;

public class BookEntry extends AbsEntry implements EntryServices {
	public static final String SQL_GIVE_UPDATE = "update books set quantity=(quantity-1) where id=?;";
	public static final String SQL_GIVE_INSERT = "insert into bookstaken (book_id, user_id, deadline) values (?, ?, ? + interval ? month);";
	public static final String SQL_SEARCH_SELECT = "SELECT bookstaken.id, username, phone,  title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id JOIN books ON books.id = bookstaken.book_id where username like ? and title like ? and phone like ? and author like ?;";
	public static final String SQL_GETBACK_SELECT = "SELECT bookstaken.id, username, phone, book_id, title, author, deadline FROM bookstaken  JOIN users ON users.id = bookstaken.user_id JOIN books ON books.id = bookstaken.book_id where bookstaken.id=?;";
	public static final String SQL_GETBACK_DELETE = "delete from bookstaken where id=?;";
	public static final String SQL_GETBACK_UPDATE = "update books set quantity=(quantity+1) where id=?;";
	public static final String SQL_VIEWEXPIRED_SELECT = "SELECT bookstaken.id, username, phone, title, author, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id JOIN books ON books.id = bookstaken.book_id where (deadline<curdate()) order by username, title;";
	public static final String SQL_VIEWUSERSTAKEN_SELECT = "SELECT username, users.id, phone, title, deadline FROM bookstaken JOIN users ON users.id = bookstaken.user_id JOIN books ON books.id = bookstaken.book_id order by username, title;";

	private int id;
	private Book book;
	private User user;
	private String dateGiven;
	private int termMonths;

	public BookEntry() {

	}

	public BookEntry(int id, Book book, User user, String deadline) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.dateGiven = deadline;
	}

	public BookEntry(Scanner scan) throws Exception {
		this.book = new Book(scan, 3);
		this.user = new User(scan, 3);
	}

	public BookEntry(Scanner scan, Connection conn) throws SQLException, Exception {
		LibItemServices.search(conn, scan, 1);
		scan.nextLine();
		System.out.println("Choose book's id:");
		int id = Integer.parseInt(scan.nextLine());

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from books where id=" + id + ";");
		if (rs.next()) {
			int quantity = rs.getInt("quantity");
			if(quantity<=0) throw new Exception("This book is out of stock!");
			this.book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
					rs.getString("datepublished"), quantity);
		} else
			throw new Exception("No such book found");

		UserServices.search(conn, scan);
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
		this.dateGiven = ft.format(date);

		System.out.println("Term (Months): ");
		this.termMonths = Integer.parseInt(scan.nextLine());
	}

	public int getId() {
		return id;
	}

	public Book getBook() {
		return book;
	}

	public User getUser() {
		return user;
	}

	public String getDateGiven() {
		return dateGiven;
	}

	public int getTermMonths() {
		return termMonths;
	}

	@Override
	public String toString() {
		return "Entry: " + id + " - " + book.getTitle() + " (" + book.getAuthor() + ") - " + user.getUsername() + " ("
				+ user.getPhone() + ") - Deadline: " + dateGiven;
	}

}
