package vwr.project.voidbubble.test;
import java.io.*;

public class TestFileService
{
	// Main method
	public static void main (String args[])
	{
		//User's home directory
		String dir = System.getProperty("user.home");
		dir = dir + File.separatorChar;
		
		System.out.println(dir);
		
		// Stream to write file
		FileOutputStream fout;		

		try
		{
		    // Open an output stream
		    fout = new FileOutputStream (dir + "myfile.txt");

		    // Print a line of text
		    new PrintStream(fout).println ("hello world!");

		    // Close our output stream
		    fout.close();		
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to write to file");
			System.exit(-1);
		}
		// Stream to read file
		FileInputStream fin;		

		try
		{
		    // Open an input stream
		    fin = new FileInputStream (dir + "myfile.txt");

		    // Print a line of text from the file to the console
		    System.out.println(new BufferedReader(new InputStreamReader(fin)).readLine());

		    // Close our output stream
		    fin.close();		
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to read from file");
			System.exit(-1);
		}
	}
}