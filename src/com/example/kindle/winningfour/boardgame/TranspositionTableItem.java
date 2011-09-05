package com.example.kindle.winningfour.boardgame;

public class TranspositionTableItem
{
	static final int FailLow    = 0;
	static final int ExactValue = 1;
	static final int FailHigh   = 2;
	static final int Unknown    = 100000;

	public int hashCode()
	{
		return 0;
	}

	public boolean equals( Object obj )
	{
		return true;
	}

	public int depth;
	public int best;
	public int score;
	public int range;
}
