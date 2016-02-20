package oop.ex6.Method;
/** 
 * represent the Method exception 
 * @author nir&yoni.corporation
 */
public class MethodException extends Exception  {
	
	private static final long serialVersionUID = 1L;
	/**
	 * a default constructor 
	 */
	public MethodException(){
		super();
	}
	/**
	 * massage constructor 
	 * @param msg explain what where we got the problem
	 */
	public MethodException(String msg){
		
		super(msg);
		
	}
}
