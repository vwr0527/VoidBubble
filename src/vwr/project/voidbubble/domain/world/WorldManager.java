package vwr.project.voidbubble.domain.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.Entity.State;

public class WorldManager
{
	// most values are public because they will be
	// effectively hidden behind WorldService
	/**entity*/
	public Entity player;
	/**controls the camera*/
	public Camera camera;
	/**what the player aims at**/
	public Reticle crosshair;
	/**entity list*/
	public EntityList entities;
	/**allows entities to spawn other entities*/
	public Spawner spawner;
	/**reference to input service*/
	public DrawService draw_s;
	/**reference to input service*/
	public SoundService sound_s;
	/**reference to input service*/
	public InputService input_s;
	/**reference to score service*/
	public ScoreManager score_m;
	/**state list*/
	public StateList states;
	/**level list*/
	public LevelList levels;
	/**screen width*/
	public int width;
	/**screen height*/
	public int height;
	
	public WorldManager(DrawService drawS, SoundService soundS,
			ResourceService resourceS, InputService inputS, SettingManager setting, ScoreManager scoreM)
	{
		width = Integer.parseInt(setting.Get("width"));
		height = Integer.parseInt(setting.Get("height"));
		score_m = scoreM;
		draw_s = drawS;
		sound_s = soundS;
		input_s = inputS;
		entities = new EntityList();
		spawner = new Spawner(entities);
		states = new StateList(drawS, soundS, resourceS, inputS, setting, scoreM, spawner);
		levels = new LevelList(drawS, soundS, resourceS, setting, spawner);
		entities.ReadStates(states);
		entities.SetLevel(levels.GetCurrentLevel());
		camera = new Camera();
		camera.SetFollowSpeed(0.1f);
		crosshair = new Reticle(resourceS, width, height);
		
		 //testing purposes
		tempstate = states.Get("enemystate");
		
		 //eventually replace this ugly font
		consoleFont = resourceS.GetFont("Aethv2.ttf");
		consoleMsg = new StringBuffer();
		
		 //colors for drawing a background grid
		tw = new Color(1, 1, 1, 0.1f);
		tw.a = 0.1f;
		tg = new Color(0,1,0, 0.5f);
		tg.a = 0.5f;
	}

	public boolean paused = false;
	public boolean justStartedPause = false;
	public long startPauseTicks;
	public long endPauseTicks;
	public long ticksBetweenPauses;
	
	/**
	 * Updates and draws the world. Behavior depends on whether or
	 * not it is paused.
	 * @param ticks - number of ticks since the start of the game
	 */
	public void update(long ticks)
	{
		states.Update(); //if difficulty changes, states should be updated
		if (paused)
		{
			if (justStartedPause)
			{
				startPauseTicks = ticks;
				justStartedPause = false;
			}
			DrawStuff();
			entities.Update(paused, startPauseTicks - ticksBetweenPauses);
			levels.Update(paused, startPauseTicks - ticksBetweenPauses);
		}
		else
		{
			if (!justStartedPause)
			{
				endPauseTicks = ticks;
				ticksBetweenPauses += endPauseTicks - startPauseTicks;
				justStartedPause = true;
			}
			DoStuff();
			DrawStuff();
			crosshair.Update(input_s.getMouseX(), input_s.getMouseY(), camera);
			entities.SetReticle(crosshair);
			entities.Update(paused, ticks - ticksBetweenPauses);
			camera.Follow(entities.getPlayer());
			camera.Update(draw_s, ticks - ticksBetweenPauses);
			levels.Update(paused, ticks - ticksBetweenPauses);
			crosshair.Render(draw_s, ticks - ticksBetweenPauses);
		}
		Console();
	}

	public TrueTypeFont consoleFont;
	public StringBuffer consoleMsg;
	
	/**renders system messages regarding the world.*/
	private void Console()
	{
		consoleMsg.delete(0, consoleMsg.length());
		consoleMsg.append("Score: ");
		consoleMsg.append(score_m.currentScore);
		draw_s.DrawString(consoleFont, 0, 0, 0.35f, Color.lightGray, consoleMsg);
	}


	State tempstate;
	int simpleCount;
	
	/**does stuff that only need to be done when not paused*/
	private void DoStuff()
	{
		++simpleCount;
		if(simpleCount > 25)
		{
			simpleCount = 0;
			entities.Spawn(0, 0, 0, 0, 0, 0, tempstate);
		}
	}

	private Color tw; //transparent white
	private Color tg; //transparent blue
	
	/**draws stuff. regardless of paused state*/
	private void DrawStuff()
	{
		//draws a grid
		for (int i = -width; i <= width; i += 20)
		{
			draw_s.DrawLine(i, -height, tw, i, height, tg);
		}
		for (int i = -height; i <= height; i += 20)
		{
			draw_s.DrawLine(-width, i, tw, width, i, tg);
		}
	}
}
