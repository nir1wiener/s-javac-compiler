package oop.ex6.Variable;

/**
 * represent the Variable exception 
 * @author nir&yoni.corporation
 */
public class VariableException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
     * constructor - default
     * @param message
     */
    public VariableException(){
        super();
    }
    /**
     * constructor - get a massage constructor 
     * @param message- explain the error that accord
     */
    public VariableException(String message){
        super(message);
        
    }
    
}


