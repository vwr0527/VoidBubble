package vwr.project.voidbubble.domain.world;

import java.util.ArrayList;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.SettingManager;

public class LevelList
{
	public ArrayList<Level> lvls;
	private int currentLevelIndex;
	public LevelList(DrawService drawS, SoundService soundS, ResourceService resourceS, SettingManager setting, Spawner spawner)
	{
		lvls = new ArrayList<Level>();
		loadAll(drawS, soundS, resourceS, setting, spawner);
	}
	
	private void loadAll(DrawService drawS, SoundService soundS, ResourceService resourceS, SettingManager setting, Spawner spawner)
	{
		Level firstLevel = new Level(1280, 800,
				drawS, soundS, resourceS, setting, spawner);
		lvls.add(firstLevel);
	}
	
	/**Updates and draws the level*/
	public void Update(boolean paused, long ticks)
	{
		Level l = GetCurrentLevel();
		l.Update(paused, ticks);
		l.Render(ticks);
	}

	public Level GetCurrentLevel()
	{
		return lvls.get(currentLevelIndex);
	}
	
	public void NextLevel()
	{
		++currentLevelIndex;
	}
}
