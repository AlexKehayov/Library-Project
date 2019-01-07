package lib_items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.Commitable;
import interfaces.LibItemServices;

public class BookLibItemServicesImpl extends Book implements LibItemServices{
	public void addNew(Connection conn, Scanner scan) throws SQLException, Exception {
		conn.setAutoCommit(false);

		Book book = BookCreator(scan);

		PreparedStatement stmt = conn.prepareStatement(Book.SQL_ADDNEW_INSERT);

		stmt.setString(1, book.getTitle());
		stmt.setString(2, book.getAuthor());
		stmt.setString(3, book.getDatePublished());
		stmt.setInt(4, book.getQuantity());

		int rows;
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in the database failed!");

		System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", " + "Published: "
				+ book.getDatePublished() + ", Quantity: " + book.getQuantity());

		Commitable.confirm1(scan, conn);
	}

	public void search(Connection conn, Scanner scan, int i) throws SQLException, Exception {
		Book book;
		ResultSet myRs = null;
		PreparedStatement stmt;
		if (i == 1) {
			book = new Book(scan, 1);
			stmt = conn.prepareStatement(Book.SQL_SEARCH_SELECT_1);
			stmt.setString(1, "%" + book.getTitle() + "%");
			stmt.setString(2, "%" + book.getAuthor() + "%");
			stmt.setString(3, "%" + book.getDatePublished() + "%");
			myRs = stmt.executeQuery();
		} else if (i == 2) {
			stmt = conn.prepareStatement(Book.SQL_SEARCH_SELECT_2);
			myRs = stmt.executeQuery();
		} else if (i == 3) {
			book = new Book(scan, 2);
			stmt = conn.prepareStatement(Book.SQL_SEARCH_SELECT_3);
			stmt.setString(1, "%" + book.getTitle() + "%");
			myRs = stmt.executeQuery();
		}
		Book temp;
		while (myRs.next()) {
			temp = new Book(myRs.getInt("id"), myRs.getString("title"), myRs.getString("author"),
					myRs.getString("datePublished"), myRs.getInt("quantity"));
			System.out.println(temp.toString());
		}
		System.out.println("End of List");
	}

	public void delete(Connection conn, Scanner scan) throws SQLException, Exception {
		this.search(conn, scan, 1);
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement(Book.SQL_DELETE_SELECT);
		System.out.println("Enter id of book to be deleted:");

		int id = Integer.parseInt(scan.nextLine());
		stmt.setInt(1, id);
		ResultSet myRs = stmt.executeQuery();

		if (myRs.next()) {
			Book temp = new Book(myRs.getInt("id"), myRs.getString("title"), myRs.getString("author"),
					myRs.getString("datePublished"), myRs.getInt("quantity"));
			System.out.println(temp.toString());
		} else
			throw new Exception("No such book found!");

		stmt = conn.prepareStatement(Book.SQL_DELETE_DELETE);
		stmt.setInt(1, id);

		int rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deletion Failed!");
		System.out.println("Are you sure you want to delete this book?");
		Commitable.confirm1(scan, conn);
	}
	
	public Book BookCreator(Scanner scan) throws Exception {
		System.out.println("Title:");
		String title = scan.nextLine();
		if (title == null || title.equals(""))
			throw new Exception("Unnamed books are not allowed!");
		System.out.println("Author:");
		String author = scan.nextLine();
		System.out.println("Date Published (yyyy-mm-dd):");
		String date = scan.nextLine();
		Pattern p1 = Pattern.compile("^[0-9]{4}+[-]{1}+[0-9]{2}+[-]{1}+[0-9]{2}$");
		Matcher m1 = p1.matcher(date);
		if (!m1.matches()) {
			throw new Exception("Invalid Date Format");
		}
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		Date tempdate = format.parse(date);

		if (!format.format(tempdate).equals(date))
			throw new Exception("Invalid Date Format");
		System.out.println("Quantity:");
		int quantity = scan.nextInt();
		if (quantity < 0)
			throw new Exception("Please enter correct quantity!");
		
		return new Book(0, title, author, date, quantity);
	}

}
