package oop.ex6.Variable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.Expression;

/**
 * 
 * @author nir&yoni.corporation
 * this class in charge of building the variables.
 * the class will get a line represent the variable definition and
 * in case the definition is as supposed to be the factory will return 
 * a variable that fits to the request and add to the given array of variabels, else
 * throw some  VariableException.
 *
 */
public class VariableFactory {
	
	/**
	 * the function will get a line that represent one of
	 * the variable dealing options and if the line is ok mean if the line	
	 * is a legal statement of variable definition the factory will return
	 * a variable
	 * @param line -represent the given line.
	 * @param variables - an array of variables, to add the new variables to.
	 * @return - variables the update  array of  variables
	 * @throws VariableException
	 */
	

	public static ArrayList<Variable> variableFactory(ArrayList<String> splitLine,ArrayList<Variable> variables  
			,ArrayList<Variable> variablesToCompare) throws VariableException{
	
		//Initialize all the parameter of the function
		AlreadyInitializeVariable InitialiazeVariable = new  AlreadyInitializeVariable();
		NewVariableLine newVariables = NewVariableLine.instance();
		final int RETURN_LINE = 2 ;
		final int FIRST_WORD = 0;
		
		Expression type = Expression.VARIABLE_TYPE;
		Expression name = Expression.VARIABLE_NAME;
		Expression returnLine = Expression.RETURN;
		Pattern pat ;
		Matcher match;

		// check if the line is a return line
		if (splitLine.size() == RETURN_LINE){
			
			pat = Pattern.compile(returnLine.getExpression());
			match = pat.matcher((splitLine.get(FIRST_WORD)+splitLine.get(1)));
			// check if the line is a return line
			if (match.matches() ){
				return variables;
			}
		}

		pat = Pattern.compile(type.getExpression());
		match = pat.matcher(splitLine.get(FIRST_WORD));
		
		//check if the line start with - final or some other type name. 
		if (match.matches() || (splitLine.get(FIRST_WORD)).equals("final")){
			 //create all the new variable that are in the line
			newVariables.NewVariable(splitLine, variables,variablesToCompare);
			return variables;
		}
	
		pat = Pattern.compile(name.getExpression());
		match = pat.matcher(splitLine.get(FIRST_WORD));
		//check if the line is a paramter defnition line
		if (match.matches()){
			// define the given parmeter
			InitialiazeVariable.setValueToVariable(splitLine, variables,variablesToCompare);
			return  variables;
		}
		//if non of the above is true throw VariableException
		throw new VariableException("Unsupported variable line");
	}

}

	

