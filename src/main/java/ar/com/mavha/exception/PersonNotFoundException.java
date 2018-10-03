package ar.com.mavha.exception;

public class PersonNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException() {
        super("No user found");
    }
 
    public PersonNotFoundException(Long id) {
    	super("No user found for id: " + id);
    }
}
