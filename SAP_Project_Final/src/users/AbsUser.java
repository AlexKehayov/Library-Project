package users;

@SuppressWarnings("unused")
abstract class AbsUser {

	private int id;
	private String username;
	private String phone;
	
	public AbsUser() {
		
	}

	public AbsUser(int id, String username, String phone) {
		this.id = id;
		this.username = username;
		this.phone = phone;
	}

	public AbsUser(String username, String phone) {
		this.username = username;
		this.phone = phone;
	}
	
	public abstract int getId();

	public abstract String getUsername();

	public abstract String getPhone();

	public abstract String toString();
}
