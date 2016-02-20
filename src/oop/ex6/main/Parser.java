package oop.ex6.main;

import java.util.ArrayList;

import oop.ex6.FileOrganizer.FileOrganizerException;
import oop.ex6.Variable.Variable;
import oop.ex6.Variable.VariableException;
import oop.ex6.Variable.VariableFactory;
import oop.ex6.Method.*;
/**
 * parser class is the class that are responsible of taking the organized
 *  file that been put into array 
 * and to check if the file is as we expected using all other packages. if the file 
 * is not written as expected
 * this class will throw the right Exception(FileOrganizerException,VariableException,
 *  MethodException, IfOrWhileException).    
 * @author nir&yoni.corporation
 * 
 */
public class Parser {

	private static ArrayList<ArrayList<String>> globalLine = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<ArrayList<String>>> allMethods = 
													new ArrayList<ArrayList<ArrayList<String>>>();
	private static ArrayList<ArrayList<String>> method = new ArrayList<ArrayList<String>>();

	/**
	 * this function goes all over the lines split them to methods and variables, 
	 * create all global variables - if
	 * there is a problem with one of the variables it throws an exception , 
	 * then create all methods and throw 
	 * exception if needed , and in the end it checks all lines inside a method
	 *  and throw exception if needed
	 * @param fileArray - an array that holds all the already organized lines. 
	 * @throws VariableException - if a variable isn't defined good
	 * @throws MethodException - if there is problem in definition of method,
	 *  method call or inside the method block
	 * @throws IfOrWhileException - if there was a problem in if or while statement 
	 */
	public static void parse(ArrayList<ArrayList<String>> fileArray) throws FileOrganizerException, 
	VariableException, MethodException, IfOrWhileException{

		globalLine = new ArrayList<ArrayList<String>>();
		allMethods = new ArrayList<ArrayList<ArrayList<String>>>();
		method = new ArrayList<ArrayList<String>>();

		ArrayList<Variable> globalVariables = new ArrayList<Variable>();
		ArrayList<Method> fileMethods = new ArrayList<Method>();
		// divide line to method lines and to variables lines
		divideFileToMethodAndVariables(fileArray);

		// creates all global variables
		for (ArrayList<String> line : globalLine ){
			VariableFactory.variableFactory(line, globalVariables, globalVariables);
		} 
		//creates all methods
		for (ArrayList<ArrayList<String>> curMethod : allMethods ){
			Method method = MethodFactory.createMethod(fileMethods, curMethod , globalVariables);
			fileMethods.add(method);
		}
		// check if all method's block is good
		for(Method methodToCheck : fileMethods){
			methodToCheck.getMethodBlock().isBlockGood();
		}
	}
	/*
	 * gets an array and divide it to 2 different arrays, one that holds all lines that defined 
	 * global variables,
	 * and the other holds all lines of methods.
	 */
	private static void divideFileToMethodAndVariables(ArrayList<ArrayList<String>> fileArray)                       
			throws FileOrganizerException{ 
		final int PLACE_IN_ARRAY = 1;
		final int NO_BRACKETS = 0;
		final int RETURN_STATEMENT_SIZE = 2;
		int open_bracket = 0;
		// goes over all lines and decide in which array to put a certian line
		for(ArrayList<String> curLine : fileArray){

			// if the last word in this line is and open bracket its add this 
			//line to a method and then will add 
			// all lines until this bracket will close
			if (curLine.get(curLine.size() - PLACE_IN_ARRAY ).equals("{")){				
				method.add(curLine);
				open_bracket ++;
				continue;
			}
			if (curLine.get(curLine.size() - PLACE_IN_ARRAY ).equals("}")){
				open_bracket --;
				//Checks that there is no more close brackets then opens brackets  
				if ((open_bracket < NO_BRACKETS) ){
					throw new FileOrganizerException("illgeal number of bracket");
				}
				// if open bracket equals a 0 then this line is the last line of the current method,
				//checks also  that was a return line
				if (open_bracket == NO_BRACKETS){
					ArrayList<String> returnLine =  method.get(method.size() - PLACE_IN_ARRAY);
					if (returnLine.size() == RETURN_STATEMENT_SIZE){
						if((!returnLine.get(0).equals("return"))|| (!returnLine.get(1).equals(";"))){
							throw new FileOrganizerException("non return");
						}
					}else{
						throw new FileOrganizerException("non return");

					}
					method.add(curLine);
					allMethods.add(method);
					method = new ArrayList<ArrayList<String>>();
					continue;

				}
				method.add(curLine);
				continue;
			}
			if (open_bracket  > NO_BRACKETS){
				method.add(curLine);
				continue;
			}
			// case that its global line,means that this line is outside of method
			if (open_bracket == NO_BRACKETS ){
				globalLine.add(curLine);
			}

		}
		// at the end of all lines can't be that a method didn't close all her brackets 
		if (open_bracket != NO_BRACKETS ){
			throw new FileOrganizerException("illgeal number of bracket");
		}


	}
}
