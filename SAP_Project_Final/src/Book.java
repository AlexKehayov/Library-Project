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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.Commitable;
import interfaces.LibItem;

public class Book implements LibItem, Commitable{
	private int id;
	private String title;
	private String author;
	private String datePublished;
	private int quantity;

	public Book() {

	}

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Book(int id, String title, String author, String datePublished, int quantity) throws Exception {
		this.id = id;
		this.title = title;
		this.author = author;
		this.datePublished = datePublished;
		this.quantity = quantity;
	}

	public Book(Scanner scan) throws Exception {
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
		this.title = title;
		this.author = author;
		this.datePublished = date;
		this.quantity = quantity;
	}

	public Book(Scanner scan, int i) throws Exception {
		if (i == 1) {
			System.out.println("Title:");
			String title = scan.nextLine();
			System.out.println("Author:");
			String author = scan.nextLine();
			System.out.println("Date Published (yyyy-mm-dd):");
			String date = scan.nextLine();
			this.title = title;
			this.author = author;
			this.datePublished = date;
		} else if (i == 2) {
			System.out.println("Title:");
			String title = scan.nextLine();
			this.title = title;
		} else if (i == 3) {
			System.out.println("Title:");
			String title = scan.nextLine();
			this.title = title;
			System.out.println("Author:");
			String author = scan.nextLine();
			this.author = author;
		}
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addNew(Connection conn, Scanner scan) throws SQLException, Exception {
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn
				.prepareStatement("insert into books (title, author, datepublished, quantity) values (?, ?, ?, ?)");

		stmt.setString(1, this.getTitle());
		stmt.setString(2, this.getAuthor());
		stmt.setString(3, this.getDatePublished());
		stmt.setInt(4, this.getQuantity());

		int rows;
		rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Writing in the database failed!");

		System.out.println("Title: " + this.getTitle() + ", Author: " + this.getAuthor() + ", " + "Published: "
				+ this.getDatePublished() + ", Quantity: " + this.getQuantity());

		confirm(scan, conn);
	}

	public void search(Connection conn, Scanner scan, int i) throws SQLException, Exception {
		Statement myStmt = conn.createStatement();
		ResultSet myRs = null;
		if (i == 1) {
			myRs = myStmt.executeQuery(
					"select * from books where title like '%" + this.getTitle() + "%'" + " and author like '%"
							+ this.getAuthor() + "%' and datepublished like '%" + this.getDatePublished() + "%';");
		} else if (i == 2) {
			myRs = myStmt.executeQuery("select * from books;");
		} else if (i == 3) {
			myRs = myStmt.executeQuery("select * from books where title like '%" + this.getTitle() + "%';");
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
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement("delete from books where id=?");
		System.out.println("Enter id of book to be deleted:");

		int id = Integer.parseInt(scan.nextLine());
		stmt.setInt(1, id);

		Statement stmt2 = conn.createStatement();
		ResultSet myRs = stmt2.executeQuery("select * from books where id=" + id + ";");

		if (myRs.next()) {
			Book temp = new Book(myRs.getInt("id"), myRs.getString("title"), myRs.getString("author"),
					myRs.getString("datePublished"), myRs.getInt("quantity"));
			System.out.println(temp.toString());
		} else
			throw new Exception("No such book found!");

		int rows = stmt.executeUpdate();
		if (rows != 1)
			throw new Exception("Deletion Failed!");

		confirm(scan, conn);

	}

	@Override
	public String toString() {
		return "Book [id:" + id + ", title:" + title + ", author:" + author + ", datePublished:" + datePublished
				+ ", quantity:" + quantity + "]";
	}

}
