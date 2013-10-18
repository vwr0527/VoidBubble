package vwr.project.voidbubble.domain.world;

import java.util.ArrayList;

/**
 * Manages entities. Updates them if not paused, renders them,
 * and is responsible for adding new entities.
 */

public class EntityList
{
	/**List of currently active entities**/
	private ArrayList<Entity> entList;
	/**List of entities to add after update**/
	private ArrayList<Entity> spawnList;
	
	private int numEnts;
	private int entCursor;
	private Camera cam;
	private Reticle crosshair;

	private Level level_r;

	private int numNewEnts;
	
	public EntityList()
	{
		entList = new ArrayList<Entity>();
		spawnList = new ArrayList<Entity>();
	}
	
	public void ReadStates(StateList states)
	{
		Spawn(0,-30,0,0,0,0, states.Get("playerstate"));
	}

	public void SetLevel(Level lvl)
	{
		level_r = lvl;
	}

	/**This is where the player entity will be aiming at**/
	public void SetReticle(Reticle ret)
	{
		crosshair = ret;
	}
	
	/**
	 * Adds an entity.
	 * @param x X position
	 * @param y Y position
	 * @param r Rotation
	 * @param xv X velocity
	 * @param yv Y velocity
	 * @param rv Angular velocity
	 * @param s State
	 */
	//Works almost the same way as the function below, except it replaces
	//all entities in the spawn list every time.
	public void Spawn(float x, float y, float r, float xv, float yv, float rv, Entity.State s)
	{
		if (numNewEnts >= spawnList.size())
		{
			spawnList.add(new Entity(x, y, r, xv, yv, rv, s));
		}
		else
		{
			Entity e = spawnList.get(numNewEnts);
			e.fillAttributes(x, y, r, xv, yv, rv, s);
		}
		++numNewEnts;
	}
	//if the number of ents exceeded the capacity of entList, then expand entList, otherwise,
	//looks through the entity list, when it finds one that is "dead" it reclaims that spot
	//when another spot is needed, it performs a linear search from the last location it was
	//wrapping back to the beginning if needed.
	private void Add(Entity e)
	{
		//numEnts counts how many active ents are in the entList
		if (numEnts >= entList.size())
		{
			entList.add(new Entity(e));
		}
		else
		{
			//find the ent that is not active, replace it with the new one.
			
			//note: numEnts must be less than entList.size at this point
			
			//don't loop forever. at least one of these ents must be
			//non active. If all entities are active, it shouldn't
			//have gotten here. But if it did anyway, it would choose
			//an entity that is still active (entCursor - 1) and
			//replace it with the new one anyway.
			for(int i = 0; i < entList.size(); ++i)
			{
				//if the entity at the position of entCursor is not active,
				//break, because entCursor is on the correct index
				if (!entList.get(entCursor).isActive()) break;
				//otherwise, increment the cursor, looping back if needed
				entCursor = (entCursor + 1) % entList.size();
			}
			Entity ent = entList.get(entCursor);
			ent.fillAttributes(e);
		}
		++numEnts;
	}
	
	/**
	 * Updates and renders the active entities.
	 * @param paused - Is it paused?
	 * @param ticks - number of ticks passed when not paused.
	 */
	public void Update(boolean paused, long ticks)
	{
		//Make the player face the reticle
		if (entList.size() > 0) getPlayer().NoticePoint(crosshair.getX(), crosshair.getY(), ticks);
		//update the main entity list
		for(int i = 0; i < entList.size(); ++i)
		{
			Entity e = entList.get(i);
			if(e.isActive())
			{
				if (!paused)
				{
					//cannot allow e to change the list while
					//iterating through the list
					e.Update(ticks);
					for(int j = i+1; j < entList.size(); ++j)
					{
						Entity f = entList.get(j);
						if (f.isActive()) e.Interact(f, ticks);
					}
					e.Interact(level_r, ticks);
					e.Render(ticks);
				}
				else
				{
					e.Render(ticks);
				}
			}
		}
		numEnts = 0;
		//Update active state
		for(Entity e : entList)
		{
			if(e.isActive())
			{
				e.updateActive();
				if(e.isActive()) ++numEnts;
			}
		}
		//Add unspawned entities to the entity list
		for(int i = 0; i < numNewEnts; ++i)
		{
			Add(spawnList.get(i));
		}
		numNewEnts = 0;
	}
	
	/**
	 * @return number of entities
	 */
	public int Count()
	{
		return numEnts;
	}

	public Entity getPlayer()
	{
		return entList.get(0);
	}
}
