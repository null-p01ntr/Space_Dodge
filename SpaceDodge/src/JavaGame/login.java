package JavaGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class login
{
	static String loginName;
	static boolean Failed=true;
	String password;
	ArrayList<String>nameArr=new ArrayList<String>();
	ArrayList<String>passArr=new ArrayList<String>();
	
	public login() throws IOException //only login
	{
		loginName=JOptionPane.showInputDialog(null, "Enter Your Userame", "Login", JOptionPane.QUESTION_MESSAGE);
		readUserFile("Users.txt"); 
		if(nameExists(loginName))
		{
			do
			{
				password=JOptionPane.showInputDialog(null, "Hello "+loginName+", please enter your Password", "Login", JOptionPane.QUESTION_MESSAGE);
				if(passwordMatches(password))
				{
					Failed=false;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wrong Password", "ERROR", JOptionPane.ERROR_MESSAGE);
					Failed=true;
				}	
			}while(Failed)	;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Username does not exist please register", "ERROR", JOptionPane.ERROR_MESSAGE);
			loginName=null;
			Failed=true;
		}
	}
	public login(String string) throws IOException, InterruptedException //login and play
	{

		loginName=JOptionPane.showInputDialog(null, "Enter Your Userame", "Login", JOptionPane.QUESTION_MESSAGE);
		readUserFile("Users.txt");
		if(nameExists(loginName))
		{
			do
			{
				password=JOptionPane.showInputDialog(null, "Welcome "+loginName+", please enter your Password", "Register", JOptionPane.QUESTION_MESSAGE);
				if(passwordMatches(password))
				{
					Failed=false;
					movingSpaceCraft  spaceDodge=new  movingSpaceCraft(); //play	
					spaceDodge.createGUI();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wrong Password", "ERROR", JOptionPane.ERROR_MESSAGE);
					Failed=true;
				}	
			}while(Failed)	;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Username does not exist please register", "ERROR", JOptionPane.ERROR_MESSAGE);
			loginName=null;
			Failed=true;
		}
	
	}
	public void readUserFile(String FileName) //reading user informations(usernames and passwords) from the related .txt file
	{		
		ArrayList<String>accArr=new ArrayList<String>();
		int wordCount=0;
		
		File file = new File(FileName);
	    Scanner input=null;
		try 
		{
			input = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
	 
	    while (input.hasNext()) 
	    {
	      String word  = input.next();
	      accArr.add(word); //adding every word in to a array list
	      wordCount++;
	    }
	    
	    int lineCount=wordCount-1;
	    int k=1;
		for(int j=0;j<lineCount;j+=2) //separating the names and passwords(odd indexed ones are names and even ones are passwords
		{
			nameArr.add(accArr.get(j));
			passArr.add(accArr.get(k));
			k+=2;
		}
	}
	private boolean nameExists(String userName) //checking if the entered username exist 
	{
		for(int i=0;i<nameArr.size();i++)
		{
			if(userName.equals(nameArr.get(i)))
				return true;
		}
		return false;
	}
	private boolean passwordMatches(String userPass) //checking if the password matches with the entered username 
	{
		for(int i=0;i<passArr.size();i++)
		{
			if (userPass.equals(passArr.get(i))&&loginName.equals(nameArr.get(i)))
				return true;
		}
		return false;
	}
}
