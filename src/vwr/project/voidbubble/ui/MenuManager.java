package vwr.project.voidbubble.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.WorldService;

public class MenuManager
{
	private TrueTypeFont consoleFont;
	private DrawService draw_s;
	private WorldService world_s;
	private SettingManager setting_m;
	private ScoreManager score_m;
	private ResourceService resource_s;
	private SoundService sound_s;
	private InputService input_s;
	private MenuChanger menu_c;
	private ArrayList<MenuItemList> menus;
	private MenuItemList currentMenu;
	private Texture cursorTex;
	private boolean active;
	private int width;
	private int height;

	public MenuManager(DrawService drawS, SoundService soundS,
			InputService inputS, ResourceService resourceS,
			SettingManager settings, ScoreManager scores, WorldService worldS)
	{
		draw_s = drawS;
		sound_s = soundS;
		setting_m = settings;
		score_m = scores;
		resource_s = resourceS;
		input_s = inputS;
		world_s = worldS;
		menu_c = new MenuChanger();
		menus =	new ArrayList<MenuItemList>();
		
		LoadSettings();
		LoadResources();
		LoadMenus();
		
		active = false;
	}

	private void LoadSettings()
	{
		width = Integer.parseInt(setting_m.Get("width"));
		height = Integer.parseInt(setting_m.Get("height"));
	}

	private void LoadResources()
	{
		consoleFont = resource_s.GetFont("Aethv2.ttf");
		cursorTex = resource_s.GetTexture("nonexistant.png");
	}

	private void LoadMenus()
	{
		MenuItemList mainMenu = new MenuItemList();
		MenuItemDecoration title = new MenuItemDecoration(
		resource_s, setting_m, score_m, draw_s,	"Menu", "Aethv2.ttf", null);
		title._x = (int)(width * (0.38));
		title._y = (int)(height * (0.75));
		title._fontCol = Color.green;
		mainMenu.itemList.add(title);
		menus.add(mainMenu);
	}

	/**Only update the menu page currently being displayed**/
	public void update(long ticks)
	{
		if (Keyboard.next()
			&& Keyboard.getEventKeyState()
			&& (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE))
		{
			active = !active;
		}
		
		if (active)
		{
			menus.get(menu_c.Page).Update(ticks);
			
			draw_s.DrawCursor(
			input_s.getMouseX(), input_s.getMouseY(),
			0, 10, cursorTex, Color.white);
			
			world_s.Pause();
		}
		else
		{
			world_s.Unpause();
		}
	}
}
