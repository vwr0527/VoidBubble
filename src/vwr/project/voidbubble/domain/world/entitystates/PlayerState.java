package vwr.project.voidbubble.domain.world.entitystates;

import org.newdawn.slick.Color;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.Entity;
import vwr.project.voidbubble.domain.world.Reticle;
import vwr.project.voidbubble.domain.world.Spawner;
import vwr.project.voidbubble.domain.world.StateList;
import vwr.project.voidbubble.domain.world.Entity.State;

/**
 * State that controls the player entity in WorldManager.
 * Receives input. Is the focus of the camera.
 */
public class PlayerState extends Solid
{
	InputService input_r;
	Spawner spawn_r;
	StateList state_l;
	State laser;
	private float muzzleVel;
	private float speed;
	private boolean directionalRelative;
	public PlayerState(ResourceService res, SettingManager set, ScoreManager score, 
			InputService input,	DrawService draw, SoundService soundS, Spawner spawn,
			StateList states)
	{
		super(res, set, score, input, draw, soundS, spawn, states);
		sound = res.GetSound("laser.wav");
		input_r = input;
		spawn_r = spawn;
		state_l = states;
		muzzleVel = 12;
		speed = 0.5f;
		size = 15;
		clipBoxSize = 20;
		directionalRelative = false;
		col = new Color(1f,1f,1f,0.9f);
		texture = res.GetTexture("ship.png");
	}
	
	protected void NoticePoint(Entity e, float xpt, float ypt, long ticks)
	{
		float xdiff = getX(e) - xpt;
		float ydiff = getY(e) - ypt;
		float getDir = (float)Math.atan2(ydiff, xdiff);
		getDir *= (180.0f/(float)Math.PI);
		getDir += 90;
		setR(e, getDir);
	}
	
	protected void Update(Entity e, long ticks)
	{
		super.Update(e, ticks);
		col.r = 0.5f + ((float)Math.sin(ticks / 100.0) / 2);
		float rot = -getR(e) * ((float)Math.PI/180.0f);
		if (input_r.getMouseButton() == 2)
		{
			super.Sound(e, ticks);
			float laserXV = getXV(e) + (float)Math.sin(rot) * muzzleVel;
			float laserYV = getYV(e) + (float)Math.cos(rot) * muzzleVel;
			if (laser == null) laser = state_l.Get("laser");
			spawn_r.spawn(getX(e) + laserXV * 2, getY(e) + laserYV * 2, getR(e), laserXV, laserYV, 0, laser);
		}
		if (directionalRelative)
		{
			if (input_r.getForward() == 1) positionalAccelerate(e, (float)Math.sin(rot) * speed, (float)Math.cos(rot) * speed);
			rot += Math.PI;
			if (input_r.getBackward() == 1) positionalAccelerate(e, (float)Math.sin(rot) * speed, (float)Math.cos(rot) * speed);
			rot += Math.PI/2;
			if (input_r.getLeft() == 1) positionalAccelerate(e, (float)Math.sin(rot) * speed, (float)Math.cos(rot) * speed);
			rot += Math.PI;
			if (input_r.getRight() == 1) positionalAccelerate(e, (float)Math.sin(rot) * speed, (float)Math.cos(rot) * speed);
		}
		else
		{
			if (input_r.getForward() == 1) positionalAccelerate(e, 0, speed);
			if (input_r.getBackward() == 1) positionalAccelerate(e, 0, -speed);
			if (input_r.getLeft() == 1) positionalAccelerate(e, -speed, 0);
			if (input_r.getRight() == 1) positionalAccelerate(e, speed, 0);
		}
	}

	protected void Collision(Entity e, Entity e2, long ticks)
	{
		if (getState(e2) instanceof EnemyState)
		{
			Deactivate(e);
			Deactivate(e2);
			//WorldManager.gameOver
		}
	}
}