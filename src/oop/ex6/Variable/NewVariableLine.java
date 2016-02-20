package oop.ex6.Variable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.Expression;

/**
 * @author nir&yoni.corporation
 * A singltone desgin  class.
 * This class given a line create all the variable that
 * are in the line according to the variable type
 * @param splitLine - A string[] represent an split line.
 * @param listOfVariable - ArrayList that will hold the Variable of the line
 * @throws VariableException - according to the given exception
 */

public class NewVariableLine {

	//initialize all the conatant vairable of the class

	private static Expression exp;
	private static Pattern pat;
	private static Matcher match;
	private static NewVariableLine singleNewVariableLine = new NewVariableLine();

	/*
	 * empty private constructor
	 */
	private NewVariableLine(){}

	/**
	 * 
	 * @return singleNewVariableLine the only instance of the class
	 */
	public static  NewVariableLine instance(){
		return singleNewVariableLine;
	}

	/**
	 * given an splitLine represent a line in the file the method will create all
	 * the new variable that are in the line or,if the variable is already exist or
	 * the line is un suproted throws VariableException.   
	 * @param splitLine - ArrayList<String> of a given line in the file
	 * @param listOfVariable - ArrayList<Variable> a list of variable of the most local method
	 * @param variablesToCompare - ArrayList<Variable> a list of variable that are not in the same
	 * block but global to the given line.
	 * @return -  listOfVariable update list of variable of  the given method.
	 * @throws VariableException
	 */
	public  ArrayList<Variable> NewVariable(ArrayList<String> splitLine, ArrayList<Variable> listOfVariable,
			ArrayList<Variable> variablesToCompare) throws VariableException{
		
		ArrayList<Variable> tmpListOfVariable = new ArrayList<Variable>();

		int curIndex = 0 ;
		boolean varIsFinal = false;
		int lineLength = splitLine.size();
		int curVariable = 0;

		// check's if the variable is final
		if (splitLine.get(curIndex).equals("final")){
			varIsFinal = true;
			curIndex += 1;
			isLineEnd(lineLength, curIndex);			
		}

		// the type of the variable.
		String varType = splitLine.get(curIndex);
		exp = Expression.VARIABLE_TYPE;
		pat = Pattern.compile(exp.getExpression());
		match = pat.matcher(varType);

		if(!match.matches()){
			throw new VariableException("no such varaible type");
		}

		curIndex = addNameToType(splitLine,curIndex,listOfVariable, varType,variablesToCompare
														,varIsFinal,tmpListOfVariable );
		//Will run until the end of line.
		while (true){

			// set the name after the = to all the previous declared variable.
			if (splitLine.get(curIndex).equals("=")){

				curIndex += 1;
				isLineEnd(lineLength, curIndex);
				// check if the name is valid to the variable type
				boolean canAssigenValue = false;
				// check's if the assigned value is a variable
				canAssigenValue = isValueIsVariable(tmpListOfVariable, splitLine.get(curIndex));
				if(!canAssigenValue){
					canAssigenValue = isValueIsVariable(listOfVariable,splitLine.get(curIndex));
				}
				if(!canAssigenValue){
					canAssigenValue = isValueIsVariable(variablesToCompare,splitLine.get(curIndex));
				}
				//check's if the assigned value is valid to the type
				if (tmpListOfVariable.get(0).typeMatchVar(splitLine.get(curIndex)) && (!canAssigenValue)){
					canAssigenValue = true;
				}
				//Defined all the cur variable to true
				if (canAssigenValue){	
					while ((curVariable) != tmpListOfVariable.size()){
						tmpListOfVariable.get(curVariable).setVarDefined(true);
						curVariable += 1;
						isLineEnd(lineLength, curIndex);
					}
					curIndex += 1;
					isLineEnd(lineLength, curIndex);
				}else{
					throw new VariableException("type not support value");
				}
			}
			if(splitLine.get(curIndex).equals(";")){
				addAllVariableToList( tmpListOfVariable, listOfVariable);
				return listOfVariable; 
			}
			// check if there is new variable to create
			if (splitLine.get(curIndex).equals(",")){
				curIndex = addNameToType(splitLine,curIndex,listOfVariable, varType,variablesToCompare
						,varIsFinal,tmpListOfVariable );	
			}else{ 
				throw new VariableException("unsupport assigned value line");
			}
		}
	}

	/*
	 * the function create a new variable and add it to the listOfVariable
	 * according to the number of paramter name that it is encuterd before
	 * it encuterd = or ;
	 */
	private int addNameToType(ArrayList<String> splitLine, int curIndex,
			ArrayList<Variable> listOfVariable, String varType,ArrayList<Variable> variablesToCompare
			,boolean varIsFinal,ArrayList<Variable> tmpListOfVariable ) throws VariableException{

		int lineLength = splitLine.size();
		exp = Expression.VARIABLE_NAME;
		pat = Pattern.compile(exp.getExpression());
		curIndex += 1; 
		isLineEnd(lineLength, curIndex);
		//will run an add all the parmeter until it will see = or ;
		while (true){

			Variable newVar = new Variable(varType,varIsFinal,null);
			String curName = splitLine.get(curIndex);
			match = pat.matcher(curName);

			if (match.matches()){
				// checks if that name is already in the file.
				checkIfNameAlreadyExist(curName,tmpListOfVariable);
				checkIfNameAlreadyExist(curName,listOfVariable);
				newVar.setVarName(splitLine.get(curIndex));
			}else{
				throw new VariableException("unsupported variable name");
			}
		 
			

			tmpListOfVariable.add(newVar);
			curIndex += 1;
			isLineEnd(lineLength, curIndex);
			if (!splitLine.get(curIndex).equals(",")){
				return curIndex;
			}
			curIndex +=1;
			isLineEnd(lineLength, curIndex);
		}
	}

	/*
	 * the function check's if the line as ended. 
	 */
	private boolean isLineEnd(int lineLength ,int curIndex) throws VariableException{
		if (lineLength  ==  curIndex) {
			throw new VariableException("un superted line");
		}
		return false;
	}
	
	/*
	 * the function given a name and array list of variable check's if 
	 * the checks if the variable is already exist
	 */
	private void checkIfNameAlreadyExist(String curName , 
			ArrayList<Variable>listOfVariable )throws VariableException{
		if (listOfVariable != null){

			for (Variable var : listOfVariable){
				if (var.getVarName().equals(curName)){
					throw new VariableException("variable name is already exist");
				}
			}
		}
	}

	/*
	 * given a list and a list to copy to, the method add all the obj of the list to 
	 * list tocopyto
	 * @param list
	 * @param listToCopyTo
	 */
	private void addAllVariableToList(ArrayList<Variable> list , ArrayList<Variable> listToCopyTo) 
			throws VariableException{

		for (Variable newVariable : list){
			if(newVariable.isVarFinal()&&!(newVariable.isVarDefined())){
				throw new VariableException("variable is final but not defined");
			}
			listToCopyTo.add(newVariable);
		}
		list = new ArrayList<Variable>();
	}
	
	/*
	 * this function return true if the variable name is exist in the list and defined else false
	 */
	private boolean isValueIsVariable(ArrayList<Variable> listOfVariables ,String varName){
		Variable curVar = null;
		for(Variable var : listOfVariables)  {
			if( var.getVarName().equals(varName)){
				curVar = var;
				break;
			}
		}
		if(curVar != null){
			int variable = 0 ;
			if(listOfVariables.get(variable).getVarType().equals
					((curVar.getVarType()))&&curVar.isVarDefined()){
				return true;
			}
		}
		return false;
	}
}

