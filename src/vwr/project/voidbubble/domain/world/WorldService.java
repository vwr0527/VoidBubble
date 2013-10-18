package vwr.project.voidbubble.domain.world;

/**
 * allows limited access to various functions in WorldManager
 */

public class WorldService
{
	/**reference to the world manager being controlled*/
	private WorldManager world_m;
	
	public WorldService(WorldManager worldM)
	{
		world_m = worldM;
	}
	
	/**pauses the world*/
	public void Pause()
	{
		world_m.paused = true;
	}

	/**unpauses the world*/
	public void Unpause()
	{
		world_m.paused = false;
	}
}
