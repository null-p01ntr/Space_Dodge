package JavaGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class scoreBoard
{    
    String Scores = ""; //main string variable to hold usernames and scores 
    
    String exit=null;
	int e=0;//exit command
	
	scoreBoard()
	{
		readScoreFile("Scores.txt");
	    Scores+="\n(0) For main menu";
	   
		do
		{
			exit=JOptionPane.showInputDialog(null, Scores, "Scores", JOptionPane.INFORMATION_MESSAGE);			
			e = Integer.parseInt(exit);
		}while(e!=0);
	}
	public void readScoreFile(String FileName) //reading scores from related .txt file
	{
		String line = null;
		try 
	    {
	        FileReader fileReader = new FileReader(FileName);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        while((line = bufferedReader.readLine()) != null) 
	        {
	        	Scores+=line;
	        	Scores+="\n";
	        }
	        bufferedReader.close();         
	    }
	    catch(FileNotFoundException ex) 
	    {
	        System.out.printf("The file '%s' is not found", FileName);                
	    }
	    catch(IOException ex) 
	    {
	        System.out.printf("Error reading file '%s'\n", FileName);                  
	    }
	}
}
