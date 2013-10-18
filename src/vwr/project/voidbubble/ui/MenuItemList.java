package vwr.project.voidbubble.ui;

import java.util.ArrayList;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.WorldService;

/**A page full of menu items**/
public class MenuItemList
{
	/**List of menu items in this menu page**/
	public ArrayList<MenuItem> itemList;
	
	public MenuItemList()
	{
		itemList = new ArrayList<MenuItem>();
	}
	
	public void Update(long ticks)
	{
		for(MenuItem m : itemList)
		{
			m.Update(ticks);
		}
	}
}
