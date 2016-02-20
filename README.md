# s-javac-compiler
nir wiener

=============================
=  File description    =
=============================

Expression package:
--------------------

Expression.java:
=================
this class represent all the expression that 
will be match to the file to check if the file 
holds one of the given expression .

FileOrganizer package:
-------------------- 

FileOrganizer.java:
===================
this class organize a given file such that every empty line or
a comment line will be deleted.and all the other line
will be add to an array list that each cell hold an array list 
that each cell is a variable/charcter/name/opretor. in the line.


FileOrganizerException.java:
=============================
this class throw the given Exception and
the according msg.

main package:
--------------------  

parser.java:
===================
parser class is the class that are responsible of taking the organized 
file that been put into array 
and to check if the file is as we expected using all other packages.
 if the file is not written as expected
this class will throw the right Exception(FileOrganizerException,VariableException, 
MethodException, IfOrWhileException). 

Sjavac.java:
===================
contains the Sjavac class.
the class call the parser and print
will print 0 if the file code ok
will print 1 if the file code not is not legal
will print 2 if the file cant be reach
and will print the error massage that has been added.


Method package:
--------------------  


Block.java:
===================
this class represent a block inside a method or inside if or while statement 
the main function is 'isBlockGood' that checks if all lines in this block
is as we will expected.


IfOrWhileException.java:
=========================
this class throw the given Exception and
the according msg.


Method.java:
===================
this class represent a method in the Sjavac file.

MethodException.java:
=====================
represent the Method exception

MethodFactory.java:
===================
A class that represents a method Factory
when her main function is createMethod that create a new method after checking
that the method definition is good.
 
IfOrWhileStatement.java:
===================
represent a if or while statement, all local variables,
all global variables and all method
that are in the if or while 

CallMethod.java:
===================
represent a method call check's if the call
is valid.


Variable package:
--------------------  

AlreadyInitializeVariable.java:
================================
This class given a line change or set the value of the variable that are in the line
according to the value that are in the line.

NewVariableLine.java:
================================
This class given a line create all the variable that
are in the line according to the variable type


Variable.java:
================================
this class represent a given variable in the file.
containing all the information about the variable.

VariableException.java:
================================
represent the Variable exception

VariableFactory.java:
================================
this class in charge of building the variables.


==================
=   Design     =
==================

we Design our program in the following way:
Building made in packages every package has its own purpose and in
the end is the main(parser && Sjavac) that
connect all the packages together.

we design our program such that FileOrganizer just reOrganize the file.
the variable package handle all the initialzition and creation of variables.
the Method package  handle the checking of the method block and defnition.
and correcntes.
the Expression package hold all the needed regex in the program.

a word about the building thought, at first we wanted to build the regex design
by in each class initialize it's needed regex, but than we saw that there is 
more than a few regex that are used by different classes.
so we decided to create an enum class that will hold all the expression
that are use in the program( a pool of regex) that way each class 
that need to use an expression can import that class and use is expression  

  
 ---- OOP Mechanisms ----
  
  * Composition: The use of composition is mainly implemented in the Method 
  	package which composes a variable list and a block.
				 
  * Inheritance: inheritance is implemented in our program  in the Exception 
   method of each package.

  
  * Overriding: Override is mainly used in the Exception  method.
 
  *singleton :we decided to design the NewVariableLine.class as a singleton because
	it can be called a lot from the variable factory and in that way we will 
	save running time.
  
  * Immutablilty: Immutability is mainly defined in the different components. 
    A variable is considered as immutable because when verifying ,the value 
    of a variable is unimportant, but the type  of a variable remains forever unchanged.
  	Also, a method's signature is immutable, once one declared a method, it has one 
  	signature and a list of variables that nothing can change.
  				    
  *Factory class: are implement in the variable package and method package. 
  
=============================
=  Implementation details =
=============================

program flow:
---------------
first we reading all the file into a buffer and than we add all the non
empty or comment line to an array.
than we split all the lines to two Arrays:
a globalLine array.
and an array of array of methods.

than we create all the global variable by running on the globalLine array.
than we create all the method's  that are defined, meaning there name there
parameter and her block.

and than for each method we run on all her inside block,and check that all 
the variable's and method's call are legal and valid.
-----------------

---- Programming Tools ----
  
  * Exceptions: Exceptions are also used in our design (see exceptions hierarchy tree).
  
  * Regular Expressions: Regular expressions are widely used in the design as they are the tool, 
    in which we use for parsing any kind of statement in order to determine what is it's 
  	purpose and its compatibility with s-java.
  
  * Streams: In our program we use the Buffered Reader as a stream to read the line of commands
    from the s-java file received in the Sjavac main method.
  
  * Serialization: Serialization is used in the Exceptions as they are all serialized.

---- Java Specifics ----
  
  * Javadoc: We used javadoc to create a convenient API for the common user of our program.
  
  * Collections: From java's Collections we used array lists 
  
  * Packages: in order to create modulartiy of the program
  
  * Enums: create in order to make a pool of regex.
   


====================================
= EXCEPTION HANDLING =
====================================

In the program we handle the exception in the following way:

First we have a 4 classes of exception:
----------------------------------------

FileOrganizerException: handle the exception while reading the file.

VariableException: handle the exception  when trying to create a new variable or
try to compare some data to a variable.

IfOrWhileException: handle the exception when checking if the if or while block is
valid.

MethodException: handle the  exception when trying to check if the method
is valid and is content valid to.
----------------------------------------
we  design the Exception in way that each Exception tell you
exactly what kind of problem the file have.
meaning each Exception throw an according msg that 
will tell you what kind of problem the file have,
and an explantion to the exact problem.

than if one of the 4 above Exception is throw by the parser
we will catch it in the Sjavac.class and will print 1
and the problem that accord.

and if we will have an IOException we also  catch it in the Sjavac
and print 2 and the problem that accord, meaning illegal file name.

The hierarchy exception tree: 
  
 Sjavac:
  		|-fileOrganizer: 
  		|	           |-fileOrganizerException
  		|	           |- IOException 		  
  		|                        
  		|		   
  		|          
  		|		   
  		|-      parser:
  		|              |- Variable:
  		|						  |-VariableException						  
		|		
		|			   |- method :
		|						  |- VariableException
		|                         |- MethodException	
		|                         |- IfOrWhileException	
		|                         |	
		|                         |	
		
===================
====================================

* In order to modify my code to handle a new type of variable
i just need to add the type to the VARIABLE_TYPE expression in the 
Expression class.
than we also need to add an regex to check if the value is valid.
and in the Variable.class in the typeMatchValue method we will add
a case that check's for the given new type.

*adding a switch statement :
first we will add to new regex : to check if the word is a switch\case in the 
Expression class.
then as we do with if or while block, we will create a method
that checks if the block of switch statement is valid meannig 
if all the variable is defiend and we will check each "case" block
if it is valid to by creating a block from each case and run the method
checkBlock, as we do with if or while block.

* Importing the methods and global variable of one s-Java file to the other :
first we will add a regex to the enum class that check's if the import line is valid.
we will create a new class that when we get a line of import will take the filename,
and run on this file and will create two list as created in the parser of all global
variables and all methods,then we will run the rest of the original file and if we will
get an unrecognized method or variable we will check if one of the two list that we 
created is holding this variable or method. 

=============================
= Expression =
=============================

1.METHOD_DEFINITION:
			("((\\s*(void)\\s+[a-zA-Z][a-zA-Z0-9_]*\\s*[(](\\s*(final\\s+)?(int|double|String|boolean|char)
			\\s+([a-zA-Z][a-zA-Z_0-9]*|[_][a-zA-Z_0-9]+)\\s*[,])*\\s*(final\\s+)?
			(int|double|String|boolean|char)\\s+([a-zA-Z][a-zA-Z_0-9]*|[_][a-zA-Z_0-9]+)\\s*[)]\\s*[{]\\s*))|     
			(\\s*(void)\\s+[a-zA-Z][a-zA-Z0-9_]*\\s*[(]\\s*[)]\\s*[{]\\s*)")

* this expression check's if a line of 	defending a method is valid.
meaning:
void method_name ( final Variable_type variable_name , [(can be 0 - N times)] ){

2.VARIABLE_TYPE("\\s*(int|double|String|boolean|char)\\s*"):

*this expression check's if the given word is a valid variable type.
meaning:
 int , boolean [will pass]
 iNt [will not pass]



                
                

