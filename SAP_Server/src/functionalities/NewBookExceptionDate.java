package functionalities;

public class NewBookExceptionDate extends Exception{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		
		return "Please enter valid date!";
	}

	
}
