package vwr.project.voidbubble.domain.world.entitystates;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.ClipBox;
import vwr.project.voidbubble.domain.world.Entity;
import vwr.project.voidbubble.domain.world.Level;
import vwr.project.voidbubble.domain.world.Spawner;
import vwr.project.voidbubble.domain.world.StateList;

/**
 * Test state
 */
public class ClippingState extends BaseProjectile
{
	protected float clipBoxSize;

	public ClippingState(ResourceService res, SettingManager setM, ScoreManager scoreM, InputService inputS, DrawService drawS, SoundService soundS, Spawner spawn, StateList states)
	{
		super(res, setM, scoreM, inputS, drawS, soundS, spawn, states);
		size = 30;
		clipBoxSize = 30;
	}
	
	protected void Init(Entity e)
	{
		getClip(e).Resize(clipBoxSize, clipBoxSize);
		getClip(e).AtPosition(getX(e), getY(e));
	}
	
	protected void Update(Entity e, long ticks)
	{
		super.Update(e, ticks);
		getClip(e).AtPosition(getX(e), getY(e));
	}
	
	protected void Render(Entity e, long ticks)
	{
		super.Render(e, ticks);
		//getClip(e).DrawOutline(draw_s);
	}
	
	protected void Interact(Entity e, Entity e2, long ticks)
	{
		ClipBox c2 = getClip(e2);
		if (getClip(e).CollisionTest(c2))
		{
			Collision(e, e2, ticks);
		}
	}

	protected void Collision(Entity e, Entity e2, long ticks)
	{
	}
}
