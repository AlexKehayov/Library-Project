package lib_items;

@SuppressWarnings("unused")
abstract class AbsBook {
	
	private int id;
	private String title;
	private String author;
	private String datePublished;
	private int quantity;
	
	public AbsBook() {
		super();
	}

	public AbsBook(String title, String author) {
		super();
		this.title = title;
		this.author = author;
	}

	public AbsBook(int id, String title, String author, String datePublished, int quantity) throws Exception {
		this.id = id;
		this.title = title;
		this.author = author;
		this.datePublished = datePublished;
		this.quantity = quantity;
	}
	
	public abstract int getId();

	public abstract String getTitle();

	public abstract String getAuthor();

	public abstract String getDatePublished();

	public abstract int getQuantity();

	public abstract String toString();

}
