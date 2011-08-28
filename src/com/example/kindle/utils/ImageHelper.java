package com.example.kindle.utils;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;

import com.example.kindle.winningfour.App;

public class ImageHelper
{
	public static void waitForImage(final Image image, final Component component)
	{
		App.log("ImageLoader::waitforimage");
		
		MediaTracker media_tracker = new MediaTracker(component);
		image.getWidth(null); // this will trigger an image loader

		int id = 1;
		media_tracker.addImage(image, id);
		
		try
		{
		    media_tracker.waitForID(id);
		}
		catch(Exception e)
		{
			App.log("ImageLoader::waitforimage Image loading interrupted : " + e);
		}
		
		App.log("ImageLoader::waitforimage done");
	}
}
