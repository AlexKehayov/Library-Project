package entries;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import lib_items.Book;
import users.User;

@SuppressWarnings("unused")
public abstract class AbsEntry {
	
	private int id;
	private Book book;
	private User user;
	private String dateGiven;
	private int termMonths;
	
	
	public AbsEntry() {
		super();
	}
	
	public AbsEntry(int id, Book book, User user, String dateGiven, int termMonths) {
		super();
		this.id = id;
		this.book = book;
		this.user = user;
		this.dateGiven = dateGiven;
		this.termMonths = termMonths;
	}


	public abstract int getId();

	public abstract Book getBook();

	public abstract User getUser();

	public abstract String getDateGiven();

	public abstract int getTermMonths();

	public abstract String toString();
	
}
