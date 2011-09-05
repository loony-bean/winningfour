package com.example.kindle.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.example.kindle.winningfour.App;

/**
 * Helper class for file operations.
 */
public class FileHelper
{
	/**
	 * Writes string data into file.
	 * 
	 * @param filename Name of the file to be used.
	 * @param text Test to be written into file.
	 * @param append Indicates if the file should be cleared before the write. 
	 */
	public static void write(final String filename, final String text, boolean append)
	{
		FileWriter writer = null;
		try
		{
			File optfile = new File(System.getProperty("kindlet.home"), filename);
			writer = new FileWriter(optfile, append);
			writer.write(text);
		}
		catch (IOException e)
		{ 
			App.log("FileHelper::writeString exception for file " + filename); 
		}
		finally
		{ 
			if (writer != null)
			{ 
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				} 
			} 
		}
	}

	/**
	 * Reads string array from file.
	 * 
	 * @param filename Name of the file to be read.
	 * 
	 * @return String array read from the file.
	 */
	public static String[] read(final String filename)
	{
		ArrayList list = new ArrayList();
		BufferedReader reader = null;
		try
		{
			File file = new File(System.getProperty("kindlet.home"), filename);
			reader = new BufferedReader(new FileReader(file));

			String next = null;
			while ((next = reader.readLine()) != null)
			{
	            list.add(next);
	        }
		}
		catch (IOException e)
		{
			App.log("FileHelper::read exception for file " + filename);
		}
		finally
		{
			if (reader != null)
			{ 
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					App.log("FileHelper::read close exception");
				} 
			} 
		}
		
		return (String[]) list.toArray(new String[list.size()]);
	}
}
