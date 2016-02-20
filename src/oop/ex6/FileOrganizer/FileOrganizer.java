package oop.ex6.FileOrganizer;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Expression.*;
	
/**
 * 
 * @author nir&yoni.corporation
 * this class organize a given file such that every empty line or
 * a comment line will be deleted.and all the other line
 * will be add to an array list that each cell hold an array list 
 * that each cell is a variable/charcter/name/opretor. in the line
 * 
 *
 */
public class FileOrganizer  {
	
	/**
	 * The function get a file read it to an array list,each index in
	 * the array represent a  an array list that represent line from the file, 
	 * and return the array list. 
	 * @param file- a given file to read from.
	 * @return organizedFile - a list of list  of lines from the file.
	 * @throws IOException - if the file was'nt found.
	 */
	public static ArrayList<ArrayList<String>> arrayOfFileLines(File file) throws IOException{
		
		//read all the file to the buffer.
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));

		
		ArrayList<ArrayList<String>> organizedFile = new ArrayList<ArrayList<String>>();
		String curLine;
		
		//will run on all the line's in the file.
		while ((curLine = bufferReader.readLine() )!= null){
		// for each line check if it empty or a comment line or else.	
			if (!checkLine(curLine)){
				ArrayList<String> newArrayLine = new ArrayList<String>();
				newArrayLine = editLine(curLine);
				organizedFile.add(newArrayLine);	
			}
			
		}
		bufferReader.close();
		return organizedFile;	
	}
	
	/*
	 * given a line the function check's if the line is 
	 * empty or a cooment if so return true. else false. 
	 */
	private static boolean checkLine(String line){
		
		Expression exp = Expression.COMMENT;
		Pattern pattern  = Pattern.compile(exp.getExpression());
		Matcher matcher = pattern.matcher(line);
		// if the line is a comment line.
		if (matcher.matches()){
			return true;
		}
		//update the pattern and matcher.
		exp = Expression.EMPETY_LINE;
		pattern  = Pattern.compile(exp.getExpression());
		matcher = pattern.matcher(line);
		//if the line is empty.
		if (matcher.matches()){
			return true;
		}
		return false;
	}
	
	/*
	 * given a line the function separate between each 
	 * string\operator that are in the line. and add to an array list
	 * and return the new array list.
	 */
	private static ArrayList<String> editLine(String line){
		
		Expression exp = Expression.OPERATORS;
		Pattern pattern  = Pattern.compile(exp.getExpression());
		Matcher matcher = pattern.matcher(line);
		
        String editLine = line;
     
        // for every subline that is an operator create a space from the
        //next string.
        while (matcher.find()){
            String subLine= line.substring(matcher.start(), matcher.end());
            editLine = editLine.replace(subLine," "+subLine+" ");
        }
                
        editLine  = editLine.replaceAll("\\s+"," ").trim();
        
        
   	 	String[] tmpList = editLine.split("\\s");
   	 	int index  = 0;
   	 	ArrayList<String> lineArray = new ArrayList<String>();
   	 	// for every string that was cut earlier attach it and add it to the new 
   	 	//list in the same cell
   	 	while (index < tmpList.length){
   	 		
   	 		if ((tmpList[index].equals("\""))||(tmpList[index].equals("\'"))){
   			 // the index that start an 
   			 int indexToChange =index;
   			 index += 1;
   			
   			 while ((tmpList.length > index)){
   				 
   				 if  ((tmpList[index].equals("\""))||(tmpList[index].equals("\'"))){
   					 tmpList[indexToChange] = tmpList[indexToChange] +" "+ tmpList[index];
   					 break;
   				 }
   				 tmpList[indexToChange] =  tmpList[indexToChange] +" "+ tmpList[index];
   				 index += 1;
   			}
   			 lineArray.add(tmpList[indexToChange]);
   			 index +=1;
   			 continue;
   		 }
   	 	 lineArray.add(tmpList[index]);
   		 index += 1;
   	  } 
        return lineArray;
	}


}
