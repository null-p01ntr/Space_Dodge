package JavaGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

public class scoreBoard
{    
    ArrayList<String> Scores=new ArrayList<String>(); //main string variable to hold usernames and scores 
    
    String exit=null;
	int e=0;//exit command
	
	scoreBoard()
	{
		Scores.add("\n");
		readScoreFile("Scores.txt");
		Scores.add("\n(0) For main menu");
		String list="";
		for(int i=0;i<Scores.size();i++)
			list+=Scores.get(i);
		do
		{
			exit=JOptionPane.showInputDialog(null, list, "Scores", JOptionPane.INFORMATION_MESSAGE);			
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
	        	Scores.add(line+"\n");
	        }
	        Collections.sort(Scores,Collections.reverseOrder());
	        System.out.printf("%s", Scores.get(2));
	        
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
