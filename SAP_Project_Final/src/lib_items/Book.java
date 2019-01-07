package lib_items;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book extends AbsBook{

	public final static String SQL_ADDNEW_INSERT = "insert into books (title, author, datepublished, quantity) values (?, ?, ?, ?)";
	public final static String SQL_SEARCH_SELECT_1 = "select * from books where title like ? and author like ? and datepublished like ?;";
	public final static String SQL_SEARCH_SELECT_2 = "select * from books;";
	public final static String SQL_SEARCH_SELECT_3 = "select * from books where title like ?;";
	public final static String SQL_DELETE_DELETE = "delete from books where id=?";
	public final static String SQL_DELETE_SELECT = "select * from books where id=?;";

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

	@Override
	public String toString() {
		return "Book [id:" + id + ", title:" + title + ", author:" + author + ", datePublished:" + datePublished
				+ ", quantity:" + quantity + "]";
	}

}