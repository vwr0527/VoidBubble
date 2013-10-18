package vwr.project.voidbubble.domain.world.entitystates;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
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

/**
 * An abstract class that governs the basic behavior of a
 * simple projectile. IE: It moves, it spins, it renders
 * a sprite, and it plays sounds.
 */
public abstract class BaseProjectile extends Entity.State
{
	protected ResourceService resource_s;
	protected DrawService draw_s;
	protected Texture texture;
	protected Random rand;
	protected Audio sound;
	protected Color col;
	
	protected float size;
	
	public BaseProjectile(ResourceService res, SettingManager setM, ScoreManager scoreM, InputService inputS, DrawService drawS, SoundService soundS, Spawner spawn, StateList states)
	{
		super(res, setM, scoreM, inputS, drawS, soundS, spawn, states);
		resource_s = res;
		draw_s = drawS;
		rand = new Random();
		col = Color.white;
		texture = res.GetTexture("nonexistant.png");
		size = 50;
	}
	
	protected void Update(Entity e, long ticks)
	{
		incrementCounter(e);
		simpleNewtonian(e);
	}
	
	protected void Sound(Entity e, long ticks)
	{
		if(sound != null) sound.playAsSoundEffect(1f, 1f, false);
	}
	
	protected void Render(Entity e, long ticks)
	{
		draw_s.DrawSprite(getX(e), getY(e), getR(e), size, texture, col);
	}
}
