package staffMembers;

@SuppressWarnings("unused")
abstract class AbsStaffMember {
	
	private String username;
	private String password;
	
	
	
	public AbsStaffMember() {
		super();
	}

	public AbsStaffMember(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public abstract String getUsername();
	
	public abstract String getPassword();
	
	public abstract int hashCode();

	public abstract boolean equals(Object obj);
}
