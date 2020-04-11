package JavaGame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class register 
{
	public String name=null;
	public String password=null;
	
	public register()
	{
		name=JOptionPane.showInputDialog(null, "Enter Your Userame", "Register", JOptionPane.QUESTION_MESSAGE);
		password=JOptionPane.showInputDialog(null, "Enter Password", "Register", JOptionPane.QUESTION_MESSAGE);
		saveID();
	}
	public void saveID()//saves name and password into the related .txt file
	{
		try 
		{
			PrintWriter pw = new PrintWriter(new FileOutputStream("Users.txt", true));//preventing overwrite
			pw.printf("%s %s\n", name, password);
			pw.close();
		}
		catch (IOException e) 
		{
			System.out.printf("Error writing to file '%s'\n", "Users.txt");
		}
	}
}
