package vwr.project.voidbubble.domain;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import vwr.project.services.audio.*;
import vwr.project.services.graphics.*;
import vwr.project.services.input.*;
import vwr.project.services.storage.*;
import vwr.project.voidbubble.domain.world.*;
import vwr.project.voidbubble.ui.*;

/**
 * Game is responsible for starting the display,
 * initializing all the subsystems,
 * running the main update and render loop,
 * and shutting down the game upon exiting.
 */

 /*
  * model view controller pattern
  * 
 * Domain = programming logic = everything??? world, entity, level??
 * View = what user sees = display, entity.view, level.view
 * Model = data structures = world, entity, level
 * Controller = how user interacts = input manager, entity.controller, ui.controller
 */

public class Game
{
	private ScoreManager scores;
	private SettingManager settings;
	private ResourceManager resource_m;
	private ResourceService resource_s;
	private DrawManager draw_m;
	private DrawService draw_s;
	private SoundManager sound_m;
	private SoundService sound_s;
	private InputManager input_m;
	private InputService input_s;
	private WorldManager world_m;
	private WorldService world_s;
	private MenuManager menu_m;
	
	/**frames per second*/
	private int FPS;
	/**number of ticks since game started*/
	private long ticks;
	/**number of system ticks at the time the game started*/
	private long starting_ticks;
	/**where the persistent storage directory is*/
	private String storedDirectory;

	public Game()
	{
		InitSettings();
		InitDisplay();
		LoadResources();
		
		// res created in LoadResources
		// set and scores created in initSettings
		resource_s = new ResourceService(resource_m);
		draw_m = new DrawManager(settings);
		draw_s = new DrawService(draw_m);
		sound_m = new SoundManager(settings);
		sound_s = new SoundService(sound_m);
		input_m = new InputManager(settings);
		input_s = new InputService(input_m);
		world_m = new WorldManager(draw_s, sound_s, resource_s, input_s, settings, scores);
		world_s = new WorldService(world_m);
		menu_m = new MenuManager(draw_s, sound_s, input_s, resource_s, settings, scores, world_s);
		
		starting_ticks = Sys.getTime();
	}

	/**sets the stored directory, constructs settings manager, sets the FPS,
	 * and initializes the score manager*/
	private void InitSettings()
	{
		storedDirectory = System.getProperty("user.home") + File.separator + "voidbubble";
		settings = new SettingManager(storedDirectory);
		FPS = Integer.parseInt(settings.Get("framerate"));
		
		scores = new ScoreManager(storedDirectory);
	}

	/**constructs the resource manager*/
	private void LoadResources()
	{
		try
		{
			resource_m = new ResourceManager(
					"res" + File.separator + "texture",
					"res" + File.separator + "sound",
					"res" + File.separator + "font");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			AbnormalExit();
		}
		catch(FontFormatException e)
		{
			e.printStackTrace();
			AbnormalExit();
		}
	}
	/**Tries to create Display. Exits with code -1 if fails*/
	private void InitDisplay()
	{
		try
		{
			Display.setDisplayMode(
					new DisplayMode(
							Integer.parseInt(settings.Get("width")),
							Integer.parseInt(settings.Get("height"))));
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**loop continuously until X is pressed on the window.
	 * Synced to the frame rate, so it's not a busy loop*/
	public void Run()
	{
		while(!Display.isCloseRequested())
		{
			ticks = Sys.getTime() - starting_ticks;
			Display.sync(FPS);
			input_m.read();
			world_m.update(ticks);
			menu_m.update(ticks);
			sound_m.play();
			draw_m.render();
			Display.update();
			Debug();
		}
		//Exiting
		NormalExit();
	}

	/**only use if display has been initialized but a problem occurred*/
	private void AbnormalExit()
	{
		AL.destroy();
		Display.destroy();
		System.exit(-1);
	}
	
	/**Called on exit. Saves settings and scores*/
	private void NormalExit()
	{
		scores.Save();
		settings.Save();
		AL.destroy();
		Display.destroy();
	}

	/**Called in the main loop, once per frame*/
	private void Debug()
	{
	}
}
