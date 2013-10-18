package vwr.project.services.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;


/**
 * Controls the various options.
 * Uses a single hash map, String, Integer.
 * Where String is the name of the option,
 * and Integer is the value.
 * It can also load options from a file, or save to a file.
 */

public class SettingManager
{
	private boolean debugging = true;
	
	private Map<String, String> settingsMap;
	private String settingsFile;
	private String settingsPath;
	public SettingManager(String path)
	{
		settingsPath = path;
		settingsFile = path + File.separator + "settings.cfg";
		settingsMap = new HashMap<String, String>();

		//open the file. if it's not there, then print an error message
		FileInputStream fin;
		try
		{
		    fin = new FileInputStream (settingsFile);
		    BufferedReader bufread = new BufferedReader(new InputStreamReader(fin));

		    String nextLine = bufread.readLine();
		    while(nextLine != null)
		    {
		    	parseSetting(nextLine);
		    	nextLine = bufread.readLine();
		    }
		    
		    fin.close();		
		}
		catch (IOException e)
		{
			System.out.println("Could not open settings file!");
		}
		fixSettings();
	}
	
	private void parseSetting(String nextLine)
	{
		System.out.println("parseSetting: ");
		if(nextLine == null) return;
		if(!nextLine.contains("=")) return;
		String[] twos = nextLine.split("=");
		if(debugging) System.out.println("Key=|" + twos[0] + "| Value=|" + twos[1] +"|");
		settingsMap.put(twos[0], twos[1]);
	}
	
	/**
	 * if the loaded settings file is missing
	 * highly important settings, or the settings
	 * are out of range, it is corrected here
	 * "Eager Materialization"
	 */
	private void fixSettings()
	{
		//game
		if(!settingsMap.containsKey("framerate"))
			settingsMap.put("framerate", "60");
		if(!settingsMap.containsKey("width"))
			settingsMap.put("width", "640");
		if(!settingsMap.containsKey("height"))
			settingsMap.put("height", "400");
		
		//input
		if(!settingsMap.containsKey("key left"))
			settingsMap.put("key left", "30"); //a
		if(!settingsMap.containsKey("key right"))
			settingsMap.put("key right", "32"); //d
		if(!settingsMap.containsKey("key forward"))
			settingsMap.put("key forward", "17"); //w
		if(!settingsMap.containsKey("key back"))
			settingsMap.put("key back", "31"); //s
	}
	
	public void Set(String name, String value)
	{
		settingsMap.put(name, value);
	}
	
	public String Get(String name)
	{
		String result = settingsMap.get(name);
		if(result == null) return "";
		else return result;
	}
	
	/**
	 * Save all settings to the settings file
	 */
	public void Save()
	{
		try
		{
			new File(settingsPath).mkdir();
			FileOutputStream fout = new FileOutputStream(settingsFile);
			PrintStream writer = new PrintStream(fout);
			Set<Entry<String,String>> allSettings = settingsMap.entrySet();
			for(Entry<String,String> entry : allSettings)
			{
				writer.println(entry.getKey() + "=" + entry.getValue());
			}
			
		} catch (FileNotFoundException e)
		{
			System.out.println("Could not save settings! Tried to save to " + settingsFile);
			e.printStackTrace();
		}
		
	}

	/**If settings have changed*/
	public boolean Changed()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
