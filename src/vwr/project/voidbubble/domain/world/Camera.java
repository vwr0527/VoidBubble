package vwr.project.voidbubble.domain.world;

import vwr.project.services.graphics.DrawService;

/**
 * All the information that represents a camera.
 */
public class Camera
{
	/**x position*/
	private float x;
	/**y position*/
	private float y;
	/**scale, or zoom level*/
	private float s;
	/**rotation*/
	private float r;
	/**follows at a rate of (distance to entity)*followSpeed*/
	private float followSpeed;
	
	public Camera()
	{
		s = 1f;
		x = 0;
		y = 0;
	}
	
	/**
	 * Set camera to follow an entity, dictated by how fast
	 * the camera is set to follow the entity
	 * @param e - entity to follow
	 */
	public void Follow(Entity e)
	{
		x -= (x - e.getX()) * followSpeed;
		y -= (y - e.getY()) * followSpeed;
	}
	
	/**
	 * Sets the rotation of the camera equal to parameter
	 * @param e - entity the camera rotates with
	 */
	public void RotateWith(Entity e)
	{
		r = -e.getR();
	}
	
	/**
	 * Updates the camera
	 * @param drawS - draw service to modify the view with
	 * @param ticks - time elapsed during unpaused states
	 */
	public void Update(DrawService drawS, long ticks)
	{
		//r = (float)Math.sin(ticks / 2000.0f) * 10;
		//s = ((float)Math.cos(ticks / 500.0f) + 50) / 50.0f;
		drawS.SetCamera(x, y, r, s);
	}
	
	/**
	 * Set camera's following speed. speed = 0.5f means it travels half the distance
	 * to the entity in a frame. speed = 0.1f means it travels one tenth the distance
	 * in a frame.
	 * @param speed - speed at which the camera follows the entity it's following.
	 * cannot exceed 1.0f
	 */
	public void SetFollowSpeed(float speed)
	{
		if (speed > 1.0f) speed = 1.0f;
		followSpeed = speed;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
}
