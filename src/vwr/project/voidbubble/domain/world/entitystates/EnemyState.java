package vwr.project.voidbubble.domain.world.entitystates;

import org.newdawn.slick.Color;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.Entity;
import vwr.project.voidbubble.domain.world.Spawner;
import vwr.project.voidbubble.domain.world.StateList;
import vwr.project.voidbubble.domain.world.Entity.State;

public class EnemyState extends Solid
{

	private float initialOffset;
	private ScoreManager score_m;

	public EnemyState(ResourceService res, SettingManager setM,
			ScoreManager scoreM, InputService inputS, DrawService drawS,
			SoundService soundS, Spawner spawn, StateList states)
	{
		super(res, setM, scoreM, inputS, drawS, soundS, spawn, states);
		col = new Color(1f, 0f, 0f);
		texture = res.GetTexture("enemy1.png");
		size = 15;
		clipBoxSize = 20;
		score_m = scoreM;
	}
	
	protected void Init(Entity e)
	{
		initialOffset = rand.nextFloat() * (float)Math.PI;
		setR(e, initialOffset * 1000);
		super.Init(e);
	}
	
	protected void Update(Entity e, long ticks)
	{
		positionalAccelerate(e, rand.nextFloat() - 0.5f, rand.nextFloat() - 0.5f);
		rotationalAccelerate(e, (rand.nextFloat() - 0.5f) * 10f);
		col.a = 0.9f + ((float)Math.sin((ticks / 40.0) + initialOffset) * 0.1f);
		//if (getCounter(e) == 1) Sound(e, ticks);
		super.Update(e, ticks);
	}
	
	protected void Collision(Entity e, Entity e2, long ticks)
	{
		State e2state = getState(e2);
		if (e2state instanceof Laser)
		{
			Deactivate(e);
			Deactivate(e2);
			score_m.AddCurrentScore(15);
		}
		else if (e2state instanceof PlayerState)
		{
			Deactivate(e);
			Deactivate(e2);
			//WorldManager.gameOver
		}
	}
}
