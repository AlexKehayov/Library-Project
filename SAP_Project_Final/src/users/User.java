package users;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends AbsUser {
	public final static String SQL_ADDNEWUSER_INSERT = "insert into users (username, phone) values (?, ?);";
	public final static String SQL_SEARCH_SELECT = "select * from users where username like ? and phone like ?;";

	private int id;
	private String username;
	private String phone;

	public User(int id, String username, String phone) {
		this.id = id;
		this.username = username;
		this.phone = phone;
	}

	public User(String username, String phone) {
		this.username = username;
		this.phone = phone;
	}

	public User(Scanner scan) throws Exception {
		System.out.println("Username:");
		String username = scan.nextLine();
		System.out.println("Phone:");
		String phone = scan.nextLine();

		Pattern p1 = Pattern.compile("^[0-9]{10}$");
		Matcher m1 = p1.matcher(phone);
		if (!m1.matches()) {
			throw new Exception("Invlaid phone number");
		}
		this.username = username;
		this.phone = phone;
	}

	public User(Scanner scan, int i) throws Exception {
		System.out.println("Username:");
		String username = scan.nextLine();
		System.out.println("Phone:");
		String phone = scan.nextLine();

		this.username = username;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", phone=" + phone + "]";
	}
}
