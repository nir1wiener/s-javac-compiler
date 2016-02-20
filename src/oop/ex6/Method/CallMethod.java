package oop.ex6.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.Expression;
import oop.ex6.Variable.Variable;
import oop.ex6.Variable.VariableException;

/**
 * @author nir&yoni.corporation
 * represent a method call check's if the call
 * is valid.
 */
public class CallMethod {
	
	/*
	 * checks if a method name exist in all methods and if it exist return method else return null
	 */
	private static Method methodExist(String methodName,ArrayList<Method>allMethods){
		
		for(Method method : allMethods){
			if(method.getMethodName().equals(methodName))
				return method;
		}
		return null;
	}
	
	/*
	 * gets two lists of the local and global variables and string that represent a already defined
	 * variable or a variable that wasn't yet defined and returns the type of this variable.
	 */
	private static String type(String variable,ArrayList<Variable> globalVariables,
			ArrayList<Variable> localVariables) throws VariableException{
		
		// check if variable exist and return its type
		Variable var = isVarExist(variable,globalVariables,localVariables);
		if(var != null){
			return var.getVarType();
		}
		
		// expression to check what type is this variable
		Expression exp = Expression.BOOLEAN;
		Pattern booleanPattern = Pattern.compile(exp.getExpression());
		exp = Expression.CHAR;
		Pattern charPattern = Pattern.compile(exp.getExpression());
		exp = Expression.DOUBLE;
		Pattern doublePattern = Pattern.compile(exp.getExpression());
		exp = Expression.INT;
		Pattern intPattern = Pattern.compile(exp.getExpression());
		exp = Expression.STRING;
		Pattern stringPattern = Pattern.compile(exp.getExpression());
		Matcher matcher = intPattern.matcher(variable);
		
		// checks if this string represent a int type variable
		if(matcher.matches()){
			return "int";
		}
		// checks if this string represent a double type variable
		matcher = doublePattern.matcher(variable);
		if(matcher.matches()){
			return "double";
		}
		// checks if this string represent a boolean type variable
		matcher = booleanPattern.matcher(variable);
		if(matcher.matches()){
			return "boolean";
		}
		// checks if this string represent a char type variable
		matcher = charPattern.matcher(variable);
		if(matcher.matches()){
			return "char";
		}
		// checks if this string represent a String type variable
		matcher = stringPattern.matcher(variable);
		if(matcher.matches()){
			return "String";
		}
		//if it doesn't match any pattern its unsupported  variable type
		throw new VariableException("unsupported variable type");
	}

	/*
	 *this function gets a local variables,global variables and a line and return array that each cell
	 * represent the type of the method call parameters 
	 */
	private static ArrayList<String> getMethodCallParameters(ArrayList<String> line,ArrayList<Variable> 
		globalVariables,ArrayList<Variable> localVariables) throws MethodException, VariableException{
		
		ArrayList<String> methodCallParameters = new ArrayList<String>();
		int index = 1;
		String type;
		int lineLen = line.size();
		// checks that the second word is an open bracket
		if(!(line.get(index).equals("("))){
			System.out.println(line.get(index));
			throw new MethodException("unsportted method call");
		}
		index++;
		// check that the index is not out of bounds
		isLineEnd(index, lineLen);
		
		// go over all words that represent a parameter and checks their type and add to the array 
		while(true){
			if(line.get(index).equals(")")){
				index++;
				isLineEnd(index, lineLen);
				// if there is ';' it means that no more parameters and we can finish the run
				if(!(line.get(index).equals(";"))||(index+1 != lineLen)){
					throw new MethodException("method call defintion isnt correct");
				}else{
					break;
				}
			}
			type = type(line.get(index),globalVariables,localVariables);
			methodCallParameters.add(type);
			index++;
			isLineEnd(index, lineLen);
			// checks if there is a ',' if there is we go to the next word
			if(line.get(index).equals(",")){
				index++;
				isLineEnd(index, lineLen);
				continue;
			}else if (line.get(index).equals(")")){
				continue;
			}
			throw new MethodException("unsupported line");
		}
		return methodCallParameters;
	}

	/**
	 * checks if this method call is valid else throws exception
	 * @param line - the method call line
	 * @param globalVariables - all global variables to check if variables that was call by method call
	 * are defined and with a proper type 
	 * @param allMethods - all methods to check if this method exist
	 * @param localVariables - as global variables
	 * @throws MethodException - if there was problem with the method call
	 * @throws VariableException - if there was problem with on of the variables ( method parameters)
	 */
	public static void isMethodCallGood(ArrayList<String> line,ArrayList<Variable> globalVariables,
			ArrayList<Method> allMethods,ArrayList<Variable> localVariables)
					throws MethodException, VariableException{
		
		final int NAME = 0;
		// checks that the method is exist

		Method method = methodExist(line.get(NAME),allMethods);
		if(method == null){
			throw new MethodException("method dosent exist");
		}
		// checks if the number of parameters matches the number of parameters that
		//the method expected
		ArrayList<String> methodCallParameters = getMethodCallParameters
				(line,globalVariables,localVariables);
		if(method.getMethodParameters().size() !=  methodCallParameters.size() ){
			throw new MethodException("patameters dont match");
		}
		// checks if each parameter type is equal
		for(int index = 0; index < methodCallParameters.size(); index++){
			if(!methodCallParameters.get(index).equals(method.getMethodParameters().
																get(index).getVarType())){
				throw new MethodException("patameters dont match");
			}
		}
	}
	
	/*
	 * checks if index is out of bounds
	 */
	private static void isLineEnd(int index,int lineLen) throws MethodException{
		if(index == lineLen){
			throw new MethodException("unsupported line");
		}
	}
	
	/*
	 * gets a name of variable and checks if it exist in the local variables or in the global
	 *  variables  if it exist the function will return this variable  else return null
	 */
	private static Variable isVarExist(String varName,ArrayList<Variable> 
		globalVariables,ArrayList<Variable> localVariables){

		for(Variable var : globalVariables){
			if(var.getVarName().equals(varName)){
				return var;
			}
		}
		for(Variable var : localVariables){
			if(var.getVarName().equals(varName)){
				return var;
			}
		}
		return null;
	}

}
