package vwr.project.services.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
/**
 * allows limited access to various functions in DrawManager
 */
public class DrawService
{
	/**local reference to the draw manager*/
	private DrawManager drawman_r;
	
	public DrawService(DrawManager drawM)
	{
		drawman_r = drawM;
	}

	/**Set the camera parameters.
	 * @param x - X position of the camera
	 * @param y - Y position of the camera
	 * @param r - Rotation of the camera
	 * @param s - Scale, or zoom of the camera
	 */
	public void SetCamera(float x, float y, float r, float s)
	{
		drawman_r.setCamera(x, y, r, s);
	}

	/**
	 * Draws a sprite.
	 * @param x - X position of the center of the sprite
	 * @param y - Y position of the center of the sprite
	 * @param r - Rotation of the sprite
	 * @param s - Scale of the sprite
	 * @param tex - Texture to use for the sprite
	 * @param col - Color tinting of the sprite
	 */
	public void DrawSprite(float x, float y, float r, float s, Texture tex, Color col)
	{
		drawman_r.DrawSprite(true, x, y, r, s, tex, col);
	}

	/**
	 * Draws a sprite in front of the world, unaffected by the camera.
	 * @param x - X position of the center of the sprite
	 * @param y - Y position of the center of the sprite
	 * @param r - Rotation of the sprite
	 * @param s - Scale of the sprite
	 * @param tex - Texture to use for the sprite
	 * @param col - Color tinting of the sprite
	 */
	public void DrawUISprite(float x, float y, float r, float s, Texture tex, Color col)
	{
		drawman_r.DrawSprite(false, x, y, r, s, tex, col);
	}

	/**
	 * Draws a line.
	 * @param x1 - X position of the starting point
	 * @param y1 - Y position of the starting point
	 * @param c1 - Color of the starting point
	 * @param x2 - X position of the end point
	 * @param y2 - Y position of the end point
	 * @param c2 - Color of the end point
	 */
	public void DrawLine(float x1, float y1, Color c1, float x2, float y2, Color c2)
	{
		drawman_r.DrawLine(true, x1, y1, c1, x2, y2, c2);
	}
	
	public void LineNextPoint(float x, float y, Color c)
	{
		drawman_r.LineNextPoint(true, x, y, c);
	}

	/**
	 * Draws a line in front of the world, unaffected by the camera.
	 * @param x1 - X position of the starting point
	 * @param y1 - Y position of the starting point
	 * @param c1 - Color of the starting point
	 * @param x2 - X position of the end point
	 * @param y2 - Y position of the end point
	 * @param c2 - Color of the end point
	 */
	public void DrawUILine(float x1, float y1, Color c1, float x2, float y2, Color c2)
	{
		drawman_r.DrawLine(false, x1, y1, c1, x2, y2, c2);
	}
	
	/**
	 * Renders white text unaffected by the camera
	 * @param ttf - Font to use.
	 * @param x - X position of this text
	 * @param y - Y position of this text
	 * @param size - Size of this text
	 * @param text - StringBuffer that contains the string of this text.
	 */
	public void DrawString(TrueTypeFont ttf, float x, float y, float size, StringBuffer text)
	{
		drawman_r.DrawString(false, ttf, x, y, size, Color.white, text);
	}

	/**
	 * Renders text unaffected by the camera
	 * @param ttf - Font to use.
	 * @param x - X position of this text
	 * @param y - Y position of this text
	 * @param size - Size of this text
	 * @param col - Color of this text
	 * @param text - StringBuffer that contains the string of this text.
	 */
	public void DrawString(TrueTypeFont ttf, float x, float y, float size, Color col, StringBuffer text)
	{
		drawman_r.DrawString(false, ttf, x, y, size, col, text);
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
		drawman_r.DrawCursor(x, y, r, s, tex, col);
	}
}
