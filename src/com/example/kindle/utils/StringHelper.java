package com.example.kindle.utils;

import java.util.StringTokenizer;

public class StringHelper
{
	public static String[] split(String what, String token) 
	{
		StringTokenizer st = new StringTokenizer(what, token);
	    String[] splitlist = new String[st.countTokens()];
	    
	    for (int i = 0; i < splitlist.length; i++)
	    {
	    	splitlist[i] = st.nextToken();
	    }
	    
	    return splitlist;
	}
}
