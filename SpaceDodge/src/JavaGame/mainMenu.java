package JavaGame;

public enum mainMenu 
{
	one("(1)", "Register\n"), 
	two("(2)", "Login\n"), 
	three("(3)", "Play\n"),
	four("(4)", "Scores\n"),
	zero("(0)", "Exit\n");
	
	private final String num;
	private final String desc;
	
	mainMenu(String number, String description)
	{
		num=number;
		desc=description;
	}
	
	public String getNum()
	{
		return num;
	}
	
	public String getDesc()
	{
		return desc;
	}
}
