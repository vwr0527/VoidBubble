package vwr.project.voidbubble.domain.world;

import org.newdawn.slick.Color;

import vwr.project.services.graphics.DrawService;

public class ClipBox
{
	private float width;
	private float height;
	private float xpos;
	private float ypos;
	private boolean colliding;

	//ClipBox was originally naively put in State,
	//because somehow I thought that; since multiple
	//entities shared states, and entities with the same
	//states have the same hitboxes, then hitboxes belong in
	//states. What I didn't realize is that you can't detect
	//the intersection of two clipboxes when they are actually
	//each other one and the same. SAME POSITION!
	//Now they are in each entity. This greatly simplifies
	//things and allows the state to dynamically resize
	//hitboxes.
	public ClipBox(float w, float h)
	{
		Resize(w, h);
	}

	public void Resize(float w, float h)
	{
		width = w;
		height = h;
		center();
	}

	public ClipBox(float side)
	{
		Resize(side);
	}
	
	private void Resize(float side)
	{
		width = side;
		height = side;
		center();
	}
	
	public void AtPosition(float x, float y)
	{
		xpos = x - width / 2;
		ypos = y - height / 2;
	}

	private void center()
	{
		xpos = -width / 2;
		ypos = -height / 2;
	}

	public boolean CollisionTest(ClipBox clip)
	{
		if (clip.width == 0 || width == 0) return false;
		if (clip.height == 0 || height == 0) return false;
		float left = xpos;
		float top = ypos;
		float right = xpos + width;
		float bottom = ypos + height;
		float left2 = clip.xpos;
		float top2 = clip.ypos;
		float right2 = clip.xpos + clip.width;
		float bottom2 = clip.ypos + clip.height;
		
		//thanks tekpool!
		
		if( ! (right < left2
				|| left > right2
				|| top > bottom2
				|| bottom < top2) )
		{
			colliding = true;
			clip.colliding = true;
			return true;
		}
		return false;
	}
	
	public boolean ContainmentTest(ClipBox clip)
	{
		float left = xpos;
		float top = ypos;
		float right = xpos + width;
		float bottom = ypos + height;
		float left2 = clip.xpos;
		float top2 = clip.ypos;
		float right2 = clip.xpos + clip.width;
		float bottom2 = clip.ypos + clip.height;
		
		if ( !(left2 < left
				|| top2 < top
				|| bottom2 > bottom
				|| right2 > right))
		{
			colliding = false;
			return true;
		}
		return false;
	}
	
	public void DrawOutline(DrawService draw)
	{
		Color col;
		if (colliding) col = Color.red;
		else col = Color.blue;
		draw.DrawLine(xpos, ypos, col, xpos + width, ypos, col);
		draw.LineNextPoint(xpos + width, ypos + height, col);
		draw.LineNextPoint(xpos, ypos + height, col);
		draw.LineNextPoint(xpos, ypos, col);
		colliding = false;
	}
}
