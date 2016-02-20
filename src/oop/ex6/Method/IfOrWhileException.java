package oop.ex6.Method;
/** 
 * represent the if or while statement exception 
 * @author nir&yoni.corporation
 */
public class IfOrWhileException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * a default constructor 
	 */
	public IfOrWhileException(){
		super();
	}
	/**
	 * massage constructor 
	 * @param msg explain what where we got the problem
	 */
	public IfOrWhileException(String msg){
		super(msg);
		
	}


}
