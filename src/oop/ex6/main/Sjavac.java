package oop.ex6.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import oop.ex6.FileOrganizer.FileOrganizer;

/**
 * represent the sjava compiler 
 * @author nir&yoni.corporation
 */

public class Sjavac {
/**
 * call the parser that checks all line in the given file
 * if there were exception print 1/2 and the given exception massage 
 * or print 0 if the file is valid 
 */
	public static void main(String[] args){

		try{
			File file  = new File(args[0]); 
			ArrayList<ArrayList<String>> fileArray = FileOrganizer.arrayOfFileLines(file);
		
			Parser.parse(fileArray);
			System.out.println(0);
		// file name isn't legal 
		}catch(IOException e){
			System.out.println(2);
			System.out.println("illegal file name");
		// other exceptions 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
