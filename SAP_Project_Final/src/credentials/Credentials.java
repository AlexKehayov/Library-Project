package credentials;

public class Credentials {

	private final String url = "jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final  String root = "alex";
	private final String password = "prileptsi";

	public Credentials() {
		
	}

	public String getUrl() {
		return url;
	}

	public String getRoot() {
		return root;
	}

	public String getPassword() {
		return password;
	}
	
	

}
