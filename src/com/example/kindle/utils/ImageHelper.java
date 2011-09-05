package com.example.kindle.utils;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;

import com.example.kindle.winningfour.App;

/**
 * Helper class for kindle specific image manipulations.
 */
public class ImageHelper
{
	/**
	 * Returns only after the image has been completely loaded.
	 * Image loading on Kindle is asynchronous, and for not displaying
	 * half-rendered images MediaTracker service is used.
	 * 
	 * @param image Image waiting to be loaded.
	 * @param component Component that is used as a waiter.
	 */
	public static void waitForImage(final Image image, final Component component)
	{
		App.log("ImageLoader::waitforimage");
		
		MediaTracker tracker = new MediaTracker(component);
		image.getWidth(null); // this will trigger an image loader

		int id = 1;
		tracker.addImage(image, id);
		
		try
		{
		    tracker.waitForID(id);
		}
		catch(Exception e)
		{
			App.log("ImageLoader::waitforimage Image loading interrupted : " + e);
		}
		
		App.log("ImageLoader::waitforimage done");
	}
}
