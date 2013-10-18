package vwr.project.voidbubble.domain.world;

import vwr.project.voidbubble.domain.world.Entity.State;

/**
 * Allows entities to spawn other entities.
 */
public class Spawner
{
	/**reference to the entity list*/
	private EntityList elist;
	
	/**
	 * Point local reference elist to world manager's entities
	 */
	public Spawner(EntityList el)
	{
		elist = el;
	}
	
	/**
	 * Spawns an entity
	 * @param x - X position
	 * @param y - Y position
	 * @param r - Rotation
	 * @param xv - X velocity
	 * @param yv - Y velocity
	 * @param rv - Angular velocity
	 * @param s - State
	 */
	public void spawn(float x, float y, float r, float xv, float yv, float rv, State s)
	{
		elist.Spawn(x, y, r, xv, yv, rv, s);
	}
}
