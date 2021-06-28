package minesweeper;

public class CellManager
{
	private boolean flagFlag;
	private boolean openFlag;
	private boolean bombFlag;
	private boolean clearFlag;
	private int aroundBombNum;


	public CellManager()
	{
		flagFlag = false;
		openFlag = false;
		bombFlag = false;
		aroundBombNum = 0;

	}

	public void setBombFlag()
	{
		bombFlag = true;
	}

	public void takeBombFlag()
	{
		bombFlag = false;
	}

	public boolean getBombFlag()
	{
		return bombFlag;
	}

	public void setFlag()
	{
		flagFlag = true;
	}

	public void takeFlag()
	{
		flagFlag = false;
	}

	public boolean getflagFlag()
	{
		return flagFlag;
	}

	public void setOpenFlag()
	{
		openFlag = true;
	}

	public void takeOpenFlag()
	{
		openFlag = false;
	}

	public boolean getOpenFlag()
	{
		return openFlag;
	}

	public void addAroundBombNum()
	{
		aroundBombNum++;
	}

	public int getAroundBombNum()
	{
		return aroundBombNum;
	}

	public void setClearFlag()
	{
		clearFlag = true;
	}

	public boolean getClearFlag()
	{
		return clearFlag;
	}




}
