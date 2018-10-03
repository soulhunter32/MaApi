package ar.com.mavha.exception;

public class EmptyBodyException extends Exception{

	private static final long serialVersionUID = 1L;

	public EmptyBodyException() {
        super("Please specify the request body");
    }
}
