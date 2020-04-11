package JavaGame;

import javax.swing.JOptionPane;
import java.io.IOException;

public class main 
{
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{		
		String selectionInput=null;
		int selectionInputINT=0;
		movingSpaceCraft  spaceDodge=new  movingSpaceCraft();	
		
		do
		{	
			String Menu="";
			for(mainMenu option: mainMenu.values())
			{	
				Menu+=option.getNum();
				Menu+=option.getDesc();
			}
			
			selectionInput=JOptionPane.showInputDialog(null, Menu, "Space Dodge || Logged in as: "+login.loginName, JOptionPane.PLAIN_MESSAGE);			
			selectionInputINT=Integer.parseInt(selectionInput);
			
			if(selectionInputINT==1) //register
			{
				new register();
			}
			else if(selectionInputINT==2) //only login
			{
				new login();
			}
			else if(selectionInputINT==3) //play game
			{	
				if(login.Failed) //ask player to login before playing if s/he haven't logged in successfully and then start the game
				{	
					new login("play"); // login and play
				}
				else //start the game if the player already logged in successfully
				{
					spaceDodge.createGUI();
				}
			}
			else if(selectionInputINT==4) //score board
			{
				new scoreBoard();
			}
		}while(selectionInputINT!=0);
	}
}
