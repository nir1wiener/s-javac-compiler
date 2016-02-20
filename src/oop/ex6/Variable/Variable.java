package oop.ex6.Variable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.Expression;

/**
 * 
 * @author nir&yoni.corporation
 * this class represent a given variable in the file.
 * containing all the information about the variable.
 *
 */

public class Variable {
	
	//initialize all the data members of the variable.
	private String varType;
	private String varName;
	private boolean varDefined = false;
	private boolean isVarFinal = false;
	
	/**
	 * constructor of the class
	 * @param type - the variable type(int,char,string,boolean,double)
	 * @param isFinal - if the variable is final or not.
	 * @throws VariableException
	 */
	public Variable(String type, boolean isFinal,String varName ) throws VariableException{
	
		this.varType = type;
		this.isVarFinal = isFinal;
		this.varName = varName;
	}

	/**
	 * 
	 * @return the variable type
	 */
	public String getVarType() {
		return this.varType;
	}

	/**
	 * 
	 * @return the variable name
	 */
	public String getVarName() {
		return this.varName;
	}

	/**
	 * set the name of the variable according to the given string
	 * @param varName - the name of the variable. 
	 */
	public void setVarName(String varName) {
		
		this.varName = varName;
	}

	/**
	 * 
	 * @return true if the variable is already defiend false else.
	 */
	public boolean isVarDefined() {
		
		return this.varDefined;
	}

	/**
	 * given a boolean varDefined(true,false) set the data member accordingly
	 * @param varDefined - true or false.
	 */
	public void setVarDefined(boolean varDefined) {
		this.varDefined = varDefined;
	}
	
	/**
	 * 
	 * @return if the variable is final
	 */

	public boolean isVarFinal() {
		return this.isVarFinal;
	}
	
	/**
	 * given a string represent the variable value, check's if 
	 * the value is  compatible to the type of the variable
	 * @param varValue - the value to check.
	 * @return true if value compatible false else. 
	 */
	public boolean typeMatchVar(String varValue){
		
		Expression exp;
		Pattern pattern;
		Matcher mattcher;
		
		switch(this.varType){
			
			case("int"):
				
				exp = Expression.INT;
				pattern = Pattern.compile(exp.getExpression());
				mattcher = pattern.matcher(varValue);
				return (mattcher.matches()) ? true : false ;
			
			case("double"):

				exp = Expression.DOUBLE;
				pattern = Pattern.compile(exp.getExpression());
				mattcher = pattern.matcher(varValue);
		    	return (mattcher.matches()) ? true : false ;
				
			case("boolean"):
				
				exp = Expression.BOOLEAN;
				pattern = Pattern.compile(exp.getExpression());
				mattcher = pattern.matcher(varValue);
				return (mattcher.matches()) ? true : false ;
			
			
			case("char"):
				
				exp = Expression.CHAR;
				pattern = Pattern.compile(exp.getExpression());
				mattcher = pattern.matcher(varValue);
				return (mattcher.matches()) ? true : false;	

			
			case("String"):
				
				exp = Expression.STRING;
			 	pattern = Pattern.compile(exp.getExpression());
			 	mattcher = pattern.matcher(varValue);
			 	return (mattcher.matches()) ? true : false ;
			
			default:
				// will never get to here
				return false;
			
		}		
		
	}
	
	
}
