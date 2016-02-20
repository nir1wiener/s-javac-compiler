package oop.ex6.Method;
import java.util.ArrayList;
import oop.ex6.Variable.Variable;

/**
 * this class represent a method 
 * @author nir&yoni.corporation
 */
public class Method {
	private String methodName;
	private ArrayList<Variable> methodParameters;
	private Block methodBlock;
	
	/**
	 * a method constructor 
	 * @param methodName - the name of this method
	 * @param methodParameters - array of parameters that this method gets
	 * @param methodBlock -  all lines that are represent this method.starting with method
	 * definition and ends with '}'
	 */
	public Method(String methodName,ArrayList<Variable> methodParameters,Block methodBlock) {
		this.methodName = methodName;
		this.methodParameters = methodParameters;
		this.methodBlock = methodBlock;
	}
	
	/**
	 * 
	 * return method name
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**
	 * 
	 * return method parameters
	 */
	public ArrayList<Variable> getMethodParameters() {
		return methodParameters;
	}
	
	/**
	 * 
	 * return method block
	 */
	public Block getMethodBlock(){
		return methodBlock;
	}
	
}
