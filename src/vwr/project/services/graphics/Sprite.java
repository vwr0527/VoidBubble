package vwr.project.services.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

/**
 * Draws a single texture. It is centered at
 * X and Y, rotated, scaled, and tinted with
 * a color.
 * A boolean camTrans determines whether it
 * is will be transformed by the camera.
 */
public class Sprite
{
	private float x;
	private float y;
	private Texture texture_r;
	private Color color_r;
	private float rotation;
	private float size;
	private boolean camTrans;
	
	public Sprite(boolean camTransform, float xpos, float ypos, float rot, float spritesize, Texture tex, Color col)
	{
		color_r = new Color(1,1,1,1);
		fillAttributes(camTransform, xpos,ypos,rot,spritesize,tex,col);
	}

	public void fillAttributes(boolean camTransform, float xpos, float ypos, float rot, float spritesize, Texture tex, Color col)
	{
		camTrans = camTransform;
		x = xpos;
		y = ypos;
		rotation = rot;
		size = spritesize;
		texture_r = tex;
		
		//just setting the colors equal would be bad.
		//it would become a reference, and then later
		//that reference could be changed. EG: Color.white
		if (col != null)
		{
			color_r.a = col.a;
			color_r.r = col.r;
			color_r.g = col.g;
			color_r.b = col.b;
		}
	}
	
	/**Draws the sprite*/
	public void Render()
	{
		color_r.bind();
		texture_r.bind();
		glPushMatrix();
		glTranslatef(x, y, 0);
		glRotatef(rotation, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0,1);
		glVertex3f(-size, -size, 0 );
		glTexCoord2f(0,0);
		glVertex3f(-size, size, 0 );
		glTexCoord2f(1,0);
		glVertex3f(size, size, 0 );
		glTexCoord2f(1,1);
		glVertex3f(size, -size, 0 );
		glEnd();
		glPopMatrix();
	}
	
	/**
	 * @return - whether or not it is affected by the camera
	 */
	public boolean getCamTrans()
	{
		return camTrans;
	}
}
