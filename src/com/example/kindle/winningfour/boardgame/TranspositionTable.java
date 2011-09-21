package com.example.kindle.winningfour.boardgame;

import java.util.HashMap;

import com.example.kindle.winningfour.App;

public class TranspositionTable
{
	public TranspositionTable()
	{
		this.data = new HashMap();
	}

	public TranspositionTableItem lookup(int key, int depth)
	{
		// TODO: make use of depth
		return (TranspositionTableItem) this.data.get(new Integer(key));
	}
	
	public void add(int key, int depth, int score, int range)
	{
		TranspositionTableItem item = (TranspositionTableItem) this.data.get(new Integer(key));

		if (item == null)
		{
			item = new TranspositionTableItem();
			item.score = score;
			item.depth = depth;
			item.range = range;

			this.data.put(new Integer(key), item);
		}
		else if (item.depth < depth)
		{
			item.score = score;
			item.depth = depth;
			item.range = range;
		}
	}
	
	public void destroy()
	{
		App.log("TranspositionTable::destroy");

		this.data.clear();
		this.data = null;

		App.log("TranspositionTable::destroy done");
	}
	
	private HashMap data;
}
