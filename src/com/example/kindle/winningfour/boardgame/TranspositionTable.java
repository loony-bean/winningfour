package com.example.kindle.winningfour.boardgame;

import java.util.HashMap;

import com.example.kindle.winningfour.App;

public class TranspositionTable
{
	public TranspositionTable()
	{
		this.data = new HashMap();
	}
	
	public int lookup(int key, int depth, int alpha, int beta)
	{
		int result = TranspositionTableItem.Unknown;
		TranspositionTableItem item = (TranspositionTableItem) this.data.get(Integer.valueOf(key));

		if (item != null && (item.depth >= depth))
		{
			switch (item.range)
			{
			case TranspositionTableItem.FailLow:
				if (item.score <= alpha)
				{
					result = alpha;
				}
				break;
			case TranspositionTableItem.FailHigh:
				if (item.score >= beta)
				{
					result = beta;
				}
				break;
			case TranspositionTableItem.ExactValue:
				result = item.score;
			}
		}

		return result;
	}
	
	public void add(int key, int depth, int score, int range)
	{
		TranspositionTableItem item = (TranspositionTableItem) this.data.get(Integer.valueOf(key));

		if (item == null)
		{
			//if (hashtable.size() >= model.getHashelements())
			//	return;

			item = new TranspositionTableItem();	          	
			item.score = score;
			item.depth = depth;
			item.range = range;

			this.data.put(Integer.valueOf(key), item);
		}  // entry does exist but with smaller distance
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
