package users;

import java.util.Scanner;

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

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", phone=" + phone + "]";
	}
}
