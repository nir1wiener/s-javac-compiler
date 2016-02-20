package oop.ex6.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Expression.Expression;
import oop.ex6.Variable.*;

/**
 * this class represent a block inside a method or inside if or while statement 
 * the main function is 'isBlockGood' that checks if all lines in this block
 * is as we will expected
 * @author nir&yoni.corporation
 */
public class Block {

	private ArrayList<ArrayList<String>> block = new ArrayList<ArrayList<String>>();
	private ArrayList<Variable> globalVariables = new ArrayList<Variable>();
	private ArrayList<Method> allMethods = new ArrayList<Method>();
	private ArrayList<Variable> localVariables = new ArrayList<Variable>();

	/**
	 * a constructor for block  
	 * @param block - array of lines that represent all lines inside the block
	 * @param globalVariables - all global variables 
	 * @param allMethods - all defined methods
	 * @param localVariables - variables that was defined inside this block
	 */
	public Block(ArrayList<ArrayList<String>> block,ArrayList<Variable> globalVariables,
			ArrayList<Method> allMethods,ArrayList<Variable> localVariables){

		this.allMethods = allMethods;
		this.globalVariables = globalVariables;
		this.block = block;
		this.localVariables = localVariables;

	}
	/**
	 * checks each line in this block if the line is legal 
	 * @throws MethodException - if line that represent method call isn't good
	 * @throws VariableException - if line that represent any kind of define variables isn't good  
	 * @throws IfOrWhileException - if line that represent if or while block isn't good
	 */
	public void isBlockGood() throws MethodException, VariableException, IfOrWhileException{
		// expressions that we will check to decide if its a variable , while or if , call method,
		// or return statement 
		Expression exp = Expression.VARIABLE_TYPE;
		Pattern variableTypePattern  = Pattern.compile(exp.getExpression());

		exp = Expression.METHOD_NAME;
		Pattern methodCallPattern = Pattern.compile(exp.getExpression());

		exp = Expression.IF_WHILE;
		Pattern ifOrWhilePattern = Pattern.compile(exp.getExpression());

		exp = Expression.RETURN;
		Pattern returnPattern = Pattern.compile(exp.getExpression());
		
		if(this.block == null){
			return;
		}

		// runs on all lines in this block and check if every line is good else it will throw exception
		for(int separatedLine = 0; separatedLine < this.block.size();separatedLine++){
			final int FIRST_WORD = 0;
			final int SECONED_WORD = 1;

			String fristWordInLine = this.block.get(separatedLine).get(FIRST_WORD);
			Matcher matcher = variableTypePattern.matcher(fristWordInLine);
			
			// if line is more then one word and the if the first word is match variable Type Pattern or 
			// equals to final it means that this line is define variable 
			// if second word isn't '(' its means that its for sure not ifOrWhile statement 
			//and for sure not method call
			if (this.block.get(separatedLine).size() > SECONED_WORD){
				String secondWordInLine = this.block.get(separatedLine).get(SECONED_WORD);
				if(matcher.matches() || fristWordInLine.equals("final")||!(secondWordInLine.equals("("))){
                                                                                                               
					createLocalBlockVariable(this.block.get(separatedLine));
					continue;
				}
			}
			// if its a return statement we will go and check the next line
			matcher = returnPattern.matcher(fristWordInLine);
			if(matcher.matches()){
				continue;
			}
			// if its a if or while statement we will check if this if or while is good
			matcher = ifOrWhilePattern.matcher(fristWordInLine);
			if(matcher.matches()){
				ArrayList<Variable> tmpLocalVariables = new ArrayList<Variable>();
				for(Variable v:this.localVariables){
					tmpLocalVariables.add(v);
				}
				IfOrWhileStatement ifOrWhile = createIfOrWhileStatement(separatedLine);
				ifOrWhile.isWhileOrIfGood();
				separatedLine += ifOrWhile.getBlock().size()+2;
				this.localVariables = tmpLocalVariables;
				continue;
			}
			//if its a method call we will check if method call is good
			matcher = methodCallPattern.matcher(fristWordInLine);
			if(matcher.matches()){
				CallMethod.isMethodCallGood(this.block.get(separatedLine), globalVariables, allMethods, 
						localVariables);
				
				continue;
			}
		}
	}

	/*
	 * if a current line is variable definition we will call variableFactory that will check if this
	 * line is as expected and will add it to the local variables list  
	 */
	private void createLocalBlockVariable(ArrayList<String> separatedLine) throws VariableException{
		VariableFactory.variableFactory(separatedLine,this.localVariables,this.globalVariables);
	}

	/*
	 * return a new IfOrWhileStatement obj
	 */
	private IfOrWhileStatement createIfOrWhileStatement(int startLine){
		
		return new IfOrWhileStatement(this.block, this.globalVariables, this.allMethods, 
										this.localVariables,startLine);
	}
	
	/**
	 * return the lines that represent block
	 */
	public ArrayList<ArrayList<String>> getBlock() {
		return block;
	}
	
	/**
	 * return array of all local variables 
	 */
	public ArrayList<Variable> getLocalVariables(){
		return localVariables;
	}
	
	/**
	 * return array of all global variables 
	 */
	public ArrayList<Variable> getGlobalVariables(){
		return globalVariables;
	}
}
