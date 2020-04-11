package JavaGame;

public enum pickFighter 
{
	one("(1)", "Millennium Falcon\n"), 
	two("(2)", "Infinity Gauntlet\n"), 
	three("(3)", "Tardis\n");
	
	private final String num;
	private final String desc;
	
	pickFighter(String number, String description)
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
