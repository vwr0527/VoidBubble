package vwr.project.voidbubble.domain.world.entitystates;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.Entity;
import vwr.project.voidbubble.domain.world.Spawner;
import vwr.project.voidbubble.domain.world.StateList;

public class Laser extends ClippingState
{
	private Texture blast1;
	private Texture blast2;
	private Texture blast3;
	private int lifespan;
	private ScoreManager score_m;

	public Laser(ResourceService res, SettingManager setM, ScoreManager scoreM,
			InputService inputS, DrawService drawS, SoundService soundS,
			Spawner spawn, StateList states)
	{
		super(res, setM, scoreM, inputS, drawS, soundS, spawn, states);
		blast1 = res.GetTexture("blast1.png");
		blast2 = res.GetTexture("blast2.png");
		blast3 = res.GetTexture("blast3.png");
		size = 15;
		lifespan = 100;
		clipBoxSize = 10;
		score_m = scoreM;
	}

	protected void Update(Entity e, long ticks) 
	{
		super.Update(e, ticks);
		if (getCounter(e) > lifespan) Deactivate(e);
		int cycle = (int)(ticks % 100);
		if (cycle < 33)
		{
			texture = blast1;
		}
		else if (cycle < 66)
		{
			texture = blast2;
		}
		else
		{
			texture = blast3;
		}
	}
	
	protected void Collision(Entity e, Entity e2, long ticks)
	{
		if(getState(e2) instanceof EnemyState)
		{
			Deactivate(e2);
			Deactivate(e);
			score_m.AddCurrentScore(15);
		}
	}
}
