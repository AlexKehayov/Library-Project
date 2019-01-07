package entries;

import lib_items.Book;
import users.User;

public class BookEntry extends AbsEntry {
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

	public BookEntry(int id, Book book, User user, String dateGiven, int termMonths) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.dateGiven = dateGiven;
		this.termMonths = termMonths;
	}

	public BookEntry(int id, String dateGiven, int termMonths, int bookId, String bookTitle, String bookAuthor,
			String bookDatePublished, int bookQuantity, int userId, String userUsername, String userPhone)
			throws Exception {

		this.id = id;
		this.dateGiven = dateGiven;
		this.termMonths = termMonths;
		this.book = new Book(bookId, bookTitle, bookAuthor, bookDatePublished, bookQuantity);
		this.user = new User(userId, userUsername, userPhone);
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
