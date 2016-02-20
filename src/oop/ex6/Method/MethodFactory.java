package oop.ex6.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.*;
import oop.ex6.Variable.*;

/**
 * a class that represents a method Factory
 * when her main function is createMethod that create a new method after checking 
 * that the method definition is good
 * @author nir&yoni.corporation
 */
public class MethodFactory {
	
	private final static int METHOD_DEFINE_LINE = 0;
	private final static int METHOD_NAME = 1;

	/**
	 * create a new Method 
	 * @param allMethods - all methods that was already defined
	 * @param methodBlock - array of lines  that represent the method
	 * @param globalVariables - all global variables 
	 * @return a Method
	 * @throws MethodException - if method definition wasn't as we expected  
	 * @throws VariableException - if one of the method parameters wasn't as we expected
	 */
	public static Method createMethod(ArrayList<Method> allMethods,ArrayList<ArrayList<String>> methodBlock,
			ArrayList<Variable> globalVariables) throws MethodException, VariableException{
		
		ArrayList<String> methodLine = methodBlock.get(METHOD_DEFINE_LINE);
		
		if(isMethodDefinedGood(methodLine)){		
			String methodName = methodLine.get(METHOD_NAME);
			checkMethodExist(methodName,allMethods);
			ArrayList<Variable> methodParameters = methodParameters(methodBlock);
			Block block = createBlock(allMethods,methodBlock,globalVariables,methodParameters);	
			return new Method(methodName, methodParameters, block);
		}
		return null;
	}
	
	/*
	 * creates an array of parameters. each cell will hold a instance of Variable
	 */
	private static ArrayList<Variable> methodParameters(ArrayList<ArrayList<String>> methodBlock) 
			throws VariableException{
		
		ArrayList<Variable> methodParameters = new ArrayList<Variable>() ;		
		ArrayList<String> methodDefineLine = methodBlock.get(METHOD_DEFINE_LINE);
		
		// parameters type are in distance of 3 or 4 if one of them is final 
		final int NEXT_PARAMETER_TYPE = 3;
		final int NAME = 1;
		int lenOfParmaters = methodBlock.get(METHOD_DEFINE_LINE).size();
		int parameter = 3;
		
		// runs on all parameters 
		while( parameter < lenOfParmaters-NEXT_PARAMETER_TYPE){
			boolean isFinal = false;
			// checks if this parameter is final or not
			if(methodDefineLine.get(parameter).equals("final")){
				isFinal = true;
				parameter++;
			}
			//create new variable and adds it to the methodParameters array
			Variable variable = new Variable(methodDefineLine.get(parameter),isFinal,methodDefineLine.
								get(parameter+NAME));
			variable.setVarDefined(true);
			for ( Variable var : methodParameters){
				if(var.getVarName().equals(variable.getVarName())){
					throw new VariableException("variable is alreadt exist");
				}
			}
			methodParameters.add(variable);
			// parameters type are in distance of 3 or 4 if one of them is final 
			parameter += NEXT_PARAMETER_TYPE ;
		}
		return methodParameters;
	}
	
	/*
	 * create a instance of block
	 */
	private static Block createBlock(ArrayList<Method> allMethods,ArrayList<ArrayList<String>> methodBlock,
			ArrayList<Variable> globalVariables,ArrayList<Variable> localVariables) throws VariableException{
		
		ArrayList<ArrayList<String>> block = new ArrayList<ArrayList<String>>();
		for(int line = 1 ; line < methodBlock.size()-1 ; line++){
			block.add(methodBlock.get(line));
		}
		return new Block(block,globalVariables,allMethods,localVariables);
	}
	
	/*
	 * checks if method definition is as expected 
	 */
	private static boolean isMethodDefinedGood(ArrayList<String> separatedLine) throws MethodException{
		String line = "";
		// gets a separated line and attach her to a string so we can works with regex
		for(int i=0; i < separatedLine.size();i++){
			line += separatedLine.get(i)+" ";
		}
		// expression that checks if method definition is as expected
		Expression exp = Expression.METHOD_DEFINITION;
		Pattern methodCheckPattern  = Pattern.compile(exp.getExpression());
		Matcher matcher = methodCheckPattern.matcher(line);
		if(matcher.matches()){
			return true;
		}
		throw new MethodException("method definition wasn't as excpected");
	}
	/*
	 * checks if this method name already exist in the array of all methods
	 */
	private static boolean checkMethodExist(String methodName,ArrayList<Method> allMethods) 
														throws MethodException{
		if(!(allMethods == null)){
			for(int method=0 ; method<allMethods.size(); method++ ){
				if(allMethods.get(method).getMethodName().equals(methodName)){
					throw new MethodException("method already exsit");
				}
			}
		}
		return true;
	}

}
