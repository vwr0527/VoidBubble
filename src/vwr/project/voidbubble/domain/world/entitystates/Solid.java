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
import vwr.project.voidbubble.domain.world.Entity.State;

/**
 * Now extends from clippingState instead of just baseProjectile.
 * this allows other things to inherit from clippingstate and implement their own
 * clipping entities, while keeping the solid state a separate thing.
**/
public class Solid extends ClippingState
{
	protected float mutualRepellantForce;

	public Solid(ResourceService res, SettingManager setM, ScoreManager scoreM, InputService inputS, DrawService drawS, SoundService soundS, Spawner spawn, StateList states)
	{
		super(res, setM, scoreM, inputS, drawS, soundS, spawn, states);
		mutualRepellantForce = 0.5f;
	}
	
	protected void Update(Entity e, long ticks)
	{
		positionalFriction(e, 0.95f);
		rotationalFriction(e, 0.9f);
		super.Update(e, ticks);
	}
	
	protected void Interact(Entity e, Level l, long ticks)
	{
		if (!l.GetBoundry().ContainmentTest(getClip(e)))
		{
			//if (l.GetBoundry().ClippedTopAmount(getClip(e)) > 0) something like this
			setXV(e, -getXV(e));
			setYV(e, -getYV(e));
		}
	}
	
	protected void Collision(Entity e, Entity e2, long ticks)
	{
		float ex = getX(e);
		float ey = getY(e);
		float e2x = getX(e2);
		float e2y = getY(e2);
		
		float vec_x = ex - e2x;
		float vec_y = ey - e2y;
		float mag = (float)Math.sqrt(vec_x * vec_x + vec_y * vec_y) / mutualRepellantForce;

		float normvec_x = vec_x / mag;
		float normvec_y = vec_y / mag;
		
		positionalAccelerate(e, normvec_x, normvec_y);
		positionalAccelerate(e2, -normvec_x, -normvec_y);
	}
}
