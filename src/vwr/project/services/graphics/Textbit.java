package vwr.project.services.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * A string of text to be rendered with GL. Uses a reference
 * to a string buffer to provide the text.
 */
public class Textbit
{
	/**font to use*/
	private TrueTypeFont font;
	private float xpos;
	private float ypos;
	/**size of the text*/
	private float textSize;
	/**reference to the string buffer*/
	private StringBuffer content_ref;
	/**color of the text*/
	private Color textCol;
	/**whether or not to apply camera transformation*/
	private boolean camTrans;
	
	public Textbit(boolean camTransform, TrueTypeFont ttf, Color col, float x, float y, float size, StringBuffer text)
	{
		textCol = new Color(1,1,1,1);
		fillAttributes(camTransform, ttf, col, x, y, size, text);
	}
	
	public void fillAttributes(boolean camTransform, TrueTypeFont ttf, Color col, float x, float y, float size, StringBuffer text)
	{
		camTrans = camTransform;
		font = ttf;
		xpos = x;
		ypos = y;
		textSize = size;
		content_ref = text;
		
		if (col != null)
		{
			textCol.a = col.a;
			textCol.r = col.r;
			textCol.g = col.g;
			textCol.b = col.b;
		}
	}

	/**Draws the text, or prints it to the console if the font isn't found*/
	public void Render()
	{
		glPushMatrix();
		glTranslatef(xpos, ypos, 0);
		glScalef(textSize, textSize, 0);
		if (font == null) System.out.println("couldn't render string: " + content_ref);
		else font.drawString(0, 0, content_ref.toString(), textCol);
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
