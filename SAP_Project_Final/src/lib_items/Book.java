package lib_items;

import java.util.Scanner;

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


	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Book [id:" + id + ", title:" + title + ", author:" + author + ", datePublished:" + datePublished
				+ ", quantity:" + quantity + "]";
	}

}