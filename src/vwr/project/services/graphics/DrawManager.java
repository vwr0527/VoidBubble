package vwr.project.services.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import vwr.project.services.storage.SettingManager;

/**
 * Draw Manager initializes the GL parameters, then it uses 
 * lists of sprites, elements, and text things
 * that it draws once a frame, then clears* the lists. You need
 * to send the draw commands to the draw manager every frame you
 * want to draw something. If the thing you want to draw is viewed
 * from the camera's perspective, you need to specify that too.
 * Otherwise it will draw it normally, without the camera's view
 * transformation. You need to tell it the camera's parameters.
 * It also draws a mouse cursor, with no camera transformation.
 * *It doesn't actually clear the list, it just marks the things
 * as not needing to be drawn, and allows them to be re-used with
 * new parameters next frame. It will expand these lists as needed.
 */

public class DrawManager
{
	/**Screen's X resolution*/
	private int width;
	/**Screen's Y resolution*/
	private int height;
	/**Reference to the setting manager*/
	private SettingManager setting_m;
	/**Sprites are simply textures that are drawn, non-repeating,
	 * with parameters such as color, size and rotation. */
	private ArrayList<Sprite> sprites;
	private int numSprites;
	/**Elements are composed of references to points. They can take
	 * different forms, such as lines or triangles.	*/
	private ArrayList<Element> elements;
	private int numElements;
	/**Textbits are strings of text with parameters such as size,
	 * font and color. */
	private ArrayList<Textbit> textbits;
	private int numTextbits;
	/**Points can take different forms, such as points with color,
	 * or points with color and texture coordinates. */
	private ArrayList<PointColor> points;
	private int numPoints;
	
	/**Mouse cursor*/
	private Sprite cursor;
	private boolean showCursor;

	private float cam_x;
	private float cam_y;
	private float cam_r;
	private float cam_s = 1;
	
	public DrawManager(SettingManager settings)
	{
		setting_m = settings;
		sprites = new ArrayList<Sprite>();
		textbits = new ArrayList<Textbit>();
		elements = new ArrayList<Element>();
		points = new ArrayList<PointColor>();
		cursor = new Sprite(false, 0, 0, 0, 0, null, null);
		DoGLInitStuff();
	}

	/**Set up the GL parameters. Will be called again if screen
	 * resolution changes, or other settings change */
	private void DoGLInitStuff()
	{
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		glEnable(GL_TEXTURE_2D);
		glEnable( GL_BLEND );
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		width = Integer.parseInt(setting_m.Get("width"));
		height = Integer.parseInt(setting_m.Get("height"));
		glOrtho(0,width,0,height, -1, 1);
	}

	/**Set the camera parameters.
	 * @param x - X position of the camera
	 * @param y - Y position of the camera
	 * @param r - Rotation of the camera
	 * @param s - Scale, or zoom of the camera
	 */
	public void setCamera(float x, float y, float r, float s)
	{
		cam_x = x;
		cam_y = y;
		cam_r = r;
		cam_s = s;
	}
	
	/**
	 * Draws the current contents of the lists, then resets them.
	 * It will first perform the camera transformations, then draw
	 * the things in the lists that need to be drawn with a camera
	 * transformation. Then it draws the rest of the things with
	 * a normal ortho perspective. The order of drawing is:
	 * cam-transformed sprites, elements and text, then
	 * GUI or non-cam-transformed sprites elements and text.
	 */
	public void render()
	{
		glClear( GL_COLOR_BUFFER_BIT);
		
		glPushMatrix();
		
		glTranslatef(width/2, height/2, 0);
		
		glPushMatrix();

		glRotatef(cam_r, 0,0,1);
		glScalef(cam_s,cam_s,1);
		glTranslatef(-cam_x, -cam_y, 0);
		
		drawAll(true);

		glPopMatrix();
		glPopMatrix();
		
		drawAll(false);
		
		ResetAllElems();
		numElements = 0;
		numSprites = 0;
		numTextbits = 0;
		numPoints = 0;
		
		if(showCursor) cursor.Render();
		showCursor = false;
	}

	/**
	 * Draws all things in the lists that are either camera transformed
	 * or not.
	 * @param camTransform - whether to draw things that are camera transformed
	 * or not.
	 */
	private void drawAll(boolean camTransform)
	{
		glDisable(GL_TEXTURE_2D);
		
		for(int i = 0; i < numElements; ++i)
		{
			Element e = elements.get(i);
			if (e.camTrans == camTransform)
				e.Render();
		}
		
		glEnable(GL_TEXTURE_2D);
		
		for(int i = 0; i < numSprites; ++i)
		{
			Sprite s = sprites.get(i);
			if (s.getCamTrans() == camTransform)
				s.Render();
		}
		
		for(int i = 0; i < numTextbits; ++i)
		{
			Textbit t = textbits.get(i);
			if (t.getCamTrans() == camTransform)
				t.Render();
		}
	}

	/**
	 * Adds a sprite to the sprite list.
	 * @param cam - is this sprite a World sprite or a GUI sprite?
	 * @param x - X position of this sprite
	 * @param y - Y position of this sprite
	 * @param r - Rotation of this sprite
	 * @param s - Scale of this sprite
	 * @param tex - Texture of this sprite
	 * @param col - Color tinting of this sprite
	 */
	//I try my best to avoid throwing anything to the GC
	//hence the variable numSprites.
	public void DrawSprite(boolean cam, float x, float y, float r, float s, Texture tex, Color col)
	{
		if (tex == null) return;
		//warning: no error handling ie: too many sprites?
		if(numSprites >= sprites.size())
		{
			sprites.add(new Sprite(cam, x,y,r,s,tex,col));
		}
		else
		{
			Sprite cur = sprites.get(numSprites);
			cur.fillAttributes(cam, x,y,r,s,tex,col);
		}
		++numSprites;
	}

	/**
	 * Adds text to the text list.
	 * @param cam - is this text on the World plane or the GUI plane?
	 * @param ttf - Font to use for this text
	 * @param x - X position of this text
	 * @param y - Y position of this text
	 * @param size - Size of this text
	 * @param col - Color tinting of this sprite
	 * @param text - StringBuffer that contains the string of this text.
	 */
	//I use string buffer so I don't constantly create and destroy
	//strings every frame.
	public void DrawString(boolean cam, TrueTypeFont ttf, float x, float y, float size, Color col, StringBuffer text)
	{
		if(numTextbits >= textbits.size())
		{
			textbits.add(new Textbit(cam, ttf, col, x, y, size, text));
		}
		else
		{
			Textbit cur = textbits.get(numTextbits);
			cur.fillAttributes(cam, ttf, col, x, y, size, text);
		}
		++numTextbits;
	}

	/**
	 * Adds a line element to the elements list.
	 * @param cam - is this line a part of the World or GUI?
	 * @param x1 - X position of the starting point
	 * @param y1 - Y position of the starting point
	 * @param c1 - Color of the starting point
	 * @param x2 - X position of the end point
	 * @param y2 - Y position of the end point
	 * @param c2 - Color of the end point
	 */
	public void DrawLine(boolean cam, float x1, float y1, Color c1, float x2, float y2, Color c2)
	{
		if(numElements >= elements.size())
		{
			Element line = new ElementLine();
			line.camTrans = cam;
			line.AddPoint(addPointColor(x1, y1, c1));
			line.AddPoint(addPointColor(x2, y2, c2));
			elements.add(line);
		}
		else
		{
			Element line = elements.get(numElements);
			line.camTrans = cam;
			line.AddPoint(addPointColor(x1, y1, c1));
			line.AddPoint(addPointColor(x2, y2, c2));
		}
		++numElements;
	}
	
	/**
	 * Resets all the elements
	 */
	private void ResetAllElems()
	{
		for(int i = 0; i < numElements; ++i)
		{
			elements.get(i).Reset();
		}
	}
	
	/**
	 * Continues the line started by DrawLine. Connecting
	 * it to the end point of the last line.
	 * @param x - X position of the next point
	 * @param y - Y position of the next point
	 * @param c - Color of the next point
	 */
	public void LineNextPoint(boolean cam, float x, float y, Color c)
	{
		if (numPoints == 0) return;
		if(numElements >= elements.size())
		{
			Element line = new ElementLine();
			line.camTrans = cam;
			line.AddPoint(points.get(numPoints - 1));
			line.AddPoint(addPointColor(x, y, c));
			elements.add(line);
		}
		else
		{
			Element line = elements.get(numElements);
			line.camTrans = cam;
			line.AddPoint(points.get(numPoints - 1));
			line.AddPoint(addPointColor(x, y, c));
		}
		++numElements;
	}
	
	/**
	 * Draws the mouse cursor.
	 * @param x - X position of the cursor
	 * @param y - Y position of the cursor
	 * @param r - Rotation of the cursor
	 * @param s - Scale of the cursor
	 * @param tex - Texture of the cursor
	 * @param col - Color tint of the texture of the cursor.
	 */
	public void DrawCursor(float x, float y, float r, float s, Texture tex, Color col)
	{
		showCursor = true;
		cursor.fillAttributes(false, x,y,r,s,tex,col);
	}
	
	/**
	 * Creates a new point or recycles an existing, inactive point.
	 * @param x - X position of this point
	 * @param y - Y position of this point
	 * @param c - Color of this point
	 * @return a reference to the point.
	 */
	private PointColor addPointColor(float x, float y, Color c)
	{
		PointColor added;
		if(numPoints >= points.size())
		{
			added = new PointColor();
			added.x = x;
			added.y = y;
			added.SetColor(c);
			points.add(added);
		}
		else
		{
			added = points.get(numPoints);
			added.x = x;
			added.y = y;
			added.SetColor(c);
		}
		++numPoints;
		return added;
	}
}
