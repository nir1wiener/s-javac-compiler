package oop.ex6.Variable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.Expression;

/**
 * 
 * @author nir&yoni.corporation
 * this class given a line change or set the value of the variable that are in the line
 * according to the value that are in the line.
 * throws VariableException else.
 */
public class AlreadyInitializeVariable {
	
	/**
	 * change the variable value or set it ,else throws some VariableException
	 * @param splitLine - represent a given line in the file
	 * @param listOfVariable - represent a list of variable that one of her is the variable that 
	 * are in the line.
	 * @throws VariableException
	 */            																								
	
	  private static final int VARIABLE_NAME = 0;
	  private static final int OPRETOR = 1;
	  private static final int VARIABLE_VALUE = 2;
	  private static final int END_OF_LINE = 3;
	  
	private Variable isVariableExsit (String name,ArrayList<Variable> listOfVariable,Variable varToUpdate)
			throws VariableException {
		for( Variable var : listOfVariable){
			if (var.getVarName().equals(name)){
    			varToUpdate = var;
    			// final variable can be defiend only once.
    			if ((varToUpdate.isVarDefined()) && (varToUpdate.isVarFinal())){
    				throw new VariableException("final variable already defined");
    			}
    			// find the match valid variable
    			return var;
    		}
    	}
		return null;
	}
	
	public void setValueToVariable(ArrayList<String> splitLine, ArrayList<Variable> listOfVariable,
			ArrayList<Variable> globalVariable) throws VariableException{
			
		final int LINE_SIZE = 4;
		int lineLength = splitLine.size();
		Variable varToUpdate = null;
		String name = splitLine.get(VARIABLE_NAME);
		
		// if the line is not legal line.    
		if (LINE_SIZE != lineLength){
			throw new VariableException("unsupported initialization value line ");
		}
		
		//checks that the VARIABLE_NAME is legal.
		Expression exp = Expression.VARIABLE_NAME;
		Pattern  pat = Pattern.compile(exp.getExpression());
		Matcher  match = pat.matcher(name);
    	//if the name is unsupported variable name
		
		if (!match.matches()){
    		throw new VariableException("unsupported variable name");
    	}
		
		varToUpdate = isVariableExsit(name, listOfVariable, varToUpdate);
		if(varToUpdate == null){
			varToUpdate = isVariableExsit(name, globalVariable, varToUpdate);
			if(varToUpdate == null){
				throw new VariableException("variable was'nt defiend");
			}
		}
		if (!splitLine.get(OPRETOR).equals("=")){
    		throw new VariableException("unsupported line, no equal opretor");
    	}
    	
		if (!varToUpdate.typeMatchVar(splitLine.get(VARIABLE_VALUE))){
    		throw new VariableException("unsupported variable value to the type");
    		
    	}
		
		if(!listOfVariable.contains(varToUpdate)){
			Variable localVAr = new Variable(varToUpdate.getVarType(), varToUpdate.isVarFinal(),
					varToUpdate.getVarName());
			localVAr.setVarDefined(true);
			
		}else{
			varToUpdate.setVarDefined(true);
		}
		
    	if (splitLine.get(END_OF_LINE).equals(";")){
    		return;
    	}
    	
    	throw new VariableException("unsupported end of line");
	}
}
