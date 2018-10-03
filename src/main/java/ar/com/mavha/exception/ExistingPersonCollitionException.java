package ar.com.mavha.exception;

public class ExistingPersonCollitionException extends Exception {

	public ExistingPersonCollitionException() {
        super("The person already exists");
    }
 
    public ExistingPersonCollitionException(Long id) {
    	super("The person with DNI " + id + " already exists");
    }
}
