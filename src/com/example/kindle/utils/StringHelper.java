package com.example.kindle.utils;

import java.util.StringTokenizer;

/**
 * Helper class for string operations.
 */
public class StringHelper
{
	/**
	 * Splits the string into parts considering the delimiter given as parameter.
	 * 
	 * @param str String to be split.
	 * @param delimiter String used as delimiter.
	 * 
	 * @return Array of strings.
	 */
	public static String[] split(final String str, final String delimiter) 
	{
		StringTokenizer st = new StringTokenizer(str, delimiter);
	    String[] splitlist = new String[st.countTokens()];
	    
	    for (int i = 0; i < splitlist.length; i++)
	    {
	    	splitlist[i] = st.nextToken();
	    }
	    
	    return splitlist;
	}
}
