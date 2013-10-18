package vwr.project.voidbubble.domain.world;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
/**
 * Entity contains all the values specific to a certain object.
 * These values are unique to the individual entity, even if
 * they are the same type of object.
 */
public class Entity
{
	/**If not active, entity is not draw nor updated,
	 * and is recycled as soon as necessary*/
	private boolean active;
	/**Need this because you can't modify the loop while
	 * in the middle of it. (see entlist.update) */
	private boolean deactivate;
	/**State controlling this entity*/
	private State state;
	/**Counts the number of frames that it has been in a certain substate*/
	private int counter;
	/**Which substate is the state currently performing*/
	private int substate;
	/**X position*/
	private float x;
	/**Y position*/
	private float y;
	/**Rotation*/
	private float r;
	/**X velocity*/
	private float xv;
	/**Y velocity*/
	private float yv;
	/**Angular velocity*/
	private float rv;
	/**Hit points*/
	private float hp;
	/**Hit box*/
	private ClipBox clip;
	
	
	public Entity(float xpos, float ypos, float rot, float xvel, float yvel,
			float rvel, State s)
	{
		clip = new ClipBox(0);
		fillAttributes(xpos, ypos, rot, xvel, yvel, rvel, s);
	}

	public Entity(Entity e)
	{
		clip = new ClipBox(0);
		fillAttributes(e);
	}

	public void fillAttributes(Entity e)
	{
		fillAttributes(e.x, e.y, e.r, e.xv, e.yv, e.rv, e.state);
	}

	/**This renews the entity's counter and activates it again. It recycles the entity.*/
	public void fillAttributes(float xpos, float ypos, float rot, float xvel, float yvel,
			float rvel, State s)
	{
		counter = 0;
		substate = 0;
		active = true;
		deactivate = false;
		
		x = xpos;
		y = ypos;
		r = rot;
		xv = xvel;
		yv = yvel;
		rv = rvel;
		state = s;
		state.Init(this);
	}

	/**
	 * @return is the entity currently active
	 */
	//this is needed for entityList.add
	//to check if this entity can be recycled
	public boolean isActive()
	{
		return active;
	}

	/**
	 * If the entity has deactivated in the last
	 * step, set it so. This exists because you
	 * cannot change a collection while iterating
	 * through it. (see entityList.update)
	 */
	public void updateActive()
	{
		if (deactivate) active = false;
	}
	
	//these needed for camera to follow
	//and for collision detection to work
	public float getX() { return x; }
	public float getY() { return y; }
	public float getR() { return r; }

	public void Update(long ticks)
	{
		if(active) state.Update(this, ticks);
	}

	public void Render(long ticks)
	{
		if(active) state.Render(this, ticks);
	}

	/**Interact with another entity**/
	public void Interact(Entity e2, long ticks)
	{
		state.Interact(this, e2, ticks);
	}
	
	/**Interact with level**/
	public void Interact(Level l, long ticks)
	{
		state.Interact(this, l, ticks);
	}
	
	public void NoticePoint(float xpt, float ypt, long ticks)
	{
		state.NoticePoint(this, xpt, ypt, ticks);
	}

	/**
	 * "States" are synonymous with "Types of Entity".
	 *  States are the core behavior manager of entities. They contain all the
	 * non changing values for each type of individual, whereas entities contain
	 * values that are different for everyone.
	 *  States have access to any entity that is passed into its methods, but
	 * normally they would only change the entity that they are embedded within.
	 *  Entities do not draw themselves, rather, they call upon their own state to
	 * draw them, according to the entity's own individual characteristics.
	 *  Entities also update themselves this way. They rely upon the state they
	 * link to in order to update themselves.
	 */
	/*
	 * 	I have changed the constructor method to receive all the subsystems it needs
	 * so that the states will keep track of them and use them whenever needed when 
	 * update is called, instead of having separate methods regarding each subsystem.
	 * PerformAction, Sound and Draw have been replaced with Update and Render, and
	 * the Sound method is called within State.Update, instead of being called by
	 * entitylist.update==>entity.sound
	 */
	public static class State
	{
		/**initialize. save any of the subsystems this type of entity will need.*/
		protected State(ResourceService res, SettingManager set, ScoreManager score, InputService input, DrawService draw, SoundService sound, Spawner spawn, StateList states) {}

		/**This is an important one. override this in your subclasses*/
		protected void Init(Entity e) {}
		
		/**This is the most important one. override this in your subclasses*/
		protected void Update(Entity e, long ticks) {}
		
		/**render function. override this too*/
		protected void Render(Entity e, long ticks) {}
		
		/**entity collision function. all entities will collide against each other
		 * @param s2 */
		protected void Interact(Entity e, Entity e2, long ticks) {}
		
		/**level collision function. all entities will collide against the level*/
		protected void Interact(Entity e, Level l, long ticks) {}
		
		/**Function to interact with a certain point**/
		protected void NoticePoint(Entity e, float x, float y, long ticks) {}
		
		//these grant all subclasses of State universal access to Entity
		//setters
		protected void Deactivate(Entity e) { e.deactivate = true; }
		protected void setX(Entity e, float setx) { e.x = setx; }
		protected void setY(Entity e, float sety) { e.y = sety; }
		protected void setR(Entity e, float setr) { e.r = setr; }
		protected void setXV(Entity e, float setxv) { e.xv = setxv; }
		protected void setYV(Entity e, float setyv) { e.yv = setyv; }
		protected void setRV(Entity e, float setrv) { e.rv = setrv; }
		protected void setHP(Entity e, float sethp) { e.hp = sethp; }
		protected void setSS(Entity e, int substate) { e.substate = substate; }
		//getters
		protected boolean getActive(Entity e) { return e.active; }
		protected float getX(Entity e) { return e.x; }
		protected float getY(Entity e) { return e.y; }
		protected float getR(Entity e) { return e.r; }
		protected float getXV(Entity e) { return e.xv; }
		protected float getYV(Entity e) { return e.yv; }
		protected float getRV(Entity e) { return e.rv; }
		protected float getHP(Entity e) { return e.hp; }
		protected int getSS(Entity e) { return e.substate; }
		protected State getState(Entity e) { return e.state; }
		protected ClipBox getClip(Entity e) { return e.clip; }
		
		//counter
		protected void incrementCounter(Entity e) { ++e.counter; }
		protected int getCounter(Entity e) { return e.counter; }
		protected void resetCounter(Entity e) { e.counter = 0; }
		
		//damage
		protected void recieveDamage(Entity e, float amount) { e.hp -= amount; }
		
		//physics functions
		/**An object in motion stays in motion.*/
		protected void simpleNewtonian(Entity e)
		{
			e.x += e.xv;
			e.y += e.yv;
			e.r += e.rv;
		}
		
		/**Slows the object down
		 * @param factor - amount to multiply the velocity by.
		 */
		protected void positionalFriction(Entity e, float factor)
		{
			e.xv *= factor;
			e.yv *= factor;
		}
		
		/**Increments the velocity
		 * @param xa - amount to add to the X velocity
		 * @param ya - amount to add to the Y velocity
		 */
		protected void positionalAccelerate(Entity e, float xa, float ya)
		{
			e.xv += xa;
			e.yv += ya;
		}
		
		/**Slows the object's rotation
		 * @param factor - amount to multiply the angular velocity by
		 */
		protected void rotationalFriction(Entity e, float factor)
		{
			e.rv *= factor;
		}
		
		/**Increments the angular velocity
		 * @param ra - amount to add to the angular velocity
		 */
		protected void rotationalAccelerate(Entity e, float ra)
		{
			e.rv += ra;
		}
	}
}
