package vwr.project.voidbubble.domain.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import vwr.project.services.graphics.DrawService;
import vwr.project.services.storage.ResourceService;

/**
 * A special kind of object
 * that translates on-screen coordinates
 * to in-game coordinates.
 */
public class Reticle
{
	private float x;
	private float y;
	private Texture tex;
	private Color col;
	private float scale;
	private int halfwidth;
	private int halfheight;
	
	public Reticle(ResourceService res, int width, int height)
	{
		tex = res.GetTexture("crosshair.png");
		col = new Color(0, 1.0f, 0.25f, 0.7f);
		scale = 15;
		halfwidth = width/2;
		halfheight = height/2;
	}
	
	public void Update(float xpos, float ypos, Camera cam)
	{
		x = xpos - halfwidth + cam.getX();
		y = ypos - halfheight + cam.getY();
	}
	
	public void Render(DrawService drawS, long ticks)
	{
		drawS.DrawSprite(x, y, ticks * 0.1f, scale, tex, col);
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
