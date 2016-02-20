package oop.ex6.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Expression.Expression;
import oop.ex6.Variable.Variable;
import oop.ex6.Variable.VariableException;
/**
 * 
 * @author nir&yoni.corporation
 * represent a if or while statement, all local variables,all global variables and all method
 *  that are in the if or while 
 */
public class IfOrWhileStatement {

	private ArrayList<ArrayList<String>> block = new ArrayList<ArrayList<String>>();
	private ArrayList<Variable> globalVariables = new ArrayList<Variable>();
	private ArrayList<Method> allMethods = new ArrayList<Method>();
	private ArrayList<Variable> localVariables = new ArrayList<Variable>();
	private ArrayList<String> LineDefintion;
	
	/**
	 * Constructor for the if or while statement, define the inside block,global and local
	 * variables and the line that start the if or while.
	 * @param block - the all method block,we create the inside block using build sub block function
	 * @param globalVariables -all global variables 
	 * @param allMethods - all defined methods
	 * @param localVariables - all local variables 
	 * @param startLine - the if or while statement.
	 */
	public IfOrWhileStatement(ArrayList<ArrayList<String>> block,ArrayList<Variable> globalVariables,
			ArrayList<Method> allMethods,ArrayList<Variable> localVariables,int startLine){
		
		this.LineDefintion = block.get(startLine);
		this.allMethods = allMethods;
		this.globalVariables = globalVariables;
		this.block = buildSubBlock(block, startLine);
		this.localVariables = localVariables;
	}
	/*
	 * gets array of multiple lines and creates a sub array of lines that represent the inside of a 
	 * if or while statement 
	 */
	private ArrayList<ArrayList<String>> buildSubBlock(ArrayList<ArrayList<String>> block,int startLine){
		
		ArrayList<ArrayList<String>> subBlock = new ArrayList<ArrayList<String>>() ;
		//go all over the lines and adds until the block is close with '}'
		int bracket = 1; 
		while(true){
			startLine ++;
			int lastIndex = block.get(startLine).size()-1;
			if(block.get(startLine).get(lastIndex).equals("{")){
				bracket++;
			}else if(block.get(startLine).get(lastIndex).equals("}")){
				bracket--;
				if(bracket==0){
					break;
				}
			}
			subBlock.add(block.get(startLine));
		}
		return subBlock;
	}
	
	/**
	 * creates a block that contains the block inside the if or while statement and check it
	 * @throws IfOrWhileException - if or while problem
	 * @throws MethodException - if there is an problem with method call
	 * @throws VariableException - if there is problem with one of the variables
	 */
	public void isWhileOrIfGood() throws IfOrWhileException, MethodException, VariableException{

		isWhileOrIfDefinedGood(this.LineDefintion);
		Block ifOrWhileBlock = new Block(this.block, this.globalVariables, this.allMethods, 
																			this.localVariables);
		
		// checks recursively if the block we just made is good
		ifOrWhileBlock.isBlockGood();
	}
	
	/*
	 * checks if the while or if statement is good.
	 * its pass all over this line to check if it as expected 
	 */
	private void isWhileOrIfDefinedGood(ArrayList<String> separatedLine) throws IfOrWhileException, 
																					MethodException{
		
		Expression exp = Expression.DOUBLE;
		Pattern numPattern = Pattern.compile(exp.getExpression());
		int index = 1;
		int minmumLengthOfStatement = 5 ;
		int lineLen = separatedLine.size();
		if(lineLen < minmumLengthOfStatement){
			throw new IfOrWhileException("unsupported line");
		}
		//we checked before we enter this function if the first word is 'if' or 'while' 
		//and now we check that
		//the next word is an open bracket as expected 
		if(!separatedLine.get(index).equals("(")){
			throw new IfOrWhileException("unsupported line");
		}
		
		index++;
		// checks that the index is not out of bounds
		isLineEnd(index,lineLen);
		// will run all over the words until a close bracket is appear
		while(!separatedLine.get(index).equals(")")){
			Matcher matcher = numPattern.matcher(separatedLine.get(index));
			// checks if the word is boolean true or false as expected
			if(separatedLine.get(index).equals("true") ||separatedLine.get(index)
												.equals("false")||matcher.matches()){
				index++;
				isLineEnd(index,lineLen);
				//if the word isn't true or false it can be a variable 
			}else{
				Variable var = isVarExist(separatedLine.get(index));
				// if variable type isn't boolean we will throw an exception otherwise we will check next word
				if( var != null ){
					String varType = var.getVarType();
					if((varType.equals("boolean")||varType.equals("double")||varType.equals("int")) &&
																						var.isVarDefined()){
						index++;
						isLineEnd(index,lineLen);
					}else{
						throw new IfOrWhileException("unsupported variable");
					}
				}else{
					throw new IfOrWhileException("unsupported variable");
				}
			}
			//the next word after boolean must be '&&' or '||' or close bracket 
			if( (separatedLine.get(index).equals("&&")) || (separatedLine.get(index).equals("||")) ){
				index++;
				isLineEnd(index,lineLen);
			}else{
				if(separatedLine.get(index).equals(")")){
					break;
				}else{
					throw new IfOrWhileException("unsupported line");
				}
			}
		}
		index++;
		isLineEnd(index, lineLen);
		// checks that the end of line is '{' as expected
		if(!(separatedLine.get(index).equals("{"))||(index+1 != lineLen)){
			throw new IfOrWhileException("unsupported line");
		}
	}
	
	/*
	 * checks if index is out of bounds
	 */
	private void isLineEnd(int index,int lineLen) throws MethodException{
		if(index == lineLen){
			throw new MethodException("unsupported line");
		}
	}
	/*
	 * gets a name of variable and checks if it exist in the local variables or in the global variables 
	 * if it exist the function will return this variable  else return null
	 */
	private Variable isVarExist(String varName){
		for(Variable var : this.globalVariables){
			if(var.getVarName().equals(varName)){
				return var;
			}
		}
		for(Variable var : this.localVariables){
			if(var.getVarName().equals(varName)){
				return var;
			}
		}
		return null;
	}
	public ArrayList<ArrayList<String>> getBlock(){
		return this.block;
	}

}
