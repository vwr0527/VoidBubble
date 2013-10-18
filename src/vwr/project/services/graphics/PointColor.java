package vwr.project.services.graphics;

import org.newdawn.slick.Color;

/**
 * Points to be used by elements. The colored only kind, no textures.
 * Textured elements would need PointColorTex, because they have
 * texture coordinates.
 */
public class PointColor
{
	float x, y;
	private Color c = new Color(1,1,1,1);
	public void SetColor(Color refCol)
	{
		c.a = refCol.a;
		c.r = refCol.r;
		c.g = refCol.g;
		c.b = refCol.b;
	}
	public void bindColor()
	{
		c.bind();
	}
}
