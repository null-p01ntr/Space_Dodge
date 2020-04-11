package JavaGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class movingSpaceCraft implements KeyListener
{
	private int move=20;	//default move unit ~speed
	private int score=0;	//score counter
	boolean addPoint=true;
  	
  	//WINDOW
	private JFrame frame;
	//Size of the window
  	private int xFrameSize=800;
	private int yFrameSize=1000;
	//Position of the window
	private int xFramePos=600; 
	private int yFramePos=75;
	
	//CHARACTER
	private JLabel character;
	private Icon fighter;
	private String pickedFighter=null;//file name of the fighter picked by player
	//Size of the character
  	private int xCharSize=120;
  	private int yCharSize=160;
  	//Position of character
  	private int xCharPos=(xFrameSize-xCharSize)/2-10;
  	private int yCharPos=700;
  	
  	//FIRE
  	boolean FIRE=false; 
  	private JLabel fire;
	private Icon blast;
	//Size of the fire blast
  	private int xFireSize=30;
  	private int yFireSize=45;
  	//Position of fire blast
  	private int xFirePos=xCharPos+xCharSize/2-15;
  	private int yFirePos=yCharPos+yCharSize/2-10;
  	
  	//LIVES
  	private int lives=3; //live counter
  	private JLabel live;
  	private Icon hearts;
  	//Size of hearts
  	private int xLiveSize=200;
  	private int yLiveSize=45;
  	//Position of hearts
  	private int xLivePos=575;
  	private int yLivePos=840;
  	
  	//OBSTACLE
  	private JLabel obstacle;
	private Icon meteor;
	//Size of the obstacle
	private int xObsSize=125;
	private int yObsSize=125;
	//Position of obstacle
	private int xObsPos=-xObsSize;
	private int yObsPos=-yObsSize;
	
	//LEVELUP
	private int level=1;//level counter
	private JLabel levelup;
	private Icon up;
	//Size of the level up
	private int xLUSize=347;
	private int yLUSize=50;
	//Position of level up
	private int xLUPos=(xFrameSize-xLUSize)/2-10;
	private int yLUPos=-100;
	
	//BACKGROUND
	private JLabel background;
	private Icon stars;
	//Size of background
	private int xBackSize=xFrameSize;
	private int yBackSize=13456;
	//Position of obstacle
	private int xBackPos=0;
	private int yBackPos=-12456;
	
	public void createGUI() throws IOException, InterruptedException
  	{	
		//CHARACTER
		pickChar(); 
		fighter=new ImageIcon(getClass().getResource(pickedFighter));
		character = new JLabel(fighter);  
		character.setBounds(xCharPos, yCharPos, xCharSize, yCharSize);
		//FIRE
		blast=new ImageIcon(getClass().getResource("fire.png"));
		fire = new JLabel(blast);
		fire.setBounds(xFirePos, yFirePos, xFireSize, yFireSize);
		//LIVES
		hearts=new ImageIcon(getClass().getResource("hearts.png"));
		live = new JLabel(hearts);  
		live.setBounds(xLivePos, yLivePos, xLiveSize, yLiveSize);
		//OBSTACLE
		meteor=new ImageIcon(getClass().getResource("obs.png"));
		obstacle = new JLabel(meteor);
		obstacle.setBounds(xObsPos, yObsPos, xObsSize, yObsSize);
		//LEVELUP
		up=new ImageIcon(getClass().getResource("levelup.png"));
		levelup = new JLabel(up);
		levelup.setBounds(xLUPos, yLUPos, xLUSize, yLUSize);
		//BACKGROUND
		stars=new ImageIcon(getClass().getResource("back.jpg"));
		background = new JLabel(stars);  
		background.setBounds(xBackPos, yBackPos, xBackSize, yBackSize);
  		//WINDOW
  		frame = new JFrame("Space Dodge || Playing as: "+login.loginName);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(xFramePos, yFramePos, xFrameSize, yFrameSize);
		frame.add(live);
		frame.add(character);
		frame.add(fire);
		frame.add(obstacle);
		frame.add(levelup);
		frame.add(background);
		frame.addKeyListener(this);
		frame.setVisible(true);
		frame.setResizable(false); //preventing user from change the winow's size
				
		while(lives>0)
		{
			Random randomPos= new Random();
			xObsPos=randomPos.nextInt(xFrameSize-xObsSize);//setting meteors starting point randomly 
			
			while(yObsPos<yFrameSize)//until the meteor goes out of the screen 
			{  
				Thread.sleep(50);
				//move meteor down
				frame.add(obstacle);
				yObsPos+=move;
				obstacle.setBounds(xObsPos, yObsPos, xObsSize, yObsSize);
				//firing
				if(FIRE)
					fire();
				if(yFirePos<=-50)
				{
					rePosBlast();
					FIRE=false;
				}
				//hit detection
				if(Hit())
				{
					yObsPos=yFrameSize;	//remove meteor
					yFirePos=-50;		//remove fire blast
					FIRE=false;
					score++;			//extra point for hitting target
					addPoint=true;
				}
				//crash detection
				if (Crash())
				{ 
					yObsPos=yFrameSize; //remove meteor
				  	lives--;
				  	xLivePos=xLivePos+75;
				  	live.setBounds(xLivePos, yLivePos, xLiveSize, yLiveSize);
				  	addPoint=false; //dont give point if crashed
				}
				else
				addPoint=true;
				//moving background
				frame.add(background);
				yBackPos+=level+5;
				background.setBounds(xBackPos, yBackPos, xBackSize, yBackSize);
			}
			rePosBlast();	//setting
			if(addPoint)	//add point if round passed successfully
				score++; 
			yObsPos=-50;	//setting meteor's position back to top
				
			if (score%5==0&&score!=0&&level<=6&&addPoint)//level up after 50 points (stops at level 7)
				levelUp();
		}
		saveScore();
		//end game window
		JOptionPane.showMessageDialog(null, "Highest Level: "+level+"\nScore: "+score*10, "Game Over", JOptionPane.WARNING_MESSAGE);
  	}
	public void moveChar(int xNewCharPos,int yNewCharPos) //main moving function for character
    {
  		character.setBounds(xNewCharPos, yNewCharPos, xCharSize, yCharSize);
    }
  	public void moveToLeft() //moving to left
    {
    	xCharPos=xCharPos-move;
    	moveChar(xCharPos, yCharPos);
    	if(!FIRE) //reset the position of the fire blast if it hasn't fired
    		rePosBlast();
    }
    public void moveToRight() //moving to right
    {
    	xCharPos=xCharPos+move;
    	moveChar(xCharPos, yCharPos); 
    	if(!FIRE) //reset the position of the fire blast if it hasn't fired
    		rePosBlast();
    }
  	private void pickChar() //customization feature
  	{
  		String picked=null;
		int p=0;
		String pick="";//fighter options
		for(pickFighter option: pickFighter.values())
		{	
			pick+=option.getNum();
			pick+=option.getDesc();
		}
		
		picked=JOptionPane.showInputDialog(null, pick, "Pick Your Fighter", JOptionPane.INFORMATION_MESSAGE);			
		p = Integer.parseInt(picked);
		
		if(p==1) //Millennium Falcon
		{
			pickedFighter="mf.png";
		}
		else if(p==2) //Infinity Gauntlet
		{
			pickedFighter="thanos.png";
		}
		else if(p==3) //Tardis
		{	
			pickedFighter="tardis.png";
		}
	}
  	private void fire() //move fire blast image to forward (will repeat in the meteor's while loop at the top)
    {  			
  		frame.add(fire);
		yFirePos-=(move+5);
		fire.setBounds(xFirePos, yFirePos, xFireSize, yFireSize);
    }
  	private void rePosBlast() //resets the position of the fire blast
  	{
  		xFirePos=xCharPos+xCharSize/2-15; 
		yFirePos=yCharPos+yCharSize/2-10;
		fire.setBounds(xFirePos, yFirePos, xFireSize, yFireSize);
  	}
  	private boolean Hit() //impact detection between fire blast and the obstacle
  	{
    	if(
  			  yFirePos<=yObsPos+yObsSize  &&  
  			  xFirePos+xFireSize>=xObsPos &&
  			  xFirePos<=xObsPos+xObsSize  
  		  ) 
  		  return true;
  	  else
  		  return false;
  	}
  	public void levelUp() throws InterruptedException //add extra challenges to make the game harder
    {
    	level++;
    	move+=5;						//increase the speed of the meteor and the character
    	yCharPos=yCharPos-(move+10);	//move character up
    	moveChar(xCharPos,yCharPos);
    	//flash "level up"
    	yLUPos=250;
    	levelup.setBounds(xLUPos, yLUPos, xLUSize, yLUSize);
    	Thread.sleep(450);
    	yLUPos=-100;
    	levelup.setBounds(xLUPos, yLUPos, xLUSize, yLUSize);
    }
    public void saveScore()//saving score to the related .txt file with the logged in username
    {
  	  try 
  		{
  			PrintWriter pw = new PrintWriter(new FileOutputStream("Scores.txt", true));//preventing overwrite
  			pw.printf("%s %d\n", login.loginName, score*10);
  			pw.close();
  		}
  		catch (IOException e) 
  		{
  			System.out.printf("Error writing to file '%s'\n", "Scores.txt");
  		}
    }
    public boolean Crash() //impact detection between character and the obstacle 
    {
    	int Threshold=20; //a little help to the player ;)
    	if(
  			  yCharPos+Threshold<=yObsPos+yObsSize  &&
  			  yCharPos+yCharSize-Threshold>=yObsPos &&
  			  
  			  xCharPos+xCharSize-Threshold>=xObsPos &&
  			  xCharPos+Threshold<=xObsPos+xObsSize  
  		  ) 
  		  return true;
  	  else
  		  return false;
    }
@Override
  	public void keyPressed(KeyEvent event) {
  	// TODO Auto-generated method stub
  	String whichKey=KeyEvent.getKeyText(event.getKeyCode());
  	
  	if(whichKey.compareTo("Left")==0)
  	{
  		if(xCharPos-20>=-10) 
  		{
  			moveToLeft();
  		}
  	}
  	else if(whichKey.compareTo("Right")==0)
  	{
  		if(xCharPos+2*20<xFrameSize-xCharSize) 
  		{
  			moveToRight();
  		}
  	}
  	else if(whichKey.compareTo("Space")==0)
  	{
  		FIRE = true;
  	}
  }
@Override
	public void keyReleased(KeyEvent event) {
	// TODO Auto-generated method stub
  }
  @Override
  	public void keyTyped(KeyEvent event) {
  	// TODO Auto-generated method stub  	
  }
 }
