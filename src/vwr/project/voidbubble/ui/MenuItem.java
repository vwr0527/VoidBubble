package vwr.project.voidbubble.ui;

import org.newdawn.slick.Color;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.WorldService;

public abstract class MenuItem
{
	protected ResourceService resource_s;
	protected SettingManager setting_m;
	protected ScoreManager score_m;
	protected DrawService draw_s;
	protected SoundService sound_s;
	protected InputService input_s;
	protected WorldService world_s;
	public int _x;
	public int _y;
	public float _size;
	public Color _col;
	
	public MenuItem(ResourceService resourceS, SettingManager settingM,
			DrawService drawS)
	{
		resource_s = resourceS;
		setting_m = settingM;
		draw_s = drawS;
		_size = 1;
		_col = new Color(1f,1f,1f);
	}
	
	public void Update(long ticks)
	{
		
	}
	
	public void ChangeValue(int valueBy)
	{
		
	}
	
	public int GetValue()
	{
		return 0;
	}
	
	public void Select()
	{
		
	}
	
	public boolean Selected()
	{
		return false;
	}
}
