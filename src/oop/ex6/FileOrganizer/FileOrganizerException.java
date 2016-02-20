package oop.ex6.FileOrganizer;
/**
 * represent the file organizer exception 
 *  @author nir&yoni.corporation
 */
public class FileOrganizerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor - default
	 * @param message
	 */
	public FileOrganizerException(){
		super();
	}
	
	/**
	 * constructor - get a massage constructor 
	 * @param message- explain the error that accord
	 */
	public FileOrganizerException(String message){
		super(message);
		
	}
}


