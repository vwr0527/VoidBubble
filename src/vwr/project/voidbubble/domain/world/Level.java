package vwr.project.voidbubble.domain.world;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.SettingManager;

public class Level
{
	private DrawService draw;
	private ClipBox boundry;

	public Level(float width, float height, DrawService drawS, SoundService soundS,
			ResourceService resourceS, SettingManager setting, Spawner spawner)
	{
		boundry = new ClipBox(width,height);
		draw = drawS;
	}

	public ClipBox GetBoundry()
	{
		return boundry;
	}

	public void Update(boolean paused, long ticks)
	{
	}

	public void Render(long ticks)
	{
		boundry.DrawOutline(draw);
	}
}
