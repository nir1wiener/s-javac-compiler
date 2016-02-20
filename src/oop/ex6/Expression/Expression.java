package oop.ex6.Expression;

/**
 * * @author nir&yoni.corporation
 * this class represent all the expression that 
 * will be match to the file to check if the file 
 * holds one of the given expression  
 * 
 */

public enum Expression {
	
	
	OPERATORS ( "\\s*[=|{|}|;|\\]|\\[|,|)|(]|,|\"|'\\s*" ),
		
	BOOLEAN ( "\\s*(false|true|\\d+|\\d+.\\d+)\\s*" ),
	
	VARIABLE_TYPE("\\s*(int|double|String|boolean|char)\\s*"),
	
	VARIABLE_NAME("\\s*([a-zA-Z][a-zA-Z_0-9]*|[_][a-zA-Z_0-9]+)\\s*"),

	METHOD_NAME("\\s*[a-zA-Z][a-zA-Z0-9_]*\\s*"),
	
	INT("\\s*[-]?\\s*\\d+\\s*"),
	
	DOUBLE("\\s*[-]?\\s*\\d+([.]\\d)?\\d*\\s*"),
	
	STRING("\\s*\"[\\w\\W]*\\s*.\\s*"),
	
	CHAR("\\s*\'\\s*[\\w\\W]\\s*\'\\s*"),
	
	RETURN("\\s*return\\s*[;]\\s*"),
	
	IF_WHILE("\\s*(if|while)\\s*"),
	
	END_OF_LINE("\\s*[[{]|[}]|[;]]\\s*"),
	
	EMPETY_LINE("\\s*"),
	
	COMMENT("\\s*//.*"),
	
	METHOD_DEFINITION("((\\s*(void)\\s+[a-zA-Z][a-zA-Z0-9_]*\\s*[(](\\s*(final\\s+)?"
			+ "(int|double|String|boolean|char)\\s+([a-zA-Z][a-zA-Z_0-9]*|"
			+ "[_][a-zA-Z_0-9]+)\\s*[,])*\\s*(final\\s+)?(int|double|String|boolean|char)"
			+ "\\s+([a-zA-Z][a-zA-Z_0-9]*|[_][a-zA-Z_0-9]+)"
			+ "\\s*[)]\\s*[{]\\s*))|(\\s*(void)\\s+[a-zA-Z][a-zA-Z0-9_]*\\s*[(]\\s*[)]\\s*[{]\\s*)");
	
	
	private final String expression; 
	
	/*
	 * private constructor, set the value of the data expression
	 * according to the given enum.
	 */
	private Expression(String exp){	
		this.expression = exp;

	}
	
	/**
	 * get the expression that the given enum hold's
	 * @return - the expression of the given enum
	 */
	public String getExpression(){
		return this.expression;
	}

}